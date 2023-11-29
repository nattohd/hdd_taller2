package listeners;

import cl.sarayar.gestorTareasRest.entities.Tarea;
import cl.sarayar.gestorTareasRest.listeners.TareasModelListener;
import cl.sarayar.gestorTareasRest.services.GeneradorSecuenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

import static org.mockito.Mockito.*;

public class TareasModelListenerTest {

    @Mock
    private GeneradorSecuenciaService generadorSecuenciaServiceMock;

    @InjectMocks
    private TareasModelListener tareasModelListener;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializar los mocks
        tareasModelListener = new TareasModelListener(generadorSecuenciaServiceMock);
    }

    @Test
    public void onBeforeConvertMenorQueUno() {
        Tarea tarea = new Tarea();
        tarea.setIdentificador(0);
        when(generadorSecuenciaServiceMock.generadorSecuencia(anyString())).thenReturn(123L);
        tareasModelListener.onBeforeConvert(new BeforeConvertEvent<>(tarea, null));
        assert tarea.getIdentificador() == 123;
    }
    @Test
    public void onBeforeConvert_MayorQueUno() {
        Tarea tarea = new Tarea();
        tarea.setIdentificador(5); // Mayor que 1
        tareasModelListener.onBeforeConvert(new BeforeConvertEvent<>(tarea, null));
        assert tarea.getIdentificador() == 5;
        verifyNoInteractions(generadorSecuenciaServiceMock);
    }
}
