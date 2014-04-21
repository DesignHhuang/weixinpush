package service;

import java.util.List;

import model.Patent;

public interface PatentManager {
	public List<Patent> getPatentsMonth(long startIndex, long pageSize);
	public List<Patent> getPatentsAll(long startIndex, long pageSize);
	public List<Patent> searchPatents(String queryString, long startIndex, long pageSize);

}
