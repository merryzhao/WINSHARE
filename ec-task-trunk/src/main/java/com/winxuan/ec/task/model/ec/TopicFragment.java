package com.winxuan.ec.task.model.ec;

import java.util.HashMap;
import java.util.Map;

/**
 * 主题
 * @author Heyadong
 * @version 1.0,2011-11-30
 */
public class TopicFragment {
	private String year;
	private String month;
	private String topic;

	/*需要从HTML 抓取*/
	private String title;
	private Map<String,TopicMeta> metas = new HashMap<String,TopicMeta>();
	
	
	public Map<String, TopicMeta> getMetas() {
		return metas;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	@Override
	public String toString() {
		String tag = ",   ";
		StringBuilder sb = new StringBuilder();
		sb.append("topic= ").append(topic).append(tag);
		sb.append("year= ").append(year).append(tag);
		sb.append("month= ").append(month).append(tag);
		sb.append("title= ").append(title).append(tag);
		sb.append("metas= ").append(metas.toString());
		
		return sb.toString();
	}
}
