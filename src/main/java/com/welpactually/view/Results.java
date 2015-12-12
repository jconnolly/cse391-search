package com.welpactually.view;

import io.dropwizard.views.View;

import java.util.ArrayList;
import java.util.List;

public class Results extends View {

    private String query;


    private List results;

    public Results(String query, ArrayList results) {
        super("results.mustache");
        this.query = query;
        this.results = results;
    }

    public String getQuery() {
        return query;
    }

    public List getResults() {
        return results;
    }
}
