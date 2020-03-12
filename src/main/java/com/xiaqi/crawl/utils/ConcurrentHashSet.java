package com.xiaqi.crawl.utils;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashSet<E> extends ConcurrentHashMap<E,Object> {

    private static final Object PLACE_HOLDER = new Object();

    public ConcurrentHashSet() {
    }

    public ConcurrentHashSet(int initialCapacity) {
        super(initialCapacity);
    }

    public boolean add(E e) {
        return this.put(e,PLACE_HOLDER) == null;
    }

    public boolean containsElement(E e) {
        return this.containsKey(e);
    }

    public boolean removeElement(E e) {
        return this.remove(e) != null;
    }

}
