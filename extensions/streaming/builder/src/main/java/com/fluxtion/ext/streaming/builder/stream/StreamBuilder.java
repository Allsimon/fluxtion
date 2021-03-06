/* 
 * Copyright (C) 2018 V12 Technology Ltd.
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
//      com.fluxtion.ext.declarative.builder.stream.StreamBuilder 

import static com.fluxtion.ext.streaming.builder.factory.PushBuilder.unWrap;

import com.fluxtion.api.partition.LambdaReflection.SerializableBiFunction;
import com.fluxtion.api.partition.LambdaReflection.SerializableConsumer;
import com.fluxtion.api.partition.LambdaReflection.SerializableFunction;
import com.fluxtion.builder.generation.GenerationContext;
import com.fluxtion.ext.streaming.api.FilterWrapper;
import com.fluxtion.ext.streaming.api.Wrapper;
import com.fluxtion.ext.streaming.api.group.GroupBy;
import com.fluxtion.ext.streaming.api.numeric.MutableNumber;
import com.fluxtion.ext.streaming.api.numeric.NumericFunctionStateless;
import com.fluxtion.ext.streaming.api.stream.Argument;
import com.fluxtion.ext.streaming.api.stream.NodeWrapper;
import com.fluxtion.ext.streaming.api.stream.SerialisedFunctionHelper;
import com.fluxtion.ext.streaming.api.stream.SerialisedFunctionHelper.LambdaFunction;
import com.fluxtion.ext.streaming.api.stream.StreamOperator;
import com.fluxtion.ext.streaming.builder.group.Group;
import com.fluxtion.ext.streaming.builder.group.GroupByBuilder;
import com.fluxtion.ext.streaming.builder.test.BooleanBuilder;
import com.google.auto.service.AutoService;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author V12 Technology Ltd.
 */
@AutoService(StreamOperator.class)
public class StreamBuilder implements StreamOperator {

    @Override
    public <S, T> FilterWrapper<T> filter(SerializableFunction<S, Boolean> filter, Wrapper<T> source, Method accessor, boolean cast) {
        FilterBuilder builder = lambdaBuilder(filter, source, accessor, cast, false);
        if(builder==null){
            Method filterMethod = filter.method();
            if (Modifier.isStatic(filterMethod.getModifiers())) {
                builder = FilterBuilder.filter(filterMethod, source, accessor, cast);
            } else {
                builder = FilterBuilder.filter(filter.captured()[0], filterMethod, source, accessor, cast);
            }
        }
        return (FilterWrapper<T>) builder.build();
    }

    @Override
    public <T> FilterWrapper<T> filter(SerializableFunction<? extends T, Boolean> filter, Wrapper<T> source, boolean cast) {
        FilterBuilder builder = lambdaBuilder(filter, source, null, cast, false);
        if(builder==null){
            Method filterMethod = filter.method();
            if (Modifier.isStatic(filterMethod.getModifiers())) {
                builder = FilterBuilder.filter(filterMethod, source);
            } else {
                builder = FilterBuilder.filter(filter.captured()[0], filterMethod, source);
            }
        }
        return (FilterWrapper<T>) builder.build();
    }
    
    private <S, T, W>  FilterBuilder lambdaBuilder(SerializableFunction<S, T> filter, Wrapper<W> source, Method accessor, boolean cast, boolean map){
        LambdaFunction<? extends S, T> addLambda = SerialisedFunctionHelper.addLambda(filter);
        FilterBuilder builder = null;
        if(addLambda!=null){
            try {
                Method filterMethod = Function.class.getMethod("apply", Object.class);
                if(map){
                    builder = FilterBuilder.map(addLambda, filterMethod, source, accessor, cast);
                }else{
                    builder = FilterBuilder.filter(addLambda, filterMethod, source, accessor, cast);
                }
            } catch (NoSuchMethodException | SecurityException ex) {
                throw new RuntimeException("unable to map/filter lambda function", ex);
            }
        }
        return builder;
    }

    @Override
    public <K, T, S extends Number, F extends NumericFunctionStateless, R extends Number> GroupBy<K, R> group(Wrapper<T> source,
            SerializableFunction<T, S> key, Class<F> functionClass) {
        GroupByBuilder<T, MutableNumber> wcQuery = Group.groupBy(source, key, MutableNumber.class);
        wcQuery.function(functionClass, MutableNumber::set);
        return (GroupBy<K, R>) wcQuery.build();
    }

    @Override
    public <K, T, S extends Number, F extends NumericFunctionStateless, R extends Number> GroupBy<K, R> group(
            Wrapper<T> source,
            SerializableFunction<T, K> key,
            SerializableFunction<T, S> supplier,
            Class<F> functionClass) {
        GroupByBuilder<T, MutableNumber> wcQuery = Group.groupBy(source, key, MutableNumber.class);
        wcQuery.function(functionClass, supplier, MutableNumber::set);
        return (GroupBy<K, R>) wcQuery.build();
    }

    @Override
    public <T, R> Wrapper<R> map(SerializableFunction<T, R> mapper, Wrapper<T> source, boolean cast) {
        FilterBuilder builder = lambdaBuilder(mapper, source, null, cast, true);
        if(builder==null){
            Method mappingMethod = mapper.method();
            if (Modifier.isStatic(mappingMethod.getModifiers())) {
                builder = FilterBuilder.map(null, mappingMethod, source, null, true);
            } else {
                builder = FilterBuilder.map(mapper.captured()[0], mappingMethod, source, null, true);
            }
        }
        return builder.build();
    }

    @Override
    public <T, R> Wrapper<R> map(SerializableFunction<T, R> mapper, Wrapper<T> source, Method accessor, boolean cast) {
        FilterBuilder builder = lambdaBuilder(mapper, source, accessor, cast, true);
            if(builder==null){
                Method mappingMethod = mapper.method();
            if (Modifier.isStatic(mappingMethod.getModifiers())) {
                builder = FilterBuilder.map(null, mappingMethod, source, accessor, true);
            } else {
                builder = FilterBuilder.map(mapper.captured()[0], mappingMethod, source, accessor, true);
            }
        }
        return builder.build();
    }

    @Override
    public <R, S, U> Wrapper<R> map(SerializableBiFunction<? extends U, ? extends S, R> mapper,
            Argument<? extends U> arg1, Argument<? extends S> arg2) {
        Method mappingMethod = mapper.method();
        FilterBuilder builder = null;
        if (Modifier.isStatic(mappingMethod.getModifiers())) {
            builder = FilterBuilder.map(null, mappingMethod, arg1, arg2);
        } else {
            builder = FilterBuilder.map(mapper.captured()[0], mappingMethod, arg1, arg2);
        }
        return builder.build();
    }

    @Override
    public <F, R> Wrapper<R> map(F mapper, Method mappingMethod, Argument... args) {
        FilterBuilder builder = null;
        if (Modifier.isStatic(mappingMethod.getModifiers())) {
            builder = FilterBuilder.map(null, mappingMethod, args);
        } else {
            builder = FilterBuilder.map(mapper, mappingMethod, args);
        }
        return builder.build();
    }

    @Override
    public <T, R> void push(Wrapper<T> source, Method accessor, SerializableConsumer<R> consumer) {
//        final Object sourceInstance = unWrap(source);
        final Object targetInstance = unWrap(consumer);
        FilterBuilder.push(targetInstance, consumer.method(), source, accessor, true).build();
    }

    @Override
    public <T, S extends T> Wrapper<T> forEach(SerializableConsumer<S> consumer, Wrapper<T> source, String consumerId) {
        Method consumerMethod = consumer.method();
        FilterBuilder builder = null;
        if (Modifier.isStatic(consumerMethod.getModifiers())) {
            builder = FilterBuilder.consume(null, consumerMethod, source);
        } else {
            builder = FilterBuilder.consume(consumer.captured()[0], consumerMethod, source);
        }
        nodeId(builder.build(), consumerId);
        return source;
    }

    @Override
    public <T> Wrapper<T> notiferMerge(Wrapper<T> source, Object notifier) {
        return BooleanBuilder.filterEither(source, notifier);
    }

    @Override
    public <T> Wrapper<T> notifierOverride(Wrapper<T> source, Object notifier) {
        return BooleanBuilder.filter(source, notifier);
    }

    @Override
    public <T> T nodeId(T node, String name) {
        if (name == null || node == null) {
            return node;
        }
        return GenerationContext.SINGLETON.nameNode(node, name);
    }

    public static <T> Wrapper<T> stream(T node) {
        if (node instanceof Wrapper) {
            return (Wrapper) node;
        }
        Optional findAny = GenerationContext.SINGLETON.getNodeList().stream().filter(new Predicate() {
            @Override
            public boolean test(Object t) {
                boolean matched = false;
                if (t instanceof Wrapper) {
                    matched = Objects.equals(node, ((Wrapper) t).event());
                }
                return matched;
            }
        }).findAny();

        if (findAny.isPresent()) {
            return (Wrapper<T>) findAny.get();
        }
        Wrapper ret = new NodeWrapper(GenerationContext.SINGLETON.addOrUseExistingNode(node));
        GenerationContext.SINGLETON.addOrUseExistingNode(ret);
        return ret;
    }

}
