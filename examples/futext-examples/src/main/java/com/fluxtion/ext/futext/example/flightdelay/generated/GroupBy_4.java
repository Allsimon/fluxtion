package com.fluxtion.ext.futext.example.flightdelay.generated;

import com.fluxtion.api.annotations.Initialise;
import com.fluxtion.api.annotations.NoEventReference;
import com.fluxtion.api.annotations.OnParentUpdate;
import com.fluxtion.ext.futext.example.flightdelay.CarrierDelay;
import com.fluxtion.ext.futext.example.flightdelay.FlightDetails;
import com.fluxtion.ext.streaming.api.Wrapper;
import com.fluxtion.ext.streaming.api.group.AggregateFunctions.AggregateCount;
import com.fluxtion.ext.streaming.api.group.AggregateFunctions.AggregateSum;
import com.fluxtion.ext.streaming.api.group.GroupBy;
import com.fluxtion.ext.streaming.api.group.GroupByIniitialiser;
import com.fluxtion.ext.streaming.api.group.GroupByTargetMap;
import java.util.Map;

/**
 * generated group by holder.
 *
 * <p>target class : CarrierDelay
 *
 * @author Greg Higgins
 */
public final class GroupBy_4 implements GroupBy<FlightDetails, CarrierDelay> {

  @NoEventReference public Object resetNotifier;
  public Filter_getDelay_By_positiveInt0 filter_getDelay_By_positiveInt00;
  private CarrierDelay target;
  private GroupByTargetMap<FlightDetails, CarrierDelay, CalculationStateGroupBy_4> calcState;
  private GroupByIniitialiser<FlightDetails, CarrierDelay>
      initialiserfilter_getDelay_By_positiveInt00;

  @OnParentUpdate("filter_getDelay_By_positiveInt00")
  public boolean updatefilter_getDelay_By_positiveInt00(
      Filter_getDelay_By_positiveInt0 eventWrapped) {
    FlightDetails event = (FlightDetails) eventWrapped.event();
    CalculationStateGroupBy_4 instance = calcState.getOrCreateInstance(event.getCarrier());
    boolean allMatched =
        instance.processSource(1, initialiserfilter_getDelay_By_positiveInt00, event);
    target = instance.target;
    {
      double value = instance.aggregateAverage1;
      value =
          instance.aggregateAverage1Function.calcAverage((double) event.getDelay(), (double) value);
      target.setAvgDelay((int) value);
      instance.aggregateAverage1 = value;
    }
    {
      int value = instance.aggregateCount2;
      value = AggregateCount.increment((int) 0, (int) value);
      target.setTotalFlights((int) value);
      instance.aggregateCount2 = value;
    }
    {
      double value = instance.aggregateSum3;
      value = AggregateSum.calcSum((double) event.getDelay(), (double) value);
      target.setTotalDelayMins((int) value);
      instance.aggregateSum3 = value;
    }
    return allMatched;
  }

  @Initialise
  public void init() {
    calcState = new GroupByTargetMap<>(CalculationStateGroupBy_4.class);
    initialiserfilter_getDelay_By_positiveInt00 =
        new GroupByIniitialiser<FlightDetails, CarrierDelay>() {

          @Override
          public void apply(FlightDetails source, CarrierDelay target) {
            target.setCarrierId((java.lang.String) source.getCarrier());
          }
        };
  }

  @Override
  public CarrierDelay value(FlightDetails key) {
    return calcState.getInstance(key).target;
  }

  @Override
  public <V extends Wrapper<CarrierDelay>> Map<FlightDetails, V> getMap() {
    return (Map<FlightDetails, V>) calcState.getInstanceMap();
  }

  @Override
  public CarrierDelay event() {
    return target;
  }

  @Override
  public Class<CarrierDelay> eventClass() {
    return CarrierDelay.class;
  }

  public GroupBy_4 resetNotifier(Object resetNotifier) {
    this.resetNotifier = resetNotifier;
    return this;
  }

  @OnParentUpdate("resetNotifier")
  public void resetNotification(Object resetNotifier) {
    init();
  }
}
