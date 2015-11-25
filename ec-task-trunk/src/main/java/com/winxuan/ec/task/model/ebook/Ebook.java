package com.winxuan.ec.task.model.ebook;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.winxuan.ec.task.support.utils.ISBNTool;

/**
 * 
 * @author luosh
 *
 */
public class Ebook {
	
	/**书名*/
	String bookName ;
	/***/
	String seriesName;
	/***/
	String isbn;
	/***/
	String sizeFormat;
	/***/
	String edition;
	/***/
	String printingTimes;
	/***/
	String pageCount;
	/***/
	String wordCount;
	/***/
	String copyCount;
	/***/
	String publisher;
	/***/
	String copyRightOwner;
	/***/
	String thisEditionPublishingDate;
	/**作者*/
	String author;
	/**作者简介*/
	String authorIntroduction;
	/**翻译者*/
	String translator;
	/**语言*/
	String language;
	/**纸书价格*/
	String listPrice;
	/**电子书价格*/
	String ebookListPrice;
	/**纸张*/
	String paperMaterial;
	/**包装*/
	String pack;
	/**关键字*/
	String keyWord;
	/**编辑推荐*/
	String bookmakerRecommend;
	/**媒体推荐*/
	String mediaRecommend;
	/**全文摘要*/
	String contentsAbstract;
	/**是不是未出版的原创图书*/
	String isOriginal;
	/**图书类型 普通图书填0；当图书为期刊杂志的时候填1。*/
	String type;
	/**图书源文件*/
	List<OriginalFile> files;
	/**章节信息*/
	List<Chapter> chapters;
	/**分类信息*/
	List<Category> categorys;
	/**是否为扫描书 0：非 1：是 2013-5-13 Hakeny add*/
	String isscan;
	
	List<ExtendInfo> extendInfos;
	
	public Ebook(){ 
		this.files=new LinkedList<OriginalFile>();
		this.chapters=new LinkedList<Chapter>();
		//this.chapters.add(new Chapter("1","2","3"));
		this.categorys=new LinkedList<Category>();
		this.extendInfos = new ArrayList<ExtendInfo>();
	}
	
	public String getPaperMaterial() {
		return paperMaterial;
	}
	public void setPaperMaterial(String paperMaterial) {
		this.paperMaterial = paperMaterial;
	}
	public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
	}
	public String getBookmakerRecommend() {
		return bookmakerRecommend;
	}
	public void setBookmakerRecommend(String bookmakerRecommend) {
		this.bookmakerRecommend = bookmakerRecommend;
	}
	public String getMediaRecommend() {
		return mediaRecommend;
	}
	public void setMediaRecommend(String mediaRecommend) {
		this.mediaRecommend = mediaRecommend;
	}
	public List<ExtendInfo> getExtendInfos() {
		return extendInfos;
	}
	public void setExtendInfos(List<ExtendInfo> extendInfos) {
		this.extendInfos = extendInfos;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getSeriesName() {
		return seriesName;
	}
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	/**纸书价格*/
	public String getListPrice() {
		return listPrice;
	}
	/**纸书价格*/
	public void setListPrice(String listPrice) {
		this.listPrice = listPrice;
	}
	/**电子书价格*/
	public String getEbookListPrice() {
		return ebookListPrice;
	}
	/**电子书价格*/
	public void setEbookListPrice(String ebookListPrice) {
		this.ebookListPrice = ebookListPrice;
	}

	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public List<OriginalFile> getFiles() {
		return files;
	}
	public void setFiles(List<OriginalFile> files) {
		this.files = files;
	}
	public String getContentsAbstract() {
		return contentsAbstract;
	}
	public void setContentsAbstract(String contentsAbstract) {
		this.contentsAbstract = contentsAbstract;
	}
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public String getPrintingTimes() {
		return printingTimes;
	}
	public void setPrintingTimes(String printingTimes) {
		this.printingTimes = printingTimes;
	}
	public String getPageCount() {
		return pageCount;
	}
	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}
	public String getWordCount() {
		return wordCount;
	}
	public void setWordCount(String wordCount) {
		this.wordCount = wordCount;
	}
	public String getCopyCount() {
		return copyCount;
	}
	public void setCopyCount(String copyCount) {
		this.copyCount = copyCount;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getCopyRightOwner() {
		return copyRightOwner;
	}
	public void setCopyRightOwner(String copyRightOwner) {
		this.copyRightOwner = copyRightOwner;
	}
	public String getThisEditionPublishingDate() {
		return thisEditionPublishingDate;
	}
	public void setThisEditionPublishingDate(String thisEditionPublishingDate) {
		this.thisEditionPublishingDate = thisEditionPublishingDate;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAuthorIntroduction() {
		return authorIntroduction;
	}
	public void setAuthorIntroduction(String authorIntroduction) {
		this.authorIntroduction = authorIntroduction;
	}
	public String getTranslator() {
		return translator;
	}
	public void setTranslator(String translator) {
		this.translator = translator;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public List<Chapter> getChapters() {
		return chapters;
	}
	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}
	public List<Category> getCategorys() {
		return categorys;
	}
	public void setCategorys(List<Category> categorys) {
		this.categorys = categorys;
	}
	public String getSizeFormat() {
		return sizeFormat;
	}
	public void setSizeFormat(String sizeFormat) {
		this.sizeFormat = sizeFormat;
	}
	
	/**检查从xml读出的ebook对象是否正常,作必要的调整 0正常*/
	public static boolean checkEbook(Ebook ebook){
		String isbn = ebook.getIsbn();
		if(StringUtils.isEmpty(ebook.getType())){
			ebook.setType("0");
		}
		if(!StringUtils.isEmpty(isbn)){
			if("0".equals(ebook.getType())){
				if(isbn.length()==10){
					isbn = ISBNTool.convertISBN10To13(isbn);
					ebook.setIsbn(isbn);
				}else if(isbn.length()!=13){
					return false;
				}
			}else if("1".equals(ebook.getType())){
				if(isbn.length()!=8){
					return false;
				}
			}
		}
		return true;
	}
	/**是不是原创 是true  否false*/
	public String getIsOriginal() {
		return isOriginal;
	}
	/**是不是原创 是true  否false*/
	public void setIsOriginal(String isOriginal) {
		this.isOriginal = isOriginal;
	}
	/**图书类型 普通图书填0；当图书为期刊杂志的时候填1。*/
	public String getType() {
		return type;
	}
	/**图书类型 普通图书填0；当图书为期刊杂志的时候填1。*/
	public void setType(String type) {
		this.type = type;
	}
	public String getIsscan() {
		return isscan;
	}
	public void setIsscan(String isscan) {
		this.isscan = isscan;
	}
}
