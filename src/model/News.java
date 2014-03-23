package model;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

public class News {
	private String url;
	private String title;
	private String content;
	private String category;
	private Date updateTime;

	public String getUrl() {
		return url;
	}

	@Field
	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	@Field
	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	@Field
	public void setContent(String content) {
		this.content = content;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	@Field("update_time")
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCategory() {
		return category;
	}

	@Field
	public void setCategory(String category) {
		this.category = category;
	}
}