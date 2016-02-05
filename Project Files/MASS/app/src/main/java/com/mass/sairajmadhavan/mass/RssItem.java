package com.mass.sairajmadhavan.mass;

/**
 * Created by Sairaj Madhavan on 1/6/2015.
 */
public class RssItem {

    private final String title;
    private final String link;

    public RssItem(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }
}
