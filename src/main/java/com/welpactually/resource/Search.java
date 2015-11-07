package com.welpactually.resource;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/")
public class Search {


    private final SolrClient solrClient;

    public Search(SolrClient solr) {
        this.solrClient = solr;
    }

    @GET
    @Path("/search")
    @Produces(MediaType.TEXT_HTML)
    public Response search(@QueryParam("q") String q) throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery(q);
        solrClient.query(query);
        return Response.ok((q)).build();
    }
}