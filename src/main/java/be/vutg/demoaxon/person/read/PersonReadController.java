package be.vutg.demoaxon.person.read;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = "/person/read")
@Api(value = "Person read", tags = "Person")
public class PersonReadController {

    private static final Logger LOGGER = getLogger(PersonReadController.class);

    private final PersonRepository personRepository;

    public PersonReadController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET, produces = "application/json")
    public PersonQuery getPerson(String uuid) {
        LOGGER.info("Trying to get Person {}", uuid);
        return personRepository.findById(UUID.fromString(uuid)).get();
    }
}
