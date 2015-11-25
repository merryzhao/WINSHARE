/*
 * @(#)AuthorStringTokenizer.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.winxuan.ec.support.util.MagicNumber;


/**
 * description 作者字符串处理工具类
 * @author  huangyixiang
 * @version 2011-10-18
 */
public class AuthorStringTokenizer {
	
	private static final Log LOG = LogFactory.getLog(AuthorStringTokenizer.class);
	
	private static Date newAuthorRuleDate;
	
	private static final String AUTHOR_NAME_REG = "(?:[（〔\\(].+?[）〕\\)])?(.+?)(?:[（〔\\(].+?[）〕\\)])?";
	
	private static final String AUTHOR_NAME_SPLIT_REG = ",|，";
	
	private static final String AUTHOR_REG = "(?:(.*?)\\s+等?\\s*著?\\s*著作)?\\s*(?:(.*?)\\s主编)?\\s*(?:(.*?)\\s编者)?\\s*(?:(.*?)\\s译者)?";
	
	private static final String[][] WORDS_ARRAY = {
		{
			"、",
			"," ,
			"，" ,
			"；" ,
			";",
			"\\(.*?\\)",
			"（.*?）",
			"//",
			"\\|",
			":",
			"等编著 ",
			"副?主编",
			"编著",
			"等译",
			"等文",
			"等图",
			"等著",
			"译者",
			"著作",
			"编者",
			"　",//放最后
		},
		{
			"([　 ]编)",
			"([　 ]等)",
			"([　 ]译)",
			"([　 ]著)",
		}
		
	};
	private static  String[] exceptWordsRegs = new String[WORDS_ARRAY.length];
	
	static {
		final String split = "|" ;
		StringBuilder sb;
		for(int i =0 ; i < WORDS_ARRAY.length ; i++){
			sb = new StringBuilder();
			for(String word : WORDS_ARRAY[i]){
				sb.append(split).append(word);
				
			}
			exceptWordsRegs[i] = sb.substring(1);
		}
		
		try {
			newAuthorRuleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2012-07-17 10:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			LOG.info(e.getMessage(), e);
		}
	}
	
	public static String[] getAuthors(String authorString){
		
		for(String exceptWordsReg : exceptWordsRegs){
			authorString = authorString.replaceAll(exceptWordsReg, " ");
		}
		String[] authorSplit = authorString.trim().split("\\s+");
		if(authorSplit == null || authorSplit.length == 0){
			return null;
		}
		for(int i = 0; i < authorSplit.length; i++){
			authorSplit[i] = authorSplit[i].trim();
		}
		
		return authorSplit; 
	}
	
	public static List<String>[] getAuthorsForNewRule(String authorString){

		Pattern p = Pattern.compile(AUTHOR_REG);
		Matcher m = p.matcher(authorString.trim());
		if(!m.matches()){
			//LOG.info("author does't match new regex");
			return null;
		}
		String[] authors = new String[]{m.group(MagicNumber.ONE),
						m.group(MagicNumber.TWO),
						m.group(MagicNumber.THREE),
						m.group(MagicNumber.FOUR)};
		
		@SuppressWarnings("unchecked")
		List<String>[] list = new ArrayList[authors.length];
		for(int i = 0; i < list.length; i++){
			if(StringUtils.isBlank(authors[i])){
				list[i] = null;
			}
			else{
				List<String> names = new ArrayList<String>();
				for(String authorFull : authors[i].split(AUTHOR_NAME_SPLIT_REG)){
					if(!StringUtils.isBlank(authorFull)){
						Pattern p1 = Pattern.compile(AUTHOR_NAME_REG);
						Matcher m1 = p1.matcher(authorFull.trim());
						if(!m1.matches()){
							continue;
						}
						String authorName = m1.group(MagicNumber.ONE);
						if(!StringUtils.isBlank(authorName)){
							names.add(authorName.trim());
						}
					}
				}
				list[i] = names;
			}
		}
		return list;
	}
	
	public static String getAuthorWithUrl(String authorString , String solrCreateTime){
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(solrCreateTime);
		} catch (ParseException e) {
			LOG.info(e.getMessage(),e);
			return authorString;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, MagicNumber.EIGHT);
		Date createTime = calendar.getTime();
		return getAuthorWithUrl(authorString, createTime);
	}
	
	public static String getAuthorWithUrl(String authorString , Date createTime){
		try{
			if(StringUtils.isBlank(authorString)){
				return authorString;
			}
			
			if (createTime.after(newAuthorRuleDate)) {
				List<String>[] authorNamesList = getAuthorsForNewRule(authorString);
				if(authorNamesList == null){
					return getAuthorWithUrlForOld(authorString);
				}
				Set<String> authorNamesSet = new HashSet<String>();
				for(List<String> list : authorNamesList){
					if(list == null || list.isEmpty()){
						continue;
					}
					for(String authorName : list){
						authorNamesSet.add(authorName);
					}
				}
				for(String authorName : authorNamesSet){
					authorString = StringUtils.replace(authorString, authorName, addUrl(authorName));
				}
				authorString = authorString.replaceFirst("\\s著作", " 著")
							.replaceFirst("\\s编者", " 编")
							.replaceFirst("\\s译者", " 译");
				return authorString;
				
			}
			else{
				return getAuthorWithUrlForOld(authorString);
			}
		}
		catch (Exception e) {
			LOG.info(e.getMessage(),e);
			return authorString;
		}
		
	}
	
	private static String getAuthorWithUrlForOld(String authorString){
		String[] authors = getAuthors(authorString);
		//多作者去重，处理页面html乱码
		Set<String> set = new HashSet(Arrays.asList(authors));
		for(String split : set){
			authorString = StringUtils.replace(authorString, split, addUrl(split));
		}
		return authorString;
	}
	
	private static String addUrl(String author) {
		try {
			return "<a href=\"http://search.winxuan.com/search?author="
					+ URLEncoder.encode(author, "UTF-8")
					+ "\" style=\"color:#1D66B2\">" + author + "</a>";
		} catch (UnsupportedEncodingException e) {
			LOG.info(e.getMessage(), e);
		}
		return author;
	}
	
	/**
	 * 根据作者字段取得作者名字的拼接(去掉空格),新录入规范下只取著作者，旧的全取
	 * @param author
	 * @return
	 */
	public static String getAuthorNameString(String author){
		if(StringUtils.isBlank(author)){
			return null;
		}
		
		String authorString = "";
		String[] authors = null;
		List<String>[] allAuthors = getAuthorsForNewRule(author);
		
		if(allAuthors != null && allAuthors[0] != null && !allAuthors[0].isEmpty()){
			try{
				authors = (String[])allAuthors[0].toArray();
			}
			catch (ClassCastException e){
				LOG.error(e.getMessage(), e);
				Object[] objects = allAuthors[0].toArray();
				authors = new String[objects.length];
				for(int i = 0; i < objects.length; i++){
					authors[i] = objects[i].toString();
				}
			}
		}
		else{
			authors = getAuthors(author);
		}
		
		if(authors == null){
			return null;
		}
		
		for(String authorName : authors){
			authorString += authorName;
		}
		return authorString.replaceAll("\\s+", authorString);
	}
}
