package com.welpactually.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.welpactually.view.Results;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SuggesterResponse;
import org.apache.solr.client.solrj.response.Suggestion;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Path("/")
public class Search {


    private final SolrClient solrClient;
    private ObjectMapper mapper = new ObjectMapper();
    private org.slf4j.Logger logger = LoggerFactory.getLogger(Search.class);

    public Search(SolrClient solr) {
        this.solrClient = solr;
    }

    @GET
    @Path("/autocomplete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("query") String query) throws IOException, SolrServerException {
        SolrQuery q = new SolrQuery(query);
        q.setRequestHandler("/suggest");
        QueryResponse result = solrClient.query(q);
        List<String> terms = result.getSuggesterResponse()
                .getSuggestions()
                .get("mySuggester").stream().map(s->s.getTerm()).collect(Collectors.toList());

        HashMap<String, List> response = new HashMap<>();
        response.put("suggestions", terms);
        return Response.ok(mapper.writeValueAsString(response)).build();
    }

    @GET
    @Path("/results")
    @Produces(MediaType.TEXT_HTML)
    public Response searchHtml(@QueryParam("q") String q) throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery();
        logger.info("Query: " + q);
        query.setQuery(q);
        query.setHighlight(true).setHighlightSnippets(1);
        query.setHighlightSimplePre("<em>");
        query.setHighlightSimplePost("</em>");
        query.setParam("hl.fl", "content");
        QueryResponse queryResponse = solrClient.query(query);
        Iterator<SolrDocument> iter = queryResponse.getResults().iterator();
        logger.info(queryResponse.toString());
        ArrayList results = new ArrayList<Map<String, String>>();
        while (iter.hasNext()) {
            SolrDocument resultDoc = iter.next();
            String id = (String) resultDoc.getFieldValue("id");
            if (queryResponse.getHighlighting().get(id) != null) {
                HashMap<String, String> resultMap = new HashMap<>();
                resultMap.put("title", id);
                resultMap.put("highlight", queryResponse.getHighlighting().get(id).get("content").get(0));
                results.add(resultMap);
            }
        }
        logger.info(results.toString());
        return Response.ok(new Results(q, results)).build();
    }
}