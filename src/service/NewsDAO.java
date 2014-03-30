package service;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;

import model.News;
import util.SolrServerClient;

public class NewsDAO {
	private static SolrServer solr = SolrServerClient.getInstance()
			.getServer("news");


	public static List<News> getNewsByTimeRange(String timeRange)
			throws SolrServerException {
		SolrQuery parameters = new SolrQuery();
		parameters.set("q", "*:*");
		parameters.set("fq", String.format("update_time:[%s]", timeRange));
		parameters.addSort("update_time", ORDER.desc);
		QueryResponse response = solr.query(parameters);
		List<News> newsList = response.getBeans(News.class);
		return newsList;
	}
}
