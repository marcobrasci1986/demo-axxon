package be.vutg.demoaxon.person.write;

import lombok.Data;

import java.util.UUID;

@Data
public class PersonCreatedEvent implements PersonEvent<UUID>{
    private final UUID personId;
    private final String inss;

    @Override
    public UUID getAggregateId() {
        return personId;
    }
}
