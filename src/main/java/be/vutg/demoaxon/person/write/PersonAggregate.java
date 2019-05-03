package be.vutg.demoaxon.person.write;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class PersonAggregate {

    @AggregateIdentifier
    private UUID personId;

    private String inss;

    @SuppressWarnings("UnusedDeclaration")
    public PersonAggregate(){}

    @CommandHandler
    public PersonAggregate(CreatePersonCommand createPerson){
        apply(new PersonCreatedEvent(createPerson.getUuid(), createPerson.getInss()));
    }

    @EventSourcingHandler
    public void on(PersonCreatedEvent event) {
        this.personId = event.getPersonId();
        this.inss = event.getInss();
    }

    public UUID getUUID() {
        return personId;
    }

    public String getInss() {
        return inss;
    }
}
