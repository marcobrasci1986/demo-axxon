package be.vutg.demoaxon.person.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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
