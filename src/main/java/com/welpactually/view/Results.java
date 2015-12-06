package com.welpactually.view;

import io.dropwizard.views.View;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.util.Iterator;

public class Results extends View {

    private Iterator<SolrDocument> results;

    public Results() {
        super("results.mustache");
    }

    public Results(SolrDocumentList results) {
        super("results.mustache");
        this.results = results.iterator();
    }

    public Iterator<SolrDocument> getResults(){
        return results;
    }
}
