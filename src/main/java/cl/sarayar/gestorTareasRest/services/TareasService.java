package cl.sarayar.gestorTareasRest.services;

import java.util.List;

import cl.sarayar.gestorTareasRest.entities.Tarea;

public interface TareasService {

	List<Tarea> findAll();
	Tarea save(Tarea t);
	boolean remove(String id);
	Tarea findById(String id);
	
}
