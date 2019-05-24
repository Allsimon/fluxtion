/*
 * Copyright (C) 2019 V12 Technology Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Server Side Public License, version 1,
 * as published by MongoDB, Inc.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Server Side License for more details.
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
import com.fluxtion.ext.streaming.api.stream.StreamFunctions;
import com.fluxtion.ext.streaming.api.stream.StreamFunctions.Average;
import com.fluxtion.ext.streaming.api.stream.StreamFunctions.Count;
import com.fluxtion.ext.streaming.api.stream.StreamFunctions.Max;
import com.fluxtion.ext.streaming.api.stream.StreamFunctions.Min;
import com.fluxtion.ext.streaming.api.stream.StreamFunctions.PercentDelta;
import com.fluxtion.ext.streaming.api.stream.StreamFunctions.Sum;
import com.fluxtion.ext.streaming.builder.stream.FilterBuilder;
import com.fluxtion.ext.streaming.builder.util.FunctionArg;
import static com.fluxtion.ext.streaming.builder.event.EventSelect.*;
import static com.fluxtion.ext.streaming.builder.stream.FunctionBuilder.*;
import static com.fluxtion.ext.streaming.builder.stream.StreamBuilder.*;
import static com.fluxtion.ext.streaming.builder.util.FunctionArg.*;

/**
 * Utility class providing static helper methods to create mapping operations
 * in streams from a set of wrapped functions.
 *
 * This class is autogenerated from executing {@link StreamFunctionGenerator}
 *
 * @author Greg Higgins
 */
public class StreamFunctionsBuilder  {


    public static <T extends Double, S extends Double> SerializableBiFunction<T, S, Number> add() {
        return StreamFunctions::add;
    }

    public static <T, S> Wrapper<Number> add(SerializableFunction<T, Number> supplier1, SerializableFunction<S, Number> supplier2) {
        return FilterBuilder.map(StreamFunctions::add, arg(supplier1), arg(supplier2));
    }

    public static <T extends Number, S extends Number> Wrapper<Number> add(SerializableSupplier<T> supplier1, SerializableSupplier<T> supplier2) {
        return FilterBuilder.map(StreamFunctions::add, arg(supplier1), arg(supplier2));
    }

    public static <T extends Number, S extends Number> Wrapper<Number> add(FunctionArg<T> arg1, FunctionArg<S> arg2) {
        return FilterBuilder.map(StreamFunctions::add, arg1, arg2);
    }

    public static <T, S extends Number, R extends Number> Wrapper<Number> add(Wrapper<T> wrapper, SerializableFunction<T, S> supplier1, SerializableFunction<T, R> supplier2) {
        return FilterBuilder.map(StreamFunctions::add, arg(wrapper, supplier1), arg(wrapper, supplier2));
    }

    public static <T, U, S extends Number, R extends Number> Wrapper<Number> add(Wrapper<T> wrapper1, SerializableFunction<T, S> supplier1, Wrapper<U> wrapper2, SerializableFunction<U, R> supplier2) {
        return FilterBuilder.map(StreamFunctions::add, arg(wrapper1, supplier1), arg(wrapper2, supplier2));
    }

    public static <T extends Number, S extends Number> Wrapper<Number> add(Wrapper<T> wrapper1, Wrapper<S> wrapper2) {
        return FilterBuilder.map(StreamFunctions::add, arg(wrapper1), arg(wrapper2));
    }


    public static <T extends Double, S extends Double> SerializableBiFunction<T, S, Number> subtract() {
        return StreamFunctions::subtract;
    }

    public static <T, S> Wrapper<Number> subtract(SerializableFunction<T, Number> supplier1, SerializableFunction<S, Number> supplier2) {
        return FilterBuilder.map(StreamFunctions::subtract, arg(supplier1), arg(supplier2));
    }

    public static <T extends Number, S extends Number> Wrapper<Number> subtract(SerializableSupplier<T> supplier1, SerializableSupplier<T> supplier2) {
        return FilterBuilder.map(StreamFunctions::subtract, arg(supplier1), arg(supplier2));
    }

    public static <T extends Number, S extends Number> Wrapper<Number> subtract(FunctionArg<T> arg1, FunctionArg<S> arg2) {
        return FilterBuilder.map(StreamFunctions::subtract, arg1, arg2);
    }

    public static <T, S extends Number, R extends Number> Wrapper<Number> subtract(Wrapper<T> wrapper, SerializableFunction<T, S> supplier1, SerializableFunction<T, R> supplier2) {
        return FilterBuilder.map(StreamFunctions::subtract, arg(wrapper, supplier1), arg(wrapper, supplier2));
    }

    public static <T, U, S extends Number, R extends Number> Wrapper<Number> subtract(Wrapper<T> wrapper1, SerializableFunction<T, S> supplier1, Wrapper<U> wrapper2, SerializableFunction<U, R> supplier2) {
        return FilterBuilder.map(StreamFunctions::subtract, arg(wrapper1, supplier1), arg(wrapper2, supplier2));
    }

    public static <T extends Number, S extends Number> Wrapper<Number> subtract(Wrapper<T> wrapper1, Wrapper<S> wrapper2) {
        return FilterBuilder.map(StreamFunctions::subtract, arg(wrapper1), arg(wrapper2));
    }


    public static <T extends Double, S extends Double> SerializableBiFunction<T, S, Number> multiply() {
        return StreamFunctions::multiply;
    }

    public static <T, S> Wrapper<Number> multiply(SerializableFunction<T, Number> supplier1, SerializableFunction<S, Number> supplier2) {
        return FilterBuilder.map(StreamFunctions::multiply, arg(supplier1), arg(supplier2));
    }

    public static <T extends Number, S extends Number> Wrapper<Number> multiply(SerializableSupplier<T> supplier1, SerializableSupplier<T> supplier2) {
        return FilterBuilder.map(StreamFunctions::multiply, arg(supplier1), arg(supplier2));
    }

    public static <T extends Number, S extends Number> Wrapper<Number> multiply(FunctionArg<T> arg1, FunctionArg<S> arg2) {
        return FilterBuilder.map(StreamFunctions::multiply, arg1, arg2);
    }

    public static <T, S extends Number, R extends Number> Wrapper<Number> multiply(Wrapper<T> wrapper, SerializableFunction<T, S> supplier1, SerializableFunction<T, R> supplier2) {
        return FilterBuilder.map(StreamFunctions::multiply, arg(wrapper, supplier1), arg(wrapper, supplier2));
    }

    public static <T, U, S extends Number, R extends Number> Wrapper<Number> multiply(Wrapper<T> wrapper1, SerializableFunction<T, S> supplier1, Wrapper<U> wrapper2, SerializableFunction<U, R> supplier2) {
        return FilterBuilder.map(StreamFunctions::multiply, arg(wrapper1, supplier1), arg(wrapper2, supplier2));
    }

    public static <T extends Number, S extends Number> Wrapper<Number> multiply(Wrapper<T> wrapper1, Wrapper<S> wrapper2) {
        return FilterBuilder.map(StreamFunctions::multiply, arg(wrapper1), arg(wrapper2));
    }


    public static <T extends Double, S extends Double> SerializableBiFunction<T, S, Number> divide() {
        return StreamFunctions::divide;
    }

    public static <T, S> Wrapper<Number> divide(SerializableFunction<T, Number> supplier1, SerializableFunction<S, Number> supplier2) {
        return FilterBuilder.map(StreamFunctions::divide, arg(supplier1), arg(supplier2));
    }

    public static <T extends Number, S extends Number> Wrapper<Number> divide(SerializableSupplier<T> supplier1, SerializableSupplier<T> supplier2) {
        return FilterBuilder.map(StreamFunctions::divide, arg(supplier1), arg(supplier2));
    }

    public static <T extends Number, S extends Number> Wrapper<Number> divide(FunctionArg<T> arg1, FunctionArg<S> arg2) {
        return FilterBuilder.map(StreamFunctions::divide, arg1, arg2);
    }

    public static <T, S extends Number, R extends Number> Wrapper<Number> divide(Wrapper<T> wrapper, SerializableFunction<T, S> supplier1, SerializableFunction<T, R> supplier2) {
        return FilterBuilder.map(StreamFunctions::divide, arg(wrapper, supplier1), arg(wrapper, supplier2));
    }

    public static <T, U, S extends Number, R extends Number> Wrapper<Number> divide(Wrapper<T> wrapper1, SerializableFunction<T, S> supplier1, Wrapper<U> wrapper2, SerializableFunction<U, R> supplier2) {
        return FilterBuilder.map(StreamFunctions::divide, arg(wrapper1, supplier1), arg(wrapper2, supplier2));
    }

    public static <T extends Number, S extends Number> Wrapper<Number> divide(Wrapper<T> wrapper1, Wrapper<S> wrapper2) {
        return FilterBuilder.map(StreamFunctions::divide, arg(wrapper1), arg(wrapper2));
    }


    /**
     * Wrap {@link Sum#addValue } function for use as a map operation in an existing
     * stream. {@link Wrapper#map(SerializableFunction) }
     * requires a {@link SerializableFunction} to map input values.
     *
     * @param <T> input to {@link Sum#addValue }
     * @return {@link SerializableFunction} of {@link Sum#addValue }
     */
    public static <T extends Double> SerializableFunction<T, Number> cumSum() {
        return new Sum()::addValue;
    }

    /**
     * Performs a {@link Sum#addValue} function as a map operation on a stream.
     * The stream is automatically created by subscribing to the {@link Event}
     * and wrapping the supplier function with {@link Wrapper&lt;Number&gt;}. 
     * The wrapper is the input to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input event stream
     * @param supplier The input value to the function {@link Sum#addValue
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Sum#addValue}
     */
    public static <T> Wrapper<Number> cumSum(SerializableFunction<T, Number> supplier) {
        return FilterBuilder.map(new Sum()::addValue, arg(supplier));
    }

    public static <T extends Number> Wrapper<Number> cumSum(FunctionArg<T> arg) {
        return FilterBuilder.map(new Sum()::addValue, arg);
    }

    /**
     * Performs a {@link Sum#addValue} function as a map operation on a stream.
     * The stream is automatically created by wrapping the supplier instance function in a
     * {@link Wrapper&lt;Number&gt;}, the wrapper is the input 
     * to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input type required by {@link Sum#addValue}
     * @param supplier The wrapped instance supplying values to the function {@link Sum#addValue
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Sum#addValue}
     */
    public static <T extends Number> Wrapper<Number> cumSum(SerializableSupplier<T> supplier) {
        return FilterBuilder.map(new Sum()::addValue, arg(supplier));
    }

    public static <T, S extends Number> Wrapper<Number> cumSum(Wrapper<T> wrapper, SerializableFunction<T, S> supplier) {
        return FilterBuilder.map(new Sum()::addValue,  arg(wrapper, supplier));
    }

    public static <T extends Number> Wrapper<Number> cumSum(Wrapper<T> wrapper) {
        return FilterBuilder.map(new Sum()::addValue,  arg(wrapper));
    }

    /**
     * Wrap {@link Average#addValue } function for use as a map operation in an existing
     * stream. {@link Wrapper#map(SerializableFunction) }
     * requires a {@link SerializableFunction} to map input values.
     *
     * @param <T> input to {@link Average#addValue }
     * @return {@link SerializableFunction} of {@link Average#addValue }
     */
    public static <T extends Double> SerializableFunction<T, Number> avg() {
        return new Average()::addValue;
    }

    /**
     * Performs a {@link Average#addValue} function as a map operation on a stream.
     * The stream is automatically created by subscribing to the {@link Event}
     * and wrapping the supplier function with {@link Wrapper&lt;Number&gt;}. 
     * The wrapper is the input to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input event stream
     * @param supplier The input value to the function {@link Average#addValue
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Average#addValue}
     */
    public static <T> Wrapper<Number> avg(SerializableFunction<T, Number> supplier) {
        return FilterBuilder.map(new Average()::addValue, arg(supplier));
    }

    public static <T extends Number> Wrapper<Number> avg(FunctionArg<T> arg) {
        return FilterBuilder.map(new Average()::addValue, arg);
    }

    /**
     * Performs a {@link Average#addValue} function as a map operation on a stream.
     * The stream is automatically created by wrapping the supplier instance function in a
     * {@link Wrapper&lt;Number&gt;}, the wrapper is the input 
     * to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input type required by {@link Average#addValue}
     * @param supplier The wrapped instance supplying values to the function {@link Average#addValue
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Average#addValue}
     */
    public static <T extends Number> Wrapper<Number> avg(SerializableSupplier<T> supplier) {
        return FilterBuilder.map(new Average()::addValue, arg(supplier));
    }

    public static <T, S extends Number> Wrapper<Number> avg(Wrapper<T> wrapper, SerializableFunction<T, S> supplier) {
        return FilterBuilder.map(new Average()::addValue,  arg(wrapper, supplier));
    }

    public static <T extends Number> Wrapper<Number> avg(Wrapper<T> wrapper) {
        return FilterBuilder.map(new Average()::addValue,  arg(wrapper));
    }

    /**
     * Wrap {@link Max#max } function for use as a map operation in an existing
     * stream. {@link Wrapper#map(SerializableFunction) }
     * requires a {@link SerializableFunction} to map input values.
     *
     * @param <T> input to {@link Max#max }
     * @return {@link SerializableFunction} of {@link Max#max }
     */
    public static <T extends Double> SerializableFunction<T, Number> max() {
        return new Max()::max;
    }

    /**
     * Performs a {@link Max#max} function as a map operation on a stream.
     * The stream is automatically created by subscribing to the {@link Event}
     * and wrapping the supplier function with {@link Wrapper&lt;Number&gt;}. 
     * The wrapper is the input to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input event stream
     * @param supplier The input value to the function {@link Max#max
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Max#max}
     */
    public static <T> Wrapper<Number> max(SerializableFunction<T, Number> supplier) {
        return FilterBuilder.map(new Max()::max, arg(supplier));
    }

    public static <T extends Number> Wrapper<Number> max(FunctionArg<T> arg) {
        return FilterBuilder.map(new Max()::max, arg);
    }

    /**
     * Performs a {@link Max#max} function as a map operation on a stream.
     * The stream is automatically created by wrapping the supplier instance function in a
     * {@link Wrapper&lt;Number&gt;}, the wrapper is the input 
     * to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input type required by {@link Max#max}
     * @param supplier The wrapped instance supplying values to the function {@link Max#max
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Max#max}
     */
    public static <T extends Number> Wrapper<Number> max(SerializableSupplier<T> supplier) {
        return FilterBuilder.map(new Max()::max, arg(supplier));
    }

    public static <T, S extends Number> Wrapper<Number> max(Wrapper<T> wrapper, SerializableFunction<T, S> supplier) {
        return FilterBuilder.map(new Max()::max,  arg(wrapper, supplier));
    }

    public static <T extends Number> Wrapper<Number> max(Wrapper<T> wrapper) {
        return FilterBuilder.map(new Max()::max,  arg(wrapper));
    }

    /**
     * Wrap {@link Min#min } function for use as a map operation in an existing
     * stream. {@link Wrapper#map(SerializableFunction) }
     * requires a {@link SerializableFunction} to map input values.
     *
     * @param <T> input to {@link Min#min }
     * @return {@link SerializableFunction} of {@link Min#min }
     */
    public static <T extends Double> SerializableFunction<T, Number> min() {
        return new Min()::min;
    }

    /**
     * Performs a {@link Min#min} function as a map operation on a stream.
     * The stream is automatically created by subscribing to the {@link Event}
     * and wrapping the supplier function with {@link Wrapper&lt;Number&gt;}. 
     * The wrapper is the input to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input event stream
     * @param supplier The input value to the function {@link Min#min
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Min#min}
     */
    public static <T> Wrapper<Number> min(SerializableFunction<T, Number> supplier) {
        return FilterBuilder.map(new Min()::min, arg(supplier));
    }

    public static <T extends Number> Wrapper<Number> min(FunctionArg<T> arg) {
        return FilterBuilder.map(new Min()::min, arg);
    }

    /**
     * Performs a {@link Min#min} function as a map operation on a stream.
     * The stream is automatically created by wrapping the supplier instance function in a
     * {@link Wrapper&lt;Number&gt;}, the wrapper is the input 
     * to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input type required by {@link Min#min}
     * @param supplier The wrapped instance supplying values to the function {@link Min#min
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Min#min}
     */
    public static <T extends Number> Wrapper<Number> min(SerializableSupplier<T> supplier) {
        return FilterBuilder.map(new Min()::min, arg(supplier));
    }

    public static <T, S extends Number> Wrapper<Number> min(Wrapper<T> wrapper, SerializableFunction<T, S> supplier) {
        return FilterBuilder.map(new Min()::min,  arg(wrapper, supplier));
    }

    public static <T extends Number> Wrapper<Number> min(Wrapper<T> wrapper) {
        return FilterBuilder.map(new Min()::min,  arg(wrapper));
    }

    /**
     * Wrap {@link PercentDelta#value } function for use as a map operation in an existing
     * stream. {@link Wrapper#map(SerializableFunction) }
     * requires a {@link SerializableFunction} to map input values.
     *
     * @param <T> input to {@link PercentDelta#value }
     * @return {@link SerializableFunction} of {@link PercentDelta#value }
     */
    public static <T extends Double> SerializableFunction<T, Number> percentChange() {
        return new PercentDelta()::value;
    }

    /**
     * Performs a {@link PercentDelta#value} function as a map operation on a stream.
     * The stream is automatically created by subscribing to the {@link Event}
     * and wrapping the supplier function with {@link Wrapper&lt;Number&gt;}. 
     * The wrapper is the input to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input event stream
     * @param supplier The input value to the function {@link PercentDelta#value
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link PercentDelta#value}
     */
    public static <T> Wrapper<Number> percentChange(SerializableFunction<T, Number> supplier) {
        return FilterBuilder.map(new PercentDelta()::value, arg(supplier));
    }

    public static <T extends Number> Wrapper<Number> percentChange(FunctionArg<T> arg) {
        return FilterBuilder.map(new PercentDelta()::value, arg);
    }

    /**
     * Performs a {@link PercentDelta#value} function as a map operation on a stream.
     * The stream is automatically created by wrapping the supplier instance function in a
     * {@link Wrapper&lt;Number&gt;}, the wrapper is the input 
     * to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input type required by {@link PercentDelta#value}
     * @param supplier The wrapped instance supplying values to the function {@link PercentDelta#value
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link PercentDelta#value}
     */
    public static <T extends Number> Wrapper<Number> percentChange(SerializableSupplier<T> supplier) {
        return FilterBuilder.map(new PercentDelta()::value, arg(supplier));
    }

    public static <T, S extends Number> Wrapper<Number> percentChange(Wrapper<T> wrapper, SerializableFunction<T, S> supplier) {
        return FilterBuilder.map(new PercentDelta()::value,  arg(wrapper, supplier));
    }

    public static <T extends Number> Wrapper<Number> percentChange(Wrapper<T> wrapper) {
        return FilterBuilder.map(new PercentDelta()::value,  arg(wrapper));
    }

    /**
     * Wrap {@link Math#ceil } function for use as a map operation in an existing
     * stream. {@link Wrapper#map(SerializableFunction) }
     * requires a {@link SerializableFunction} to map input values.
     *
     * @param <T> input to {@link Math#ceil }
     * @return {@link SerializableFunction} of {@link Math#ceil }
     */
    public static <T extends Double> SerializableFunction<T, Number> ceil() {
        return Math::ceil;
    }

    /**
     * Performs a {@link Math#ceil} function as a map operation on a stream.
     * The stream is automatically created by subscribing to the {@link Event}
     * and wrapping the supplier function with {@link Wrapper&lt;Number&gt;}. 
     * The wrapper is the input to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input event stream
     * @param supplier The input value to the function {@link Math#ceil
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Math#ceil}
     */
    public static <T> Wrapper<Number> ceil(SerializableFunction<T, Number> supplier) {
        return FilterBuilder.map(Math::ceil, arg(supplier));
    }

    public static <T extends Number> Wrapper<Number> ceil(FunctionArg<T> arg) {
        return FilterBuilder.map(Math::ceil, arg);
    }

    /**
     * Performs a {@link Math#ceil} function as a map operation on a stream.
     * The stream is automatically created by wrapping the supplier instance function in a
     * {@link Wrapper&lt;Number&gt;}, the wrapper is the input 
     * to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input type required by {@link Math#ceil}
     * @param supplier The wrapped instance supplying values to the function {@link Math#ceil
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Math#ceil}
     */
    public static <T extends Number> Wrapper<Number> ceil(SerializableSupplier<T> supplier) {
        return FilterBuilder.map(Math::ceil, arg(supplier));
    }

    public static <T, S extends Number> Wrapper<Number> ceil(Wrapper<T> wrapper, SerializableFunction<T, S> supplier) {
        return FilterBuilder.map(Math::ceil,  arg(wrapper, supplier));
    }

    public static <T extends Number> Wrapper<Number> ceil(Wrapper<T> wrapper) {
        return FilterBuilder.map(Math::ceil,  arg(wrapper));
    }

    /**
     * Wrap {@link Math#floor } function for use as a map operation in an existing
     * stream. {@link Wrapper#map(SerializableFunction) }
     * requires a {@link SerializableFunction} to map input values.
     *
     * @param <T> input to {@link Math#floor }
     * @return {@link SerializableFunction} of {@link Math#floor }
     */
    public static <T extends Double> SerializableFunction<T, Number> floor() {
        return Math::floor;
    }

    /**
     * Performs a {@link Math#floor} function as a map operation on a stream.
     * The stream is automatically created by subscribing to the {@link Event}
     * and wrapping the supplier function with {@link Wrapper&lt;Number&gt;}. 
     * The wrapper is the input to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input event stream
     * @param supplier The input value to the function {@link Math#floor
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Math#floor}
     */
    public static <T> Wrapper<Number> floor(SerializableFunction<T, Number> supplier) {
        return FilterBuilder.map(Math::floor, arg(supplier));
    }

    public static <T extends Number> Wrapper<Number> floor(FunctionArg<T> arg) {
        return FilterBuilder.map(Math::floor, arg);
    }

    /**
     * Performs a {@link Math#floor} function as a map operation on a stream.
     * The stream is automatically created by wrapping the supplier instance function in a
     * {@link Wrapper&lt;Number&gt;}, the wrapper is the input 
     * to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input type required by {@link Math#floor}
     * @param supplier The wrapped instance supplying values to the function {@link Math#floor
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Math#floor}
     */
    public static <T extends Number> Wrapper<Number> floor(SerializableSupplier<T> supplier) {
        return FilterBuilder.map(Math::floor, arg(supplier));
    }

    public static <T, S extends Number> Wrapper<Number> floor(Wrapper<T> wrapper, SerializableFunction<T, S> supplier) {
        return FilterBuilder.map(Math::floor,  arg(wrapper, supplier));
    }

    public static <T extends Number> Wrapper<Number> floor(Wrapper<T> wrapper) {
        return FilterBuilder.map(Math::floor,  arg(wrapper));
    }


    /**
     * Wrap {@link Count#increment } function for use as a map operation in an existing
     * stream. {@link Wrapper#map(SerializableFunction) }
     * requires a {@link SerializableFunction} to map input values.
     *
     * @param <T> input to {@link Count#increment }
     * @return {@link SerializableFunction} of {@link Count#increment }
     */
    public static <T> SerializableFunction<T, Number> count() {
        return new Count()::increment;
    }

    /**
     * Performs a {@link Count#increment} function as a map operation on a stream.
     * The stream is automatically created by subscribing to the {@link Event}
     * and wrapping the supplier function with {@link Wrapper&lt;T&gt;}. 
     * The wrapper is the input to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input event stream
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Count#increment}
     */
    public static <T extends Event> Wrapper<Number> count(Class<T> eventClass) {
        return select(eventClass).map(new Count()::increment);
    }

    /**
     * Performs a {@link Count#increment} function as a map operation on a stream.
     * The stream is automatically created by wrapping the supplier instance function in a
     * {@link Wrapper&lt;T&gt;}, the wrapper is the input 
     * to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input type required by {@link Count#increment}
     * @param supplier The wrapped instance supplying values to the function {@link Count#increment
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Count#increment}
     */
    public static <T> Wrapper<Number> count(T supplier) {
        return stream(supplier).map(new Count()::increment);
    }


}

