package com.winxuan.ec.task.model.ec;

/**
 * Html Meta
 * @author Heyadong
 * @date 2011-11-30
 */
public class TopicMeta {
	private String name;
	private String content;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return String.format("name=%s,  content=%s", name,content);
	}
}
