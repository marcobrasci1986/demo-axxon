package be.vutg.demoaxon.person.read;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Builder
@Data
public class PersonProjection {

    @Id
    private UUID uuid;
    private String inss;
    private String name;

    public PersonProjection(UUID uuid, String inss) {
        this.uuid = uuid;
        this.inss = inss;
    }
}
