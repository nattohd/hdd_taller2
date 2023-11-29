package cl.sarayar.gestorTareasRest.listeners;

import cl.sarayar.gestorTareasRest.services.TareasService;
import cl.sarayar.gestorTareasRest.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import cl.sarayar.gestorTareasRest.entities.Tarea;
import cl.sarayar.gestorTareasRest.services.GeneradorSecuenciaService;

@Component
public class TareasModelListener extends AbstractMongoEventListener<Tarea>{

	public final GeneradorSecuenciaService generadorSecuenciaService;
	private TareasService tareasService;
	@Autowired
	public TareasModelListener(GeneradorSecuenciaService generadorSecuenciaService){
		this.generadorSecuenciaService=generadorSecuenciaService;
	}

	@Autowired
	JwtUtils jwtUtils;
	@Override
	public void onBeforeConvert(BeforeConvertEvent<Tarea> event) {
		if(event.getSource().getIdentificador()<1) {
			event.getSource().setIdentificador(generadorSecuenciaService.generadorSecuencia(Tarea.NOMBRE_SECUENCIA));
		}
	}
}
