package be.vutg.demoaxon.person.write;

import be.vutg.demoaxon.person.read.PersonReadController;
import io.swagger.annotations.Api;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = "/person/write")
@Api(value = "Person write", tags = "person")
public class PersonWriteController {

    private static final Logger LOGGER = getLogger(PersonReadController.class);

    private final CommandGateway commandGateway;

    public PersonWriteController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/create")
    public UUID createPerson(String inss) {
        UUID personId = randomUUID();
        LOGGER.info("Trying to create a person  with niss: {}, and uuid {}", inss, personId.toString());
        commandGateway.send(new CreatePersonCommand(personId, inss));
        return personId;
    }
}
