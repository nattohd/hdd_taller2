package controllers;

import cl.sarayar.gestorTareasRest.config.auth.dto.MessageResponse;
import cl.sarayar.gestorTareasRest.controllers.UsuariosController;
import cl.sarayar.gestorTareasRest.entities.Usuario;
import cl.sarayar.gestorTareasRest.services.UsuariosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
class UsuariosControllerTest {
    @Mock
    private UsuariosService usService;

    private UsuariosController usuariosController;

    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
        this.usuariosController = new UsuariosController(usService);
    }

    @Test
    void testGetAll() {
        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuario1 = new Usuario();
        usuario1.setId("1");
        usuario1.setNombre("test1");
        usuario1.setCorreo("test1@gmail.com");
        usuarios.add(usuario1);
        Usuario usuario2 = new Usuario();
        usuario2.setId("2");
        usuario2.setNombre("test2");
        usuario2.setCorreo("test2@gmail.com");
        usuarios.add(usuario2);
        when(usService.getAll()).thenReturn(usuarios);
        List<Usuario> responseUsuarios = usuariosController.getAll();
        //ver q la repsuesta no sea nula
        assertNotNull(responseUsuarios);
        //que coincida el tamaño de la lista de usuario
        assertEquals(usuarios.size(), responseUsuarios.size());

        //que coincida la respuesta
        for (int i = 0; i < usuarios.size(); i++) {
            assertEquals(usuarios.get(i).getId(), responseUsuarios.get(i).getId());
            assertEquals(usuarios.get(i).getNombre(), responseUsuarios.get(i).getNombre());
            assertEquals(usuarios.get(i).getCorreo(), responseUsuarios.get(i).getCorreo());
        }



    }
    @Test
    void testRegisterUser() {
        Usuario usuario = new Usuario();
        usuario.setId("1");
        usuario.setNombre("test");
        usuario.setCorreo("test1@gmail.com");
        usuario.setClave("pass");
        when(usService.save(any(Usuario.class))).thenReturn(usuario);
        ResponseEntity<?> responseEntity = usuariosController.registerUser(new Usuario());
        assertEquals(200, responseEntity.getStatusCodeValue());
        Usuario responseUsuario = (Usuario) responseEntity.getBody();
        assertEquals("1", responseUsuario.getId());
        assertEquals("test", responseUsuario.getNombre());
        assertEquals("test1@gmail.com", responseUsuario.getCorreo());
        assertEquals("pass", responseUsuario.getClave());
    }
    @Test
    void testRegisterUserCorreoExistente() {
        Usuario usuarioEntrada = new Usuario();
        usuarioEntrada.setCorreo("correoExistente@gmail.com");
        when(usService.existsByCorreo("correoExistente@gmail.com")).thenReturn(true);
        ResponseEntity<?> responseEntity = usuariosController.registerUser(usuarioEntrada);
        assertEquals(400, responseEntity.getStatusCodeValue());
        MessageResponse messageResponse = (MessageResponse) responseEntity.getBody();
        assertEquals("Error: Usuario ya existe!", messageResponse.getMensaje());
    }
    @Test
    void testActualizarUser(){
        Usuario usuarioEntrada = new Usuario();
        usuarioEntrada.setId("1");
        usuarioEntrada.setNombre("EjemploNuevo");
        usuarioEntrada.setCorreo("nuevo@example.com");
        usuarioEntrada.setEstado(1);
        Usuario usuarioOriginal = new Usuario();
        usuarioOriginal.setId("1");
        usuarioOriginal.setNombre("EjemploOriginal");
        usuarioOriginal.setCorreo("original@example.com");
        usuarioOriginal.setEstado(1);
        when(usService.findById("1")).thenReturn(usuarioOriginal);
        when(usService.findByCorreo("nuevo@example.com")).thenReturn(null);
        when(usService.save(any(Usuario.class))).thenReturn(usuarioEntrada);
        ResponseEntity<?> responseEntity = usuariosController.actualizarUsuario(usuarioEntrada);
        assertEquals(200, responseEntity.getStatusCodeValue());

        Usuario responseUsuario = (Usuario) responseEntity.getBody();
        assertEquals("1", responseUsuario.getId());
        assertEquals("EjemploNuevo", responseUsuario.getNombre());
        assertEquals("nuevo@example.com", responseUsuario.getCorreo());
        assertEquals(1, responseUsuario.getEstado());
    }

    @Test
    void testActualizarUsuarioCorreoExistente() {
        // Crear un usuario de ejemplo para el método de entrada
        Usuario usuarioEntrada = new Usuario();
        usuarioEntrada.setId("1");
        usuarioEntrada.setNombre("EjemploNuevo");
        usuarioEntrada.setCorreo("correoExistente@example.com");

        // Crear un usuario de ejemplo para simular el usuario original en la base de datos
        Usuario usuarioOriginal = new Usuario();
        usuarioOriginal.setId("1");
        usuarioOriginal.setNombre("EjemploOriginal");
        usuarioOriginal.setCorreo("original@example.com");

        // Crear un usuario existente con el mismo correo pero ID diferente
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId("2");
        usuarioExistente.setNombre("UsuarioExistente");
        usuarioExistente.setCorreo("correoExistente@example.com");

        // Configurar el comportamiento de usService.findById para devolver el usuario original
        when(usService.findById("1")).thenReturn(usuarioOriginal);

        // Configurar el comportamiento de usService.findByCorreo para devolver el usuario existente
        when(usService.findByCorreo("correoExistente@example.com")).thenReturn(usuarioExistente);

        // Llamar al método actualizarUsuario del controlador
        ResponseEntity<?> responseEntity = usuariosController.actualizarUsuario(usuarioEntrada);

        // Verificar que la respuesta sea 400 BadRequest
        assertEquals(400, responseEntity.getStatusCodeValue());

        // Verificar que el mensaje de error esté en el cuerpo de la respuesta
        MessageResponse messageResponse = (MessageResponse) responseEntity.getBody();
        assertEquals("Error: Correo se encuentra utilizado!", messageResponse.getMensaje());
    }

    @Test
    void testAuthenticateUser() {
        // Crear un usuario de ejemplo para el método de entrada
        Usuario usuarioEntrada = new Usuario();
        usuarioEntrada.setId("1");
        usuarioEntrada.setNombre("Ejemplo");
        usuarioEntrada.setCorreo("ejemplo@example.com");
        usuarioEntrada.setClave("clave");
        ResponseEntity<?> responseEntity = usuariosController.authenticateUser(usuarioEntrada);

        // Verificar que la respuesta sea 200 OK
        assertEquals(200, responseEntity.getStatusCodeValue());

        // Verificar que el usuario devuelto coincida con el usuario de entrada
        Usuario responseUsuario = (Usuario) responseEntity.getBody();
        assertEquals("1", responseUsuario.getId());
        assertEquals("Ejemplo", responseUsuario.getNombre());
        assertEquals("ejemplo@example.com", responseUsuario.getCorreo());
        assertEquals("clave", responseUsuario.getClave());
    }
}




