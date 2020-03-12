package com.xiaqi.crawl.filter.fetch.audio;

import com.xiaqi.crawl.filter.fetch.AbstractFetchFilterComposite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AudioFetchFilterComposite extends AbstractFetchFilterComposite<AudioFetchFilter> {

    @Autowired
    public AudioFetchFilterComposite(List<AudioFetchFilter> filters) {
        super(filters);
    }


}
