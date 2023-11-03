package cl.sarayar.gestorTareasRest.services;



import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import cl.sarayar.gestorTareasRest.entities.Usuario;

public interface UsuariosService extends UserDetailsService {

	Usuario save(Usuario usuario);
	List<Usuario> getAll();
	Usuario findByCorreo(String correo);
	Usuario findById(String id);
	Boolean existsByCorreo(String correo);
}
