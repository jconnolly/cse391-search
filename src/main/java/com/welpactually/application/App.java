package com.welpactually.application;

import com.welpactually.config.SearchConfiguration;
import com.welpactually.resource.Search;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.glassfish.jersey.filter.LoggingFilter;

import java.util.logging.Logger;

public class App extends Application<SearchConfiguration>
{
    public static void main(String[] args) throws Exception
    {
        new App().run(args);
    }

    @Override
    public String getName() {
        return "CSE391 Search";
    }


    @Override
    public void initialize(Bootstrap<SearchConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets/pages", "/", "index.html", "html"));
        bootstrap.addBundle(new ViewBundle());

    }

    @Override
    public void run(SearchConfiguration config, Environment environment) {
        SolrClient solr = getSolrConnection();
        environment.jersey().register(new Search(solr));
        environment.jersey().register(new LoggingFilter(
                        Logger.getLogger(LoggingFilter.class.getName()),
                        true)
        );

    }

    //FIXME: read this from config
    public SolrClient getSolrConnection() {
        return new HttpSolrClient("http://solr0.local:8983/solr/wiki");
    }
}