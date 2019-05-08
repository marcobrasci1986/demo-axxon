package be.vutg.demoaxon.person.read.copyFromAggregate;

import be.vutg.demoaxon.person.read.PersonProjection;
import be.vutg.demoaxon.person.read.PersonRepository;
import be.vutg.demoaxon.person.write.PersonAggregate;
import be.vutg.demoaxon.person.write.PersonEvent;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class PersonProjectEntityService {

    private final PersonRepository personRepository;

    private final EventSourcingRepository<PersonAggregate> personAggregateEventSourcingRepository;

    private PersonProjectEntityService(PersonRepository personRepository,
                                       @Qualifier("accountAggregateEventSourcingRepository") EventSourcingRepository<PersonAggregate> personAggregateEventSourcingRepository){
        this.personRepository = personRepository;
        this.personAggregateEventSourcingRepository = personAggregateEventSourcingRepository;
    }
    
    public void recreate(UUID uuid){
        PersonAggregate personFromEvent = loadAggregateFromEvent(uuid);

        Optional<PersonProjection> personProjection = personRepository.findById(uuid);

        if(personProjection.isPresent()){
            personRepository.delete(personProjection.get());
        }

        PersonProjection personQuery = createPersonProjection(personFromEvent);
        personRepository.save(personQuery);
    }

    @EventSourcingHandler
    public void on(PersonEvent personEvent){
        PersonAggregate personFromEvent = loadAggregateFromEvent((UUID)personEvent.getAggregateId());

        Optional<PersonProjection> personProjection = personRepository.findById((UUID) personEvent.getAggregateId());

        if(personProjection.isPresent()){
            personRepository.delete(personProjection.get());
        }

        PersonProjection personQuery = createPersonProjection(personFromEvent);
        personRepository.save(personQuery);
    }

    private PersonProjection createPersonProjection(PersonAggregate personAggregate) {
        return PersonProjection
                .builder()
                .inss(personAggregate.getInss())
                .uuid(personAggregate.getUUID())
                .name(personAggregate.getName())
                .build();
    }

    private PersonAggregate loadAggregateFromEvent(UUID personEvent) {
        return personAggregateEventSourcingRepository.load(personEvent.toString()).getWrappedAggregate().getAggregateRoot();
    }


}
