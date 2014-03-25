package service;

import java.util.List;

import model.Paper;

public interface PaperManager {
	public List<Paper> getPapersMonth(long startIndex, long pageSize);
	public List<Paper> getPapersAll(long startIndex, long pageSize);
	public List<Paper> searchPapers(String queryString, long startIndex, long pageSize);

}
