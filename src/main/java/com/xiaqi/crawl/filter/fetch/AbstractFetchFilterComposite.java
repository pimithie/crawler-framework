package com.xiaqi.crawl.filter.fetch;

import com.xiaqi.crawl.filter.AbstractFilterComposite;

import java.util.List;

public abstract class AbstractFetchFilterComposite<T extends FetchFilter> extends AbstractFilterComposite<T> {

    public AbstractFetchFilterComposite(List<T> filters) {
        super(filters);
    }

    public boolean doFilter(FetchMetaData fetchMetaData) {
        Node<T> node = this.getFilterChain();
        while (null != node) {
            if (! node.getFilter().filter(fetchMetaData)) {
                return false;
            }
            node = node.getNext();
        }
        return true;
    }

}
