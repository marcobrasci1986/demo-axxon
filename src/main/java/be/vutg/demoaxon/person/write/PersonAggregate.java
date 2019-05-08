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

    private String name;

    @SuppressWarnings("UnusedDeclaration")
    public PersonAggregate(){}

    @CommandHandler
    public PersonAggregate(CreatePersonCommand createPerson){
        apply(new PersonCreatedEvent(createPerson.getUuid(), createPerson.getInss()));
    }

    public void setName(ChangeNameCommand changeNameCommand){
        apply(new PersonNameChangedEvent(changeNameCommand.getUuid(), changeNameCommand.getName()));
    }

    @EventSourcingHandler
    public void on(PersonCreatedEvent event) {
        this.personId = event.getPersonId();
        this.inss = event.getInss();
    }

    @EventSourcingHandler
    public void on(PersonNameChangedEvent event) {
       this.name = event.getName();
    }

    public UUID getUUID() {
        return personId;
    }

    public String getInss() {
        return inss;
    }

    public String getName() {
        return name;
    }
}
