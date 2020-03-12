package com.xiaqi.crawl.filter.fetch.html;

import com.xiaqi.crawl.filter.fetch.AbstractFetchFilterComposite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HtmlFetchFilterComposite extends AbstractFetchFilterComposite<HtmlFetchFilter> {

    @Autowired
    public HtmlFetchFilterComposite(List<HtmlFetchFilter> filters) {
        super(filters);
    }

}
