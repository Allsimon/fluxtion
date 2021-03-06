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
package com.fluxtion.ext.streaming.builder.group;

import com.fluxtion.ext.streaming.api.group.GroupBy;
import com.fluxtion.builder.generation.GenerationContext;
import com.fluxtion.ext.streaming.api.Wrapper;
import static com.fluxtion.ext.streaming.api.group.AggregateFunctions.Avg;
import static com.fluxtion.ext.streaming.api.group.AggregateFunctions.Count;
import static com.fluxtion.ext.streaming.api.group.AggregateFunctions.Max;
import static com.fluxtion.ext.streaming.api.group.AggregateFunctions.Min;
import static com.fluxtion.ext.streaming.api.group.AggregateFunctions.Set;
import static com.fluxtion.ext.streaming.api.group.AggregateFunctions.Sum;
import com.fluxtion.ext.streaming.api.numeric.NumericFunctionStateful;
import com.fluxtion.ext.streaming.api.numeric.NumericFunctionStateless;
import com.fluxtion.ext.streaming.builder.group.GroupByContext.SourceContext;
import java.lang.reflect.Method;
import org.apache.commons.lang.StringUtils;
import com.fluxtion.api.event.Event;
import com.fluxtion.api.partition.LambdaReflection.SerializableBiConsumer;
import com.fluxtion.api.partition.LambdaReflection.SerializableFunction;

/**
 * The main instance a user interacts with while building a group by function. 
 *
 * @author Greg Higgins
 * @param <K> Source type
 * @param <T> Target type
 */
public class GroupByBuilder<K, T> {

    private final GroupByContext<K, T> groupBy;
    private final GroupByContext<K, T>.SourceContext<K, T> sourceContext;

    public GroupByBuilder(GroupByContext<K, T> context, SourceContext sourceContext) {
        this.groupBy = context;
        this.sourceContext = sourceContext;
    }

    public GroupByBuilder<K, T> join(K k, SerializableFunction<K, ?> f) {
        return groupBy.join(k, f);
    }

    public <K extends Event> GroupByBuilder<K, T> join(Class<K> k, SerializableFunction<K, ?> f) {
        return groupBy.join(k, f);
    }

    public <K extends Event> GroupByBuilder<K, T> join(Class<K> k, SerializableFunction<K, ?>... f) {
        return groupBy.join(k, f);
    }

    public <S> GroupByBuilder<S, T> join(Wrapper<S> k, SerializableFunction<S, ?> f) {
        return groupBy.join(k, f);
    }

    public <V> void init(SerializableFunction<K, V> valueFunction, SerializableBiConsumer<T, V> tragetFunction) {
        try {
            Method sourceMethod = valueFunction.method();
            Method targetMethod = tragetFunction.method();
            GroupByInitialiserInfo info = new GroupByInitialiserInfo(groupBy.getImportMap());
            sourceContext.setInitialiserId("initialiser" + sourceContext.sourceInfo.id);
            if (sourceContext.isWrapped()) {
                info.setSource(((Wrapper) sourceContext.keyProvider).eventClass(), sourceMethod, "source");
            } else {
                info.setSource(sourceContext.keyProvider.getClass(), sourceMethod, "source");
            }
            info.setTarget(sourceContext.targetClass, targetMethod, "target");
            sourceContext.addInitialiserFunction(info);
        } catch (Exception e) {
            //maybe numeric?
            initPrimitive((SerializableFunction<K, ? super Number>) valueFunction, (SerializableBiConsumer<T, ? super Byte>) tragetFunction);
        }
    }

    public void initPrimitive(SerializableFunction<K, ? super Number> valueFunction, SerializableBiConsumer<T, ? super Byte> tragetFunction) {
        Method sourceMethod = valueFunction.method();
        Method targetMethod = tragetFunction.method();
        GroupByInitialiserInfo info = new GroupByInitialiserInfo(groupBy.getImportMap());
        sourceContext.setInitialiserId("initialiser" + sourceContext.sourceInfo.id);
        if (sourceContext.isWrapped()) {
            info.setSource(((Wrapper) sourceContext.keyProvider).eventClass(), sourceMethod, "source");
        } else {
            info.setSource(sourceContext.keyProvider.getClass(), sourceMethod, "source");
        }
        info.setTarget(sourceContext.targetClass, targetMethod, "target");
        sourceContext.addInitialiserFunction(info);
    }

    public <F extends NumericFunctionStateless> void
            avg(SerializableFunction<K, ? extends Number> sourceFunction, SerializableBiConsumer<T, ? super Byte> target) {
        function(Avg, sourceFunction, target);
    }

    public <F extends NumericFunctionStateless> void
            count(SerializableBiConsumer<T, ? super Byte> target) {
        function(Count, target);
    }

    public <F extends NumericFunctionStateless> void
            set(SerializableFunction<K, ? extends Number> sourceFunction, SerializableBiConsumer<T, ? super Byte> target) {
        function(Set, sourceFunction, target);
    }

    public <F extends NumericFunctionStateless> void
            min(SerializableFunction<K, ? extends Number> sourceFunction, SerializableBiConsumer<T, ? super Byte> target) {
        function(Min, sourceFunction, target);
    }

    public <F extends NumericFunctionStateless> void
            max(SerializableFunction<K, ? extends Number> sourceFunction, SerializableBiConsumer<T, ? super Byte> target) {
        function(Max, sourceFunction, target);
    }

    public <F extends NumericFunctionStateless> void
            sum(SerializableFunction<K, ? extends Number> sourceFunction, SerializableBiConsumer<T, ? super Byte> target) {
        function(Sum, sourceFunction, target);
    }

    public void optional(boolean optional) {
        sourceContext.setOptional(optional);
    }

    public <F extends NumericFunctionStateless> void
            function(Class<F> calcFunctionClass, SerializableFunction<K, ? extends Number> sourceFunction, SerializableBiConsumer<T, ? super Byte> target) {
        //set function
        GroupByFunctionInfo info = new GroupByFunctionInfo(groupBy.getImportMap());
        String id = StringUtils.uncapitalize(calcFunctionClass.getSimpleName() + GenerationContext.nextId());
        info.setFunction(calcFunctionClass, checkFunction(calcFunctionClass), id);
        //set source
        Class<K> sourceClass = null;
        if (sourceContext.isWrapped()) {
            sourceClass = (Class<K>) ((Wrapper) sourceContext.keyProvider).eventClass();
        } else {
            sourceClass = (Class<K>) sourceContext.group.getInputSource().getClass();
        }
        Method sourceMethod = sourceFunction.method();//numericGetMethod(sourceClass, sourceFunction);
        info.setSource(sourceClass, sourceMethod, "event");
        //set source
        info.setTarget(sourceContext.targetClass, target.method(), "target");
        //add to context
        sourceContext.addGroupByFunctionInfo(info);
    }

    public <F extends NumericFunctionStateless> void
            function(Class<F> calcFunctionClass, SerializableBiConsumer<T, ? super Byte> target) {
        //set function
        GroupByFunctionInfo info = new GroupByFunctionInfo(groupBy.getImportMap());
        String id = StringUtils.uncapitalize(calcFunctionClass.getSimpleName() + GenerationContext.nextId());
        info.setFunction(calcFunctionClass, checkFunction(calcFunctionClass), id);
        //set target
        info.setTarget(sourceContext.targetClass, target.method(), "target");
        //add to context
        sourceContext.addGroupByFunctionInfo(info);
    }

    public GroupBy<K, T> build() {
        return groupBy.build();
    }

    public GroupBy<K, T> build(String publicName) {
        return groupBy.build();
    }

    private <F extends NumericFunctionStateless> Method checkFunction(Class<F> calcFunctionClass) {
        Method[] methods = calcFunctionClass.getDeclaredMethods();
        Method calcMethod = null;
        if (NumericFunctionStateful.class.isAssignableFrom(calcFunctionClass)) {
            if (methods.length > 2 || methods.length < 1) {
                throw new RuntimeException("Cannot generate numeric function from "
                        + "supplied function class must have minimum 1 and maximum 2"
                        + " public methods, where reset() is the second method.");
            }
            if (methods[0].getName().equalsIgnoreCase("reset")) {
                calcMethod = methods[1];
            } else {
                calcMethod = methods[0];
            }
        } else {
            if (methods.length != 1) {
                throw new RuntimeException("Cannot generate numeric function from "
                        + "supplied function class must have 1 public method.");

            }
            calcMethod = methods[0];

        }
        return calcMethod;
    }
}
