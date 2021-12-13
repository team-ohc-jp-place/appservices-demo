package com.redhat.demo.kogito.service;

import org.kie.kogito.rules.DataSource;
import org.kie.kogito.rules.DataStream;
import org.kie.kogito.rules.RuleUnitData;

public class EarthOrderUnit implements RuleUnitData {

    private  DataStream<EarthOrder> eventStream;

    public EarthOrderUnit(DataStream<EarthOrder> eventStream) {
        this.eventStream = eventStream;
    } 

    public EarthOrderUnit() {
        this(DataSource.createStream());
    }

    public DataStream<EarthOrder> getEventStream() {
        return eventStream;
    }
}