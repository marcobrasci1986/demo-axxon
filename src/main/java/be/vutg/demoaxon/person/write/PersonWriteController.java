package be.vutg.demoaxon.person.write;

import be.vutg.demoaxon.person.read.PersonReadController;
import io.swagger.annotations.Api;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.GenericDomainEventEntry;
import org.axonframework.eventhandling.GenericDomainEventMessage;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = "/person/write")
@Api(value = "Person write", tags = "person")
public class PersonWriteController {

    private static final Logger LOGGER = getLogger(PersonReadController.class);

    private final EventStore eventStore;

    private final CommandGateway commandGateway;

    public PersonWriteController(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping("/create")
    public UUID createPerson(String inss) {
        UUID personId = randomUUID();
        LOGGER.info("Trying to add a person  with niss: {}, and uuid {}", inss, personId.toString());
        commandGateway.send(new CreatePersonCommand(personId, inss));
        return personId;
    }


    @PutMapping("/addChangeEvent")
    @Transactional
    public UUID addChangeEventPerson(String uuid, Object name) {
        UUID personId = UUID.fromString(uuid);
        LOGGER.info("Trying to add event {} for person with uuid {}", name);
        Optional<Long> lastSequenceNumberFor = eventStore.lastSequenceNumberFor(uuid);
        if (lastSequenceNumberFor.isPresent()) {
            long sequenceNumber = lastSequenceNumberFor.get() + 1;
            GenericDomainEventMessage<Object> myType = new GenericDomainEventMessage<>("PersonAggregate", uuid, sequenceNumber, name);
            eventStore.publish(myType);
        } else {
            GenericDomainEventMessage<Object> myType = new GenericDomainEventMessage<>("PersonAggregate", uuid, 0, name);
            eventStore.publish(myType);
        }
        return personId;
    }

    @PutMapping("/addChangeEventAsTyped")
    @Transactional
    public UUID addChangeEventPersonAsTypedEvent(String uuid, String name) {
        UUID personId = UUID.fromString(uuid);
        LOGGER.info("Trying to add event {} for person with uuid {}", name);
        Optional<Long> lastSequenceNumberFor = eventStore.lastSequenceNumberFor(uuid);

        long sequenceNumber = lastSequenceNumberFor.get() + 1;
        GenericDomainEventMessage<PersonNameChangedEvent> myType = new GenericDomainEventMessage<PersonNameChangedEvent>("PersonAggregate", uuid, sequenceNumber, new PersonNameChangedEvent(personId, name));
        eventStore.publish(myType);
        return personId;
    }


    @PutMapping("/changeName")
    public UUID createPerson(String uuid, String name) {
        UUID personId = UUID.fromString(uuid);
        LOGGER.info("Trying to create a person  with niss: {}, and uuid {}", name, personId.toString());
        commandGateway.send(new ChangeNameCommand(personId, name));
        return personId;
    }
}
