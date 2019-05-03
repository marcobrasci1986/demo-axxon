package be.vutg.demoaxon.person.read;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class PersonQuery {

    @Id
    private UUID uuid;
    private String inss;

    @SuppressWarnings("UnusedDeclaration")
    private PersonQuery(){}

    public PersonQuery(UUID uuid, String inss) {
        this.uuid = uuid;
        this.inss = inss;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getInss() {
        return inss;
    }

    public static class Builder{

        private UUID uuid;
        private String inss;

        public static Builder newPersonQuery(){
            return new Builder();
        }

        public Builder withUUID(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder withInss(String inss) {
            this.inss = inss;
            return this;
        }

        public PersonQuery build() {
            return new PersonQuery(uuid, inss);
        }
    }
}
