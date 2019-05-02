package be.vutg.demoaxon.person.read;

import be.vutg.demoaxon.person.write.CreatePersonCommand;
import be.vutg.demoaxon.person.write.PersonAggregate;
import io.swagger.annotations.Api;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = "/person")
@Api(value = "Person Commands", tags = "Person Commands")
public class PersonController {

    private static final Logger LOGGER = getLogger(PersonController.class);

    private final CommandGateway commandGateway;

    private final EventSourcingRepository<PersonAggregate> personAggregateEventSourcingRepository;

    public PersonController(CommandGateway commandGateway, EventSourcingRepository<PersonAggregate> personAggregateEventSourcingRepository) {
        this.commandGateway = commandGateway;
        this.personAggregateEventSourcingRepository = personAggregateEventSourcingRepository;
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET, produces = "application/json")
    public PersonAggregate getPerson(String uuid) {
        LOGGER.info("Trying to get Person {}", uuid);
        return personAggregateEventSourcingRepository.load(uuid).getWrappedAggregate().getAggregateRoot();
    }

    @PostMapping("/create")
    public void createPerson(String inss) {
        UUID personId = randomUUID();
        LOGGER.info("Trying to create a person  with niss: {}, and uuid {}", inss, personId.toString());
        commandGateway.send(new CreatePersonCommand(personId, inss));
    }
}
