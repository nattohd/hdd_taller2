package services;

import cl.sarayar.gestorTareasRest.entities.Tarea;
import cl.sarayar.gestorTareasRest.repositories.TareasRepository;
import cl.sarayar.gestorTareasRest.repositories.UsuariosRepository;
import cl.sarayar.gestorTareasRest.services.TareasService;
import cl.sarayar.gestorTareasRest.services.TareasServiceImpl;
import cl.sarayar.gestorTareasRest.services.UsuariosService;
import cl.sarayar.gestorTareasRest.services.UsuariosServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class TareasServiceImplTest {

    @Mock
    private TareasRepository tareasRepositoryMock;
    private TareasService tareasService;
    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this  );
        this.tareasService = new TareasServiceImpl(tareasRepositoryMock);
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllTest() {
        List<Tarea> mockTasks = new ArrayList<>();
        when(tareasRepositoryMock.findAll()).thenReturn(mockTasks);
        List<Tarea> result = tareasService.findAll();
        assert result.size() == mockTasks.size();
    }

    @Test
    public void removeTrue() {
        String taskId = "id_test";
        doNothing().when(tareasRepositoryMock).deleteById(taskId);
        boolean result = tareasService.remove(taskId);
        assert result;
    }
    @Test
    public void findByIdNull() {
        String invalidId = "id_invalida";
        when(tareasRepositoryMock.findById(invalidId)).thenReturn(Optional.empty());
        Tarea result = tareasService.findById(invalidId);
        assert result == null;
    }

}
