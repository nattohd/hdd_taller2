package controllers;

import cl.sarayar.gestorTareasRest.controllers.TareasController;
import cl.sarayar.gestorTareasRest.controllers.UsuariosController;
import cl.sarayar.gestorTareasRest.entities.Tarea;
import cl.sarayar.gestorTareasRest.services.TareasService;
import cl.sarayar.gestorTareasRest.services.UsuariosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TareasControllerTest {
    @Mock
    private TareasService tareasServiceMock;
    private TareasController tareasController;
    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
        this.tareasController = new TareasController(tareasServiceMock);
    }
    @Test
    void testGetAll() {
        List<Tarea> tareas = new ArrayList<>();
        tareas.add(new Tarea("1", 1, "Descripción 1", LocalDateTime.now(), true));
        tareas.add(new Tarea("2", 2, "Descripción 2", LocalDateTime.now(), false));
        when(tareasServiceMock.findAll()).thenReturn(tareas);
        List<Tarea> foundTareas = tareasController.getAll();
        assertEquals(tareas, foundTareas);
    }

    @Test
    void testSave() {
        Tarea tarea = new Tarea("1", 1, "Descripción", LocalDateTime.now(), true);
        when(tareasServiceMock.save(any(Tarea.class))).thenReturn(tarea);
        ResponseEntity<Tarea> response = tareasController.save(tarea);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(tarea, response.getBody());
    }

    @Test
    void testUpdate() {
        Tarea tarea = new Tarea("1", 1, "Descripción", LocalDateTime.now(), true);
        when(tareasServiceMock.findById("1")).thenReturn(tarea);
        when(tareasServiceMock.save(any(Tarea.class))).thenReturn(tarea);
        ResponseEntity<Tarea> response = tareasController.update(tarea);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(tarea, response.getBody());
    }

    // Test para el método delete
    @Test
    void testDelete() {
        String id = "1";
        doThrow(new RuntimeException("Error al eliminar tarea")).when(tareasServiceMock).remove(id);
        ResponseEntity<Boolean> response = tareasController.delete(id);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse(response.getBody());
    }
    @Test
    void testDeleteSUCCESULLY() {
        String id = "1";
        doNothing().when(tareasServiceMock).remove(id);
        ResponseEntity<Boolean> response = tareasController.delete(id);
        verify(tareasServiceMock, times(1)).remove(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }




}
