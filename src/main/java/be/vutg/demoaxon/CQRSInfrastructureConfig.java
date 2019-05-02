package be.vutg.demoaxon;

import be.vutg.demoaxon.person.write.PersonAggregate;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.axonframework.eventsourcing.EventSourcingRepository.builder;

@Configuration
public class CQRSInfrastructureConfig {

    @Bean
    EventSourcingRepository<PersonAggregate> accountAggregateEventSourcingRepository(EventStore eventStore){
        EventSourcingRepository<PersonAggregate> repository = builder(PersonAggregate.class).eventStore(eventStore).build();
        return repository;
    }
}
