package be.vutg.demoaxon.person.read;

import be.vutg.demoaxon.person.write.PersonEvent;
import be.vutg.demoaxon.person.write.PersonAggregate;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static be.vutg.demoaxon.person.read.PersonQuery.Builder.newPersonQuery;

@Component
public class PersonQueryEntityManager {

    private final PersonRepository personRepository;

    private final EventSourcingRepository<PersonAggregate> personAggregateEventSourcingRepository;

    private PersonQueryEntityManager(PersonRepository personRepository,
                                     @Qualifier("accountAggregateEventSourcingRepository") EventSourcingRepository<PersonAggregate> personAggregateEventSourcingRepository){
        this.personRepository = personRepository;
        this.personAggregateEventSourcingRepository = personAggregateEventSourcingRepository;
    }

    @EventSourcingHandler
    void on(PersonEvent personEvent){
        persistAccount(buildPersonQuery(getPersonFromEvent(personEvent)));
    }

    private PersonQuery buildPersonQuery(PersonAggregate personFromEvent) {
        return newPersonQuery()
                .withUUID(personFromEvent.getUUID())
                .withInss(personFromEvent.getInss())
                .build();
    }

    private PersonAggregate getPersonFromEvent(PersonEvent personEvent) {
        return personAggregateEventSourcingRepository.load(personEvent.getAggregateId().toString()).getWrappedAggregate().getAggregateRoot();
    }

    private void persistAccount(PersonQuery personQuery){
        personRepository.save(personQuery);
    }


}
