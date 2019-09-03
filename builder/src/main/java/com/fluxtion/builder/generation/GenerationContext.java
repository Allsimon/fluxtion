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
package com.fluxtion.builder.generation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Data;
import net.openhft.compiler.CachedCompiler;

/**
 * Context for the generated output of the SEP. Provides functions to control
 * generation outputs from Fluxtion, but is not used to describe the graph
 * processing structure of a SEP.
 *
 * @author Greg Higgins
 */
@Data
public class GenerationContext {

  public static GenerationContext SINGLETON;
  private final AtomicInteger COUNT = new AtomicInteger();
  private final Map<? super Object, Map> cacheMap;
  /**
   * the output directory for the code generation
   */
  private final File resourcesRootDirectory;
  /**
   * Map representing the name of the actual node class generated at SEP
   * processing stage. Allowing a generic proxy class to be used in the SEP
   * model processing phase and then replaced with the actual class reference
   * during the code generation phase. The real class name is only known after
   * the proxy has been generated.
   */
  private final Map<Object, String> proxyClassMap = new HashMap<>();
  /**
   * Nodes that are to be added to the SEP
   */
  private final List<Object> nodeList = new ArrayList<>();
  /**
   * public named nodes to be added to the generated SEP
   */
  private final Map<Object, String> publicNodes = new HashMap<>();
  private final ClassLoader classLoader;
  /**
   * Output package for the generated file, used where relevant
   */
  private final String packageName;
  /**
   * Class name for the generated output file
   */
  private final String sepClassName;
  /**
   * the root output directory for the code generation
   */
  private final File sourceRootDirectory;
  private final File resourcesOutputDirectory;
  private final CachedCompiler javaCompiler;
  /**
   * The package directory = outputDirectory + packageName
   */
  private final File packageDirectory;

  private GenerationContext(ClassLoader classLoader, String packageName, String sepClassName, File outputDirectory,
      File resourcesRootDirectory, File buildOutputDirectory, CachedCompiler cachedCompiler) {
    this.packageDirectory = outputDirectory == null ? null : new File(outputDirectory, packageName.replace(".", "/"));
    this.resourcesOutputDirectory = new File(resourcesRootDirectory, packageName.replace(".", "/"));
    this.packageName = packageName;
    this.sepClassName = sepClassName;
    this.sourceRootDirectory = outputDirectory;
    this.resourcesRootDirectory = resourcesRootDirectory;
    this.classLoader = classLoader;
    this.javaCompiler = cachedCompiler != null ? cachedCompiler : new CachedCompiler(null, buildOutputDirectory);
    this.cacheMap = new HashMap<>();
  }

  public static void setupStaticContext(String packageName, String className, File outputDirectory,
      File resourcesRootDirectory) {
    SINGLETON = new GenerationContext(null, packageName, className, outputDirectory, resourcesRootDirectory, null,
        null);
  }

  /**
   * A global counter, can be used for generating unique class names.
   *
   * @return next id.
   */
  public int nextId() {
    return COUNT.getAndIncrement();
  }

  public int nextId(String className) {
    Map<String, Integer> classCount = cacheMap.computeIfAbsent(X.class, k -> new HashMap<>());
    String key = packageName + "." + className;
    return classCount.compute(key, (k, v) -> v == null ? 0 : v + 1);
  }

  public <T> T addOrUseExistingNode(T node) {
    if (getNodeList().contains(node)) {
      return (T) getNodeList().get(getNodeList().indexOf(node));
    }
    getNodeList().add(node);
    return node;
  }

  /**
   * a cache that is tied to this generation context instance. A new Map will
   * be created for each unique cache key.
   *
   * @param <K> The key type of the cache map
   * @param <V> The value type of the cache map
   * @param key the cache key
   * @return the newly created map
   */
  public <K, V> Map<K, V> getCache(K key) {
    return cacheMap.computeIfAbsent(key, (k) -> new HashMap());
  }

  public <T> T nameNode(T node, String name) {
    publicNodes.put(node, name);
    return node;
  }

  /**
   * removes a cache map from this instance by key.
   *
   * @param <K> The key type of the cache map
   * @param <V> The value type of the cache map
   * @param key the cache key
   * @return The mapping of the map removed or null if no mapping
   */
  public <K, V> Map<K, V> removeCache(Object key) {
    return cacheMap.remove(key);
  }

  private static class X {}
}
