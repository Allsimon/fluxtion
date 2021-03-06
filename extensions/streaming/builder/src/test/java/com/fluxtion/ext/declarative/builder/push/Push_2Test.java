package com.fluxtion.ext.declarative.builder.push;

import com.fluxtion.api.annotations.OnEvent;
import com.fluxtion.api.event.Event;
import com.fluxtion.ext.streaming.api.Wrapper;
import static com.fluxtion.ext.streaming.api.stream.NumericPredicates.gt;
import static com.fluxtion.ext.streaming.builder.event.EventSelect.select;
import com.fluxtion.ext.streaming.builder.factory.PushBuilder;
import com.fluxtion.ext.declarative.builder.helpers.DataEvent;
import com.fluxtion.ext.declarative.builder.helpers.DealEvent;
import com.fluxtion.ext.declarative.builder.helpers.TradeEvent;
import com.fluxtion.ext.declarative.builder.stream.StreamInprocessTest;
import com.fluxtion.ext.streaming.builder.stream.StreamBuilder;
import static com.fluxtion.ext.streaming.builder.stream.StreamFunctionsBuilder.count;
import java.util.Date;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author V12 Technology Ltd.
 */
public class Push_2Test extends StreamInprocessTest {

    @Test
    public void pushNotification() {
//        fixedPkg = true;
        sep((c) -> {
            Wrapper<DealEvent> inSD = select(DealEvent.class);
            Wrapper<DataEvent> inDA = select(DataEvent.class);
            UpdateCount counter = c.addNode(new UpdateCount(), "counter");
            //push
            PushBuilder.push(inSD, counter);
            PushBuilder.push(inDA, counter);
        });

        sep.onEvent(new DealEvent());
        sep.onEvent(new DataEvent());
        sep.onEvent(new DealEvent());
        sep.onEvent(new TradeEvent());
        UpdateCount counter = getField("counter");
        Assert.assertThat(counter.count, is(3));
    }

    @Test
    public void pushNotificationDataViaStream() {
//        fixedPkg = true;
        sep((c) -> {
            Wrapper<DealEvent> inSD = select(DealEvent.class);
            Wrapper<DataEvent> inDA = select(DataEvent.class);
            UpdateCount counter = c.addNode(new UpdateCount(), "counter");
            PushTarget pushTarget = c.addNode(new PushTarget(), "target");
            //push
            PushBuilder.push(inSD, counter);
            PushBuilder.push(inDA, counter);
            //push data
            PushBuilder.push(counter::getCount, pushTarget::setVal);

            StreamBuilder.stream(pushTarget).id("streamedCumSum")
                    .filter(PushTarget::getVal, gt(25))
                    .console("[above 25]");
        });

        sep.onEvent(new DealEvent());
        sep.onEvent(new DataEvent());
        sep.onEvent(new DealEvent());
        sep.onEvent(new TradeEvent());
        UpdateCount counter = getField("counter");
        PushTarget target = getField("target");
        Assert.assertThat(counter.count, is(3));
        Assert.assertThat(target.count, is(3));
        Assert.assertThat(target.val, is(30));
    }

    @Test
    public void pushNotificationData() {
//        fixedPkg = true;
        sep((c) -> {
            Wrapper<DealEvent> inSD = select(DealEvent.class);
            Wrapper<DataEvent> inDA = select(DataEvent.class);
            UpdateCount counter = c.addNode(new UpdateCount(), "counter");
            PushTarget pushTarget = c.addNode(new PushTarget(), "target");
            //push
            PushBuilder.push(inSD, counter);
            PushBuilder.push(inDA, counter);
            //push data
            PushBuilder.push(counter::getCount, pushTarget::setVal);
            PushBuilder.pushSource(counter, pushTarget::pushUpdateCount);
            PushBuilder.pushSource(counter, pushTarget::setLongCount);

        });

        sep.onEvent(new DealEvent());
        sep.onEvent(new DataEvent());
        sep.onEvent(new DealEvent());
        sep.onEvent(new TradeEvent());
        UpdateCount counter = getField("counter");
        PushTarget target = getField("target");
        Assert.assertThat(counter.count, is(3));
        Assert.assertThat(target.count, is(3));
        Assert.assertThat(target.val, is(30));
        Assert.assertThat(target.updatePushVal, is(300));
        Assert.assertThat(target.longVal, is(3000l));
    }

    @Test
    public void pushToComplexObject() {
        sep((c) -> {
            select(LongNumber.class)
                    .push(LongNumber::getVal, c.addPublicNode(new Date(), "date")::setTime)
                    .map(count())
                    .push(c.addPublicNode(new Date(), "date_2")::setTime);
        });
        
        Date date = getField("date");
        Date date_2 = getField("date_2");
        sep.onEvent(new LongNumber((8000)));
        sep.onEvent(new LongNumber((2000)));
        sep.onEvent(new LongNumber((1000)));
        Assert.assertThat(date.getTime(), is(1000L));
        Assert.assertThat(date_2.getTime(), is(3L));
    }

    public static class LongNumber extends Event{

        long val;

        public LongNumber(long val) {
            this.val = val;
        }

        public long getVal() {
            return val;
        }

        public void setVal(long val) {
            this.val = val;
        }

    }

    public static class UpdateCount extends Number{

        public int count;


        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
        
        @Override
        public double doubleValue() {
            return getCount();
        }

        @Override
        public float floatValue() {
            return getCount();
        }

        @Override
        public int intValue() {
            return getCount();
        }

        @Override
        public long longValue() {
            return getCount();
        }

        @OnEvent
        public void update() {
            count++;
        }

    }

    public static class PushTarget {

        public int count;
        public int val;
        public int updatePushVal;
        public long longVal;

        @OnEvent
        public void update() {
            count++;
        }
        
        public void pushUpdateCount(UpdateCount update){
            this.updatePushVal = update.getCount() * 100;
        }

        public void setVal(int val) {
            this.val = val * 10;
        }
        
        public void setLongCount(long longVal){
            this.longVal = longVal * 1000;
        }

        public int getCount() {
            return count;
        }

        public int getVal() {
            return val;
        }

    }

}
