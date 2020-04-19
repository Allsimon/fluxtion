/*
 * Copyright (c) 2020, V12 Technology Ltd.
 * All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Server Side Public License, version 1,
 * as published by MongoDB, Inc.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Server Side Public License for more details.
 *
 * You should have received a copy of the Server Side Public License
 * along with this program.  If not, see 
 * <http://www.mongodb.com/licensing/server-side-public-license>.
 */
package com.fluxtion.ext.streaming.api;

import com.fluxtion.api.SepContext;
import com.fluxtion.api.annotations.Initialise;
import com.fluxtion.api.annotations.NoEventReference;
import com.fluxtion.api.annotations.OnEvent;
import com.fluxtion.api.partition.LambdaReflection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Greg Higgins greg.higgins@v12technology.com
 * @param <T>
 */
public class ArrayListWrappedCollection<T> implements WrappedCollection<T> {

    private List<T> unmodifiableCollection;
    private final Wrapper<T> wrappedSource;
    @NoEventReference
    private Comparator comparator;
    private List<T> collection;

    public ArrayListWrappedCollection() {
        this(null);
    }

    public ArrayListWrappedCollection(Wrapper<T> wrappedSource) {
        this.wrappedSource = wrappedSource;
    }

    @Initialise
    public void init() {
        this.collection = new ArrayList<>();
        this.unmodifiableCollection = Collections.unmodifiableList(collection);
    }

    @OnEvent
    public boolean updated() {
        final T newItem = wrappedSource.event();
        addItem(newItem);
        return true;
    }

    public void addItem(final T newItem) {
        if(collection.contains(newItem))
            return;
        if (comparator != null) {
            int index = Collections.binarySearch(collection, newItem, comparator);
            if (index < 0) {
                index = ~index;
            }
            collection.add(index, newItem);
        } else {
            collection.add(newItem);
        }
    }

    @Override
    public void setComparator(Comparator comparator) {
        this.comparator = SepContext.service().addOrReuse(comparator);
    }

    @Override
    public < I extends Integer> void comparing(LambdaReflection.SerializableBiFunction<T, T, I> func) {
        System.out.println("SETTING COMPARATOR STATIC FUNCTION " + func.method().getParameters()[0].getType());
    }

    @Override
    public <R> void comparing(LambdaReflection.SerializableFunction<T, R> in) {
        System.out.println("SETTING COMPARATOR USING PROPERTY");
    }

    @Override
    public List<T> collection() {
        return unmodifiableCollection;
    }

    public List<T> subList(int fromIndex, int toIndex) {
        return collection().subList(fromIndex, toIndex);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.wrappedSource);
        hash = 97 * hash + Objects.hashCode(this.comparator);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ArrayListWrappedCollection<?> other = (ArrayListWrappedCollection<?>) obj;
        if (!Objects.equals(this.wrappedSource, other.wrappedSource)) {
            return false;
        }
        if (!Objects.equals(this.comparator, other.comparator)) {
            return false;
        }
        return true;
    }

}