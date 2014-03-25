package service;

import java.util.List;

import model.News;

public interface NewsManager {
	public List<News> getNewsToday(long startIndex, long pageSize);
	public List<News> getNewsYesterday(long startIndex, long pageSize);
	public List<News> getNewsWeek(long startIndex, long pageSize);
	public List<News> getNewsAll(long startIndex, long pageSize);
	public List<News> searchNews(String queryString, long startIndex, long pageSize);

}
