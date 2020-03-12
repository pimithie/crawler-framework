package com.xiaqi.crawl.filter.fetch.image;

import com.xiaqi.crawl.filter.fetch.AbstractFetchFilterComposite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImageFetchFilterComposite extends AbstractFetchFilterComposite<ImageFetchFilter> {

    @Autowired
    public ImageFetchFilterComposite(List<ImageFetchFilter> filters) {
        super(filters);
    }


}
