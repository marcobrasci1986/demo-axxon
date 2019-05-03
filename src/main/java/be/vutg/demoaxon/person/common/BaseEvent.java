package be.vutg.demoaxon.person.common;

public interface BaseEvent<AGGREGATE_ID> {
    AGGREGATE_ID getAggregateId();
}
