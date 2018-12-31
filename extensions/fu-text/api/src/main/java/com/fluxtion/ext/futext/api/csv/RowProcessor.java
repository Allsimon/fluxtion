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
package com.fluxtion.ext.futext.api.csv;

import com.fluxtion.extension.declarative.api.Wrapper;

/**
 * Interface implemented by nodes processing delimited or fixed length records
 * into structured data.
 *
 * @author gregp
 * @param <T> The target type of the processor
 */
public interface RowProcessor<T> extends Wrapper<T> {

    /**
     * The numbers of rows processed, 1 indexed.
     *
     * @return number of rows processed
     */
    int rowCount();

    /**
     * Indicates whether the row passed the validator attached to the target
     * type. A target type can annotate a method
     * <pre>@OnEvent</pre> a boolean return type indicates whether this is a
     * validating method.
     *
     * @return validation was successful, true indicates success.
     */
    boolean passedValidation();

    /**
     * The end of record character the processor uses.
     *
     * @return the eol character for a record
     */
    char eolChar();
}