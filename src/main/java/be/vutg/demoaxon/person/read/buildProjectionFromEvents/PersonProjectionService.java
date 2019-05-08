package be.vutg.demoaxon.person.read.buildProjectionFromEvents;

import be.vutg.demoaxon.person.read.PersonProjection;
import be.vutg.demoaxon.person.read.PersonRepository;
import be.vutg.demoaxon.person.write.PersonCreatedEvent;
import be.vutg.demoaxon.person.write.PersonNameChangedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonProjectionService {

    @Autowired
    private PersonRepository personRepository;

    @EventListener
    public void on(PersonCreatedEvent personCreatedEvent){
        PersonProjection personProjection = new PersonProjection(personCreatedEvent.getAggregateId(), personCreatedEvent.getInss());
        personRepository.save(personProjection);
    }

    @EventListener
    public void on(PersonNameChangedEvent personNameChangedEvent){
        Optional<PersonProjection> personProjection = personRepository.findById(personNameChangedEvent.getAggregateId());
        personProjection.get().setName(personNameChangedEvent.getName());
    }
}
