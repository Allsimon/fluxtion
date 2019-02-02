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
package com.fluxtion.builder.annotation;

import com.fluxtion.api.lifecycle.EventHandler;
import com.fluxtion.builder.node.SEPConfig;
import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.CLASS;
import java.lang.annotation.Target;

/**
 *
 * Mark a method as a SEP builder. The method must accept one argument of type
 * {@link SEPConfig}. The receiving method will use the SEPConfig instance to
 * define the static event processor that Fluxtion will generate.
 *
 * @author V12 Technology Ltd.
 */
@Target(value = {METHOD})
@Retention(value = CLASS)
@Documented
public @interface SepBuilder {

    /**
     * the name of the package the generated artifacts will be written to.
     *
     * @return package name
     */
    String packageName();

    /**
     * The name of the generated {@link EventHandler}
     *
     * @return event handler name
     */
    String name();

    /**
     * Output directory for Fluxtion generated source artifacts. The default
     * directory is supplied in the generation process, setting this value
     * overrides the default. Using maven the typical values are:
     * <ul>
     * <li>target/generated-sources/fluxtion
     * <li>target/generated-test-sources/fluxtion
     * <li>main/java
     * </ul>
     *
     * @return overridden output directory
     */
    String outputDir() default "";

    /**
     * Output directory for generated meta-data describing the static event
     * processor typical. The default directory is supplied in the generation
     * process, setting this value overrides the default. Using maven typical
     * values are:
     * <ul>
     * <li>main/resources
     * <li>test/resources
     * </ul>
     *
     * @return overridden resource directory
     */
    String resourceDir() default "";
}
