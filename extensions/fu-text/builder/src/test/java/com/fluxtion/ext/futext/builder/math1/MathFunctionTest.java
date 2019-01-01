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
package com.fluxtion.ext.futext.builder.math1;

import com.fluxtion.api.node.SEPConfig;
import com.fluxtion.extension.declarative.builder.event.EventSelect;
import com.fluxtion.ext.declarative.api.EventWrapper;
import com.fluxtion.ext.declarative.api.numeric.NumericResultRelay;
import com.fluxtion.ext.declarative.api.numeric.NumericResultTarget;
import static com.fluxtion.ext.futext.builder.math.MultiplyFunctions.multiply;
import static com.fluxtion.ext.futext.builder.math.AddFunctions.add;
import static com.fluxtion.ext.futext.builder.math.CumSumFunctions.cumSum;
import com.fluxtion.ext.declarative.api.numeric.NumericValue;
import com.fluxtion.ext.futext.builder.test.helpers.DataEvent;
import com.fluxtion.ext.futext.builder.test.helpers.DataEvent_2;
import com.fluxtion.generator.util.BaseSepTest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Greg Higgins
 */
public class MathFunctionTest extends BaseSepTest{

    @Test
    public void generateProcessor() throws Exception {
        buildAndInitSep(Builder.class);

    }

    @Test
    public void generateArrayProcessor() throws Exception {
        buildAndInitSep(BuilderArray.class);
    }

    @Test
    public void testIncSumArray() throws Exception {
        buildAndInitSep(BuilderSumIncArray.class);
        //add results listeners
        NumericResultTarget targetColours = new NumericResultTarget("red,green");
        NumericResultTarget targetNumbers = new NumericResultTarget("1,2,3");
        NumericResultTarget targetAnimals = new NumericResultTarget("dog,cat");
        sep.onEvent(targetColours);
        sep.onEvent(targetNumbers);
        sep.onEvent(targetAnimals);
        //fire some events for FX - ignored ny EQ 
        DataEvent de1 = new DataEvent();
        de1.setFilterString("RED");
        de1.value = 200;
        sep.onEvent(de1);
        de1.setFilterString("BLUE");
        sep.onEvent(de1);
        de1.setFilterString("GREEN");
        sep.onEvent(de1);
        de1.value = 600;
        sep.onEvent(de1);
        de1.setFilterInt(2);
        sep.onEvent(de1);

        assertThat(targetColours.getTarget().intValue(), is(1000));
        assertThat(targetNumbers.getTarget().intValue(), is(600));
        assertThat(targetAnimals.getTarget().intValue(), is(0));
        System.out.println("ResultColours:" + targetColours.getTarget().intValue());
        System.out.println("ResultNumbers:" + targetNumbers.getTarget().intValue());
        System.out.println("ResultAnimals:" + targetAnimals.getTarget().intValue());
    }

    public static class Builder extends SEPConfig {

        public Builder() throws Exception {
            EventWrapper<DataEvent> temp = EventSelect.select(DataEvent.class, "temp");
            EventWrapper<DataEvent> offset = EventSelect.select(DataEvent.class, "offset");
            add(DataEvent.class, DataEvent::getValue, DataEvent_2.class, DataEvent_2::getValue);
            multiply(temp, DataEvent::getValue, offset, DataEvent::getValue);
        }

    }

    public static class BuilderArray extends SEPConfig {

        public BuilderArray() throws Exception {
            EventWrapper<DataEvent>[] temp = EventSelect.select(DataEvent.class, "temp", "outsideTemp");
            EventWrapper<DataEvent> offset = EventSelect.select(DataEvent.class, "offset");
            add(DataEvent.class, DataEvent::getValue, DataEvent_2.class, DataEvent_2::getValue);
            multiply(temp[0], DataEvent::getValue, temp[1], DataEvent::getValue);
        }

    }

    public static class BuilderSumIncArray extends SEPConfig {

        public BuilderSumIncArray() {
            NumericValue sumFx = cumSum(DataEvent.class, DataEvent::getValue, "RED", "GREEN");
            NumericValue sumEq = cumSum(DataEvent.class, DataEvent::getValue, 1, 2, 3);
            //results collector for testing
            addNode(new NumericResultRelay("red,green", sumFx));
            addNode(new NumericResultRelay("1,2,3", sumEq));
        }

    }
}
