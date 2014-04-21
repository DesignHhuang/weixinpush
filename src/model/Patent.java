package model;

import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

public class Patent {
	private String id;
	private String path;
	private String title;
	private List<String> inventor;
	private String _abstract;
	private List<String> applicant;
	private List<String> category;
	private Date updateTime;

	public String getId() {
		return id;
	}

	@Field
	public void setId(String id) {
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

	public List<String> getInventor() {
		return inventor;
	}

	@Field
	public void setInventor(List<String> inventor) {
		this.inventor = inventor;
	}

	public String getAbstract() {
		return _abstract;
	}

	@Field
	public void setAbstract(String _abstract) {
		this._abstract = _abstract;
	}

	public List<String> getApplicant() {
		return applicant;
	}

	@Field
	public void setApplicant(List<String> applicant) {
		this.applicant = applicant;
	}

	public List<String> getCategory() {
		return category;
	}

	@Field
	public void setCategory(List<String> category) {
		this.category = category;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	@Field("update_time")
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
