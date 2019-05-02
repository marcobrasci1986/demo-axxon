package be.vutg.demoaxon.person.write;

import lombok.Data;

import java.util.UUID;

@Data
public class PersonCreatedEvent {
    private final UUID personId;
    private final String inss;
}
