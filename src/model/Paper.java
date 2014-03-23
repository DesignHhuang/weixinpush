package model;

import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

public class Paper {
	private long id;
	private String path;
	private String title;
	private List<String> author;
	private String _abstract;
	private List<String> keywords;
	private String classification;
	private String category;
	private Date updateTime;

	public long getId() {
		return id;
	}

	@Field
	public void setId(long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	@Field
	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	@Field
	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getAuthor() {
		return author;
	}

	@Field
	public void setAuthor(List<String> author) {
		this.author = author;
	}

	public String getAbstract() {
		return _abstract;
	}

	@Field
	public void setAbstract(String _abstract) {
		this._abstract = _abstract;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	@Field
	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public String getClassification() {
		return classification;
	}

	@Field
	public void setClassification(String classification) {
		this.classification = classification;
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
