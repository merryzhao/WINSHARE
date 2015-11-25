package com.winxuan.ec.task.service.ec.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.winxuan.ec.dao.CmDao;
import com.winxuan.ec.model.cm.Link;
import com.winxuan.ec.task.exception.ec.FileProcessException;
import com.winxuan.ec.task.model.ec.TopicFragment;
import com.winxuan.ec.task.model.ec.TopicMeta;
import com.winxuan.ec.task.service.ec.TopicService;
import com.winxuan.ec.task.support.utils.StreamUtils;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * 主题解析部署
 * @author Heyadong
 * @version 1.0 , 2011-12-1
 */
@Service("topicService")
public class TopicServiceImpl implements TopicService, Serializable {
	private static Log LOG = LogFactory.getLog(TopicServiceImpl.class);
	public static final String META_NAME_KEY = "{NAME}";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4415798429840063404L;
	private static Pattern htmlHead = Pattern.compile("(?i)<head>(.*)</head>",Pattern.DOTALL);
	private static Pattern htmlTitle = Pattern.compile("(?i)<title.*>(.*)</title>",Pattern.DOTALL);
	
	@InjectDao
	private CmDao cmDao;
	@Value("${ec.topic.dest}")
	private String dest;
	@Value("${ec.topic.regx}")
	private String regx;
	
	@Value("${ec.topic.year}")
	private int year;
	@Value("${ec.topic.month}")
	private int month;
	
	@Value("${ec.topic.page}")
	private String page;
	@Value("${ec.topic.metaNameSet}")
	private String metaNameSet;
	
	@Value("${ec.topic.metaRegex}")
	private String metaRegex;
	
	@Value("${ec.topic.metaRegexContent}")
	private String metaRegexContent;
	
	@Value("${ec.topic.meta.name}")
	private String topicName;
	@Value("${ec.topic.meta.image}")
	private String topicImage;
	@Value("${ec.topic.link.srouce}")
	private String linkSource;
	
	@Value("${ec.topic.link.href}")
	private String linkHref;
	
	
	public TopicFragment process(File zip) throws IOException, FileProcessException{
		
		if (!zip.getName().matches(regx)) {
			throw new FileProcessException(String.format("filename don't matched == %s", zip.getAbsolutePath()));
		}
		/*根据文件名获取上传年份、月份*/
		String year = null;
		String month = null;
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(zip.getName());
		if (matcher.find()){
			year = matcher.group(this.year);
			month = matcher.group(this.month);
		} 
		TopicFragment topicFragment = new TopicFragment();
		topicFragment.setMonth(month);
		topicFragment.setYear(year);
		
		//Zip文件
		ZipFile zipFile = null;
		
		try {
			/*
			 *  zip 包解析,到指定目录
			 */
	 		zipFile = new ZipFile(zip);
	 		Enumeration<ZipArchiveEntry> entrys = zipFile.getEntries();
	 		ZipArchiveEntry index = null;
	 		//将zip 信息导入Map,稍后做验证在才决定是否解压
	 		while (entrys.hasMoreElements()){
	 			
	 			ZipArchiveEntry entry = entrys.nextElement();
	 			if (entry.getName().equals(page) && !entry.isDirectory()){
	 				index = entry;
	 				break;
	 			}
	 		}
	 		//查看是否包含index.html
	 		if(page == null) {
	 			throw new FileProcessException(String.format("This zip don't has '%s' == [%s]",page,zip.getName()));
	 		}
	 		
//	 		ZipArchiveEntry index = map.get(page);
	 		//抓取HTML  head meta title 设置topic
	 		this.spider(zipFile.getInputStream(index), topicFragment);
	 		
	 		TopicMeta topic = topicFragment.getMetas().get(this.topicName);
			if (topic == null) {
				throw new FileProcessException("this index.html not find <meta name='topicName'>");
			}
			topicFragment.setTopic(topic.getContent());
			//数据验证
			checkData(topicFragment);
			
			//验证通过,开始解压
			
			String target = dest.replace("{year}", year).replace("{month}", month).replace("{topic}", topicFragment.getTopic());
			LOG.info(String.format("decompress file path == %s", target));
			entrys = zipFile.getEntries();
			while (entrys.hasMoreElements()){
	 			ZipArchiveEntry entry = entrys.nextElement();
	 			if(entry.isDirectory()){
	 				File dir = new File(target + entry.getName());
	 				dir.mkdirs();
	 				continue;
	 			}
	 			File file = new File(target + entry.getName());
	 			//创建目录
	 			file.getParentFile().mkdirs();
	 			file.createNewFile();
	 			FileOutputStream fos = new FileOutputStream(file);
	 			InputStream in = zipFile.getInputStream(entry);
	 			 
	 			StreamUtils.write(in, fos);
	 			StreamUtils.close(fos, in);
	 		}
	 		this.save(topicFragment);
		} catch (IOException e) {
			throw e;
		} catch (FileProcessException e){
			throw e;
		} finally{
			if (null != zipFile){
				zipFile.close();
			}
		}
		
 		return topicFragment;
	}
	
	/**
	 * 抓取HTML
	 * @param in 数据源
	 * @param pojo 将抓取的值赋予给他
	 * @throws IOException
	 * @throws FileProcessException
	 */
	public void spider(InputStream in, TopicFragment topic) throws IOException, FileProcessException{

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		StreamUtils.write(in, baos);
 		String pageContext = new String(baos.toByteArray());
 		Matcher headMatcher = htmlHead.matcher(pageContext);
 		if(headMatcher.find()) {
 			String headTag = headMatcher.group();
 			
 			//title
 			Matcher titleMatcher = htmlTitle.matcher(headTag);
 			if(titleMatcher.find()){
 				String title = titleMatcher.group(1);
 				topic.setTitle(title);
 			} else {
 				StreamUtils.close(in, baos);
 				throw new FileProcessException(String.format("The '<head>' don't have <title> tag, regex:%s", htmlTitle.pattern()));
 			}
 			
 			/*查找 Head 中 meta的信息*/
 			String[] names = this.metaNameSet.split("\\|");

 			Pattern metaContentRegex = Pattern.compile(metaRegexContent,Pattern.DOTALL);
 			for (String metaName : names){
 				metaName = metaName.trim();
 				Pattern htmlMeta = Pattern.compile(metaRegex.replace(META_NAME_KEY, metaName),Pattern.DOTALL);
 				Matcher metaMatcher =htmlMeta.matcher(headTag);
 				if (metaMatcher.find()){
 					String metaTag = metaMatcher.group();
 					Matcher metaContentMatcher = metaContentRegex.matcher(metaTag);
 					if (metaContentMatcher.find()) {
 						
 						TopicMeta meta = new TopicMeta();
 						meta.setContent(metaContentMatcher.group(1));
 						meta.setName(metaName);
 						topic.getMetas().put(metaName,meta);
 					}
 				}
 			}
 			
 			
 		}else {
 			StreamUtils.close(in, baos);
 			throw new FileProcessException(String.format("Not find '<head>' , regex:%s", htmlHead.pattern()));
 		}
 		
 		StreamUtils.close(in, baos);
	}
	private String replaceLink(TopicFragment topicFragment,String link){
		return link.replace("{year}", topicFragment.getYear())
		.replace("{month}", topicFragment.getMonth())
		.replace("{title}", topicFragment.getTitle())
		.replace("{topic}", topicFragment.getTopic())
		.replace("{content}", topicFragment.getMetas().get(topicImage).getContent());
	}
	@Override
	public void save(TopicFragment topicFragment) {
		
		//主题link
		Link link = new Link();
		link.setTitle(topicFragment.getTitle());
		link.setName(topicFragment.getMetas().get(topicName).getContent());
		link.setSrc(replaceLink(topicFragment, linkSource));
		link.setHref(replaceLink(topicFragment, linkHref));
		link.setImg(true);
		
		cmDao.saveOrUpdateLink(link);
		StringBuilder sb = new StringBuilder();
		sb.append("\n sendTo Database >>")
			.append("\n name==").append(link.getName())
			.append("\n title==").append(link.getTitle())
			.append("\n src==").append(link.getSrc())
			.append("\n href==").append(link.getHref())
			.append("\n isImg==").append(link.isImg());
		LOG.info(sb.toString());
	}
	
	/**
	 * 数据验证
	 * @param topicFragment
	 * @throws FileProcessException
	 */
	public void checkData(TopicFragment topicFragment) throws FileProcessException{
		//验证
		if (StringUtils.isBlank(topicFragment.getYear())){
			throw new FileProcessException("file name error ! not find 'year'");
		} else if(StringUtils.isBlank(topicFragment.getMonth())){
			throw new FileProcessException("file name error ! not find 'month'");
		} else if (StringUtils.isBlank(topicFragment.getTitle())) {
			throw new FileProcessException("html content error !  not find <title>");
		} else if(StringUtils.isBlank(topicFragment.getTopic())){
			throw new FileProcessException("html content error ! <meta> not find 'topicName'");
		} else if(null == topicFragment.getMetas().get(this.topicImage)){
			throw new FileProcessException("html content error ! <meta> not find 'topicImage'");
		}
	}
}
