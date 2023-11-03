package cl.sarayar.gestorTareasRest.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import cl.sarayar.gestorTareasRest.entities.Usuario;

public interface UsuariosRepository extends MongoRepository<Usuario, String> {
	
	Optional<Usuario> findByCorreo(String correo);
	Boolean existsByCorreo(String correo);
	Optional<Usuario> findByCorreoAndEstado(String correo, int estado);
	List<Usuario> findByEstado(int estado);

}
