package be.vutg.demoaxon.person.write;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
public class ChangeNameCommand {

    @TargetAggregateIdentifier
    private final UUID uuid;
    private final String name;
}
