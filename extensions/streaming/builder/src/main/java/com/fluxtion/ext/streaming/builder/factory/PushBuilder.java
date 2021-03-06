package com.fluxtion.ext.streaming.builder.factory;

import com.fluxtion.api.partition.LambdaReflection.MethodReferenceReflection;
import com.fluxtion.api.partition.LambdaReflection.SerializableConsumer;
import com.fluxtion.api.partition.LambdaReflection.SerializableFunction;
import com.fluxtion.api.partition.LambdaReflection.SerializableSupplier;
import com.fluxtion.builder.generation.GenerationContext;
import com.fluxtion.ext.streaming.api.PushNotifier;
import com.fluxtion.ext.streaming.api.Wrapper;
import com.fluxtion.ext.streaming.builder.stream.FilterBuilder;

/**
 * Factory for building {@link PushNotifier} instances.
 *
 * @author V12 Technology Ltd.
 */
public class PushBuilder {

    /**
     * Adds and event graph dependency from the source to the target. The target
     * will be notified if the source is on the currently executing event path.
     *
     * @param <S>
     * @param <T>
     * @param source
     * @param target
     * @return
     */
    public static <S, T> S push(S source, T target) {
        PushNotifier p = GenerationContext.SINGLETON.addOrUseExistingNode(new PushNotifier(source, target));
        return source;
    }
        
    public static<S extends T, T > void pushSource(T source,  SerializableConsumer<S> consumer){
        final Object targetInstance = consumer.captured()[0];
        FilterBuilder.push(targetInstance, consumer.method(), source, null, true).build();
        
    }

    /**
     * Pushes data from the source method to the target method when the source
     * is on the executing event path.
     *
     * @param <S>
     * @param <D>
     * @param supplier
     * @param consumer
     */
    public static <D> void push(SerializableSupplier<D> supplier, SerializableConsumer<? extends D> consumer) {
        final Object sourceInstance = supplier.captured()[0];//unWrap(supplier);
        final Object targetInstance = consumer.captured()[0];//unWrap(consumer);
        FilterBuilder.push(targetInstance, consumer.method(), sourceInstance, supplier.method(), true).build();
    }
    
    public static <S, D> Wrapper<S> push(SerializableSupplier<D> supplier, SerializableFunction<? extends D, S>  consumer) {
        final Object sourceInstance = supplier.captured()[0];//unWrap(supplier);
        final Object targetInstance = consumer.captured()[0];//unWrap(consumer);
        return FilterBuilder.push(targetInstance, consumer.method(), sourceInstance, supplier.method(), true).build();
    }

    public static Object unWrap(MethodReferenceReflection supplier) {
        final Object sourceInstance = supplier.captured()[0];
        if (sourceInstance instanceof Wrapper) {
            return ((Wrapper) sourceInstance).event();
        } else {
            return sourceInstance;
        }
    }

}
