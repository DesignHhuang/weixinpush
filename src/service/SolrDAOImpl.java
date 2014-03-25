package service;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import util.SolrServerClient;


public class SolrDAOImpl  {
	
	public <T> List<T> getResultsByTimeRange(String timeRange, long startIndex,
			long pageSize, String coreName, Class<T> clazz)
			throws SolrServerException {
		SolrServer solr = SolrServerClient.getInstance().getServer(coreName);
		SolrQuery query = new SolrQuery();
		query.set("q", "*:*");
		query.set("fq", String.format("update_time:[%s]", timeRange));
		query.addSort("update_time", ORDER.desc);
		query.setStart((int) startIndex);
		query.setRows((int) pageSize);
		QueryResponse response = solr.query(query);
		List<T> resultList = response.getBeans(clazz);
		return resultList;
	}


	public long getCountByTimeRange(String timeRange, String coreName)
			throws SolrServerException {
		SolrServer solr = SolrServerClient.getInstance().getServer(coreName);
		SolrQuery parameters = new SolrQuery();
		parameters.set("q", "*:*");
		parameters.set("fl", "id");
		parameters.set("fq", String.format("update_time:[%s]", timeRange));
		QueryResponse response = solr.query(parameters);
		return response.getResults().getNumFound();
	}


	public <T> List<T> getResults(String queryString, long startIndex,
			long pageSize, String coreName, Class<T> clazz)
			throws SolrServerException {
		SolrServer solr = SolrServerClient.getInstance().getServer(coreName);
		SolrQuery query = new SolrQuery();
		query.setQuery(queryString);
		query.addSort("update_time", ORDER.desc);
		query.setStart((int) startIndex);
		query.setRows((int) pageSize);
		QueryResponse response = solr.query(query);
		List<T> resultList = response.getBeans(clazz);
		return resultList;
	}

}
