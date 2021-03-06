package com.fluxtion.ext.declarative.builder.stream;

import com.fluxtion.api.event.Event;
import com.fluxtion.ext.streaming.api.FilterWrapper;
import static com.fluxtion.ext.streaming.api.stream.NumericPredicates.gt;
import com.fluxtion.ext.streaming.api.test.Within;
import static com.fluxtion.ext.streaming.api.test.Within.within;
import static com.fluxtion.ext.streaming.builder.event.EventSelect.select;
import static com.fluxtion.ext.streaming.builder.stream.StreamFunctionsBuilder.count;
import com.fluxtion.ext.streaming.builder.test.BooleanBuilder;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Greg Higgins greg.higgins@v12technology.com
 */
public class WithinTest extends StreamInprocessTest {

    @Test
    public void withinWindow() {
//        fixedPkg = true;
        sep((c) -> {
            FilterWrapper<MyTimer> filteredTime = select(MyTimer.class)
                    .filter(MyTimer::getVal, gt(200));
            
            Within within = within(filteredTime, 1000, MyTimer::getTime);
            BooleanBuilder.filter(select(MyTimer.class), within)
                    .map(count()).id("count");

        });
        
        Number counter = getWrappedField("count");
        sep.onEvent(new MyTimer(1, 0));
        assertThat(counter.intValue(), is(0));
        sep.onEvent(new MyTimer(5, 0));
        assertThat(counter.intValue(), is(0));
        sep.onEvent(new MyTimer(10, 201));
        assertThat(counter.intValue(), is(1));
        sep.onEvent(new MyTimer(15, 10));
        assertThat(counter.intValue(), is(2));
        sep.onEvent(new MyTimer(65, 50));
        assertThat(counter.intValue(), is(3));
        sep.onEvent(new MyTimer(2065, 20));
        assertThat(counter.intValue(), is(3));
        
    }

    public static class MyTimer extends Event {

        public final int time;
        private final int val;

        public MyTimer(int time, int val) {
            this.time = time;
            this.val = val;
        }

        public int getTime() {
            return time;
        }

        public int getVal() {
            return val;
        }

    }
}
