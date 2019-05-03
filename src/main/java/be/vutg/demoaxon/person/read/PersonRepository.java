package be.vutg.demoaxon.person.read;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<PersonQuery, UUID> {
}
