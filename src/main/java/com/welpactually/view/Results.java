package com.welpactually.view;

import io.dropwizard.views.View;
import org.apache.solr.common.SolrDocumentList;

public class Results extends View {
    public Results() {
        super("results.mustache");
    }


    public Results(SolrDocumentList results) {
        super("results.mustache");
    }
}
