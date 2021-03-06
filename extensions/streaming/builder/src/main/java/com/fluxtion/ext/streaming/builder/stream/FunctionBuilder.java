/*
 * Copyright (c) 2019, V12 Technology Ltd.
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
package com.fluxtion.ext.streaming.builder.stream;

import com.fluxtion.api.event.Event;
import com.fluxtion.api.partition.LambdaReflection.SerializableBiFunction;
import com.fluxtion.api.partition.LambdaReflection.SerializableFunction;
import com.fluxtion.api.partition.LambdaReflection.SerializableSupplier;
import com.fluxtion.ext.streaming.api.Wrapper;
import static com.fluxtion.ext.streaming.builder.event.EventSelect.select;
import com.fluxtion.ext.streaming.builder.factory.PushBuilder;
import com.fluxtion.ext.streaming.builder.util.FunctionArg;
import static com.fluxtion.ext.streaming.builder.util.FunctionArg.arg;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Provides helper methods to build mapping functions from nodes and Events.
 *
 * @author Greg Higgins greg.higgins@v12technology.com
 */
public class FunctionBuilder {

    public static <R, S, U> Wrapper<R> map(SerializableBiFunction<? extends U, ? extends S, R> mapper,
            FunctionArg<U> arg1,
            FunctionArg<S> arg2) {
        Method mappingMethod = mapper.method();
        FilterBuilder builder = null;
        if (Modifier.isStatic(mappingMethod.getModifiers())) {
            builder = FilterBuilder.map(null, mappingMethod, arg1, arg2);
        } else {
            builder = FilterBuilder.map(mapper.captured()[0], mappingMethod, arg1, arg2);
        }
        return builder.build();
    }

    public static <E1 extends Event, E2 extends Event, R, S, U> Wrapper<R> map(SerializableBiFunction<U, S, R> mapper,
            SerializableFunction<E1, U> supplier1,
            SerializableFunction<E2, S> supplier2) {

        FunctionArg arg1 = arg(supplier1);
        FunctionArg arg2 = arg(supplier2);
        Method mappingMethod = mapper.method();
        FilterBuilder builder = null;
        if (Modifier.isStatic(mappingMethod.getModifiers())) {
            builder = FilterBuilder.map(null, mappingMethod, arg1, arg2);
        } else {
            builder = FilterBuilder.map(mapper.captured()[0], mappingMethod, arg1, arg2);
        }
        return builder.build();
    }

    public static <T extends Event, R, S> Wrapper<R> map(SerializableFunction<S, R> mapper,
            SerializableFunction<T, S> supplier) {
        return select(supplier.getContainingClass()).map(mapper, supplier);
    }

    public static <T, R, S> Wrapper<R> map(SerializableFunction<S, R> mapper,
            SerializableSupplier<S> supplier) {
        Method m = mapper.method();
        Object captured = null;
        if (!Modifier.isStatic(m.getModifiers())) {
            captured = mapper.captured()[0];
        }
        FilterBuilder builder = FilterBuilder.map(captured, m,
                StreamBuilder.stream(supplier.captured()[0]), supplier.method(), true);
        return builder.build();
    }

    /**
     * Maps a set of nodes with a single mapping function. Only nodes that
     * notify a change are processed by the mapping function. The function will
     * consult each node on every update.
     *
     * @param <R>
     * @param <S>
     * @param mapper
     * @param suppliers
     * @return
     */
    public static <R, S> Wrapper<R> mapSet(SerializableFunction<S, R> mapper,
            FunctionArg... suppliers) {
        FilterBuilder builder = FilterBuilder.mapSet(mapper.captured()[0], mapper.method(), suppliers);
        final Wrapper wrapper = builder.build();
        wrapper.alwaysReset(true);
        return wrapper;
    }

    /**
     * Maps a set of nodes with a mapping function. Only nodes that notify in
     * the current execution path are included in the mapping function.
     *
     * @param <R>
     * @param <S>
     * @param mapper
     * @param suppliers
     * @return
     */
    public static <R, S> Wrapper<R> mapSetOnUpdate(SerializableFunction<S, R> mapper,
            SerializableSupplier<S>... suppliers) {
        //create instance of function and wrap
        final Object targetInstance = mapper.captured()[0];
        Wrapper<R> stream = (Wrapper<R>) StreamBuilder.stream(targetInstance);
        for (SerializableSupplier<S> supplier : suppliers) {
            PushBuilder.push(supplier, mapper);
        }
        return stream;
    }
}
