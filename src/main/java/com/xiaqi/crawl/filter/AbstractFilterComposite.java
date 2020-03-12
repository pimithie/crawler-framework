package com.xiaqi.crawl.filter;

import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * the composite of filter chain
 * @param <T> the actual type of filter
 */
public abstract class AbstractFilterComposite<T extends Filter> {

    private Node<T> filterChain;

    public AbstractFilterComposite(List<T> filters) {
        if (! CollectionUtils.isEmpty(filters)) {
            this.transferListToChain(filters);
        }
    }

    /**
     * extract to a common util class to
     * come into being a filter chain
     */
    private void transferListToChain(List<T> urlExtractFilters) {
        // current chain is empty
        int start = 0;
        if (null == filterChain) {
            filterChain = new Node<T>(urlExtractFilters.get(0));
            start++;
        }
        Node<T> current = filterChain;
        for (int i = start,size = urlExtractFilters.size(); i < size;i++) {
            Node<T> node = new Node<T>(urlExtractFilters.get(i));
            current.next = node;
            current = current.next;
        }
    }

    protected Node<T> getFilterChain() {
        return filterChain;
    }

    protected static class Node<T extends Filter> {

        T filter;

        Node<T> next;

        public T getFilter() {
            return filter;
        }

        public Node<T> getNext() {
            return next;
        }

        Node(T filter) {
            this.filter = filter;
        }

        public Node(T filter, Node<T> next) {
            this.filter = filter;
            this.next = next;
        }
    }


}
