package com.iquestion.service;


import com.iquestion.pojo.Question;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public interface SearchService {




    public List<Question> searchQuestion(String keyword, int offset, int count,
                                         String hlPre, String hlPos) throws IOException, SolrServerException ;


    public boolean indexQuestion(int qid, String title, String content) throws Exception ;
}
