package be.vutg.demoaxon.person.write;

import lombok.Data;

import java.util.UUID;

@Data
public class PersonNameChangedEvent implements PersonEvent<UUID>{
    private final UUID uuid;
    private final String name;

    @Override
    public UUID getAggregateId() {
        return uuid;
    }
}
