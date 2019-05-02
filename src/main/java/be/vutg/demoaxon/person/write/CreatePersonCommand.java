package be.vutg.demoaxon.person.write;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
public class CreatePersonCommand {

    @TargetAggregateIdentifier
    private final UUID uuid;
    private final String inss;

}
