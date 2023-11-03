package cl.sarayar.gestorTareasRest.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import cl.sarayar.gestorTareasRest.entities.Tarea;

public interface TareasRepository extends MongoRepository<Tarea, String> {
	
}
