package com.welpactually.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.welpactually.view.Results;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.logging.Logger;

@Path("/")
public class Search {


    private final SolrClient solrClient;
    private ObjectMapper mapper = new ObjectMapper();
    private org.slf4j.Logger logger = LoggerFactory.getLogger(Search.class);

    public Search(SolrClient solr) {
        this.solrClient = solr;
    }

    @GET
    @Path("/search")
    @Produces(MediaType.TEXT_HTML)
    public Response search(@QueryParam("q") String q) throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery(q);
        QueryResponse result = solrClient.query(query);
        logger.info(result.toString());
        return Response.ok(mapper.writeValueAsString(result.getResults())).build();
    }

    @GET
    @Path("/results")
    @Produces(MediaType.TEXT_HTML)
    public Results searchHtml(@QueryParam("q") String q) throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery(q);
        QueryResponse result = solrClient.query(query);
        logger.info(result.toString());
        return new Results(result.getResults());

    }
}