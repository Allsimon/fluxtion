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
package com.fluxtion.generator;

/**
 *
 * @author Greg Higgins (greg.higgins@V12technology.com)
 */
public class Templates {

    private static final String PACKAGE = "template/base";
    public static final String JAVA_TEMPLATE = PACKAGE + "/javaTemplate.vsl";
    public static final String JAVA_DEBUG_TEMPLATE = PACKAGE + "/javaTemplateDebug.vsl";
    public static final String JAVA_INTROSPECTOR_TEMPLATE = PACKAGE + "/javaIntrospectorTemplate.vsl";
    public static final String JAVA_TEST_DECORATOR_TEMPLATE = PACKAGE + "/javaTestDecoratorTemplate.vsl";
}
