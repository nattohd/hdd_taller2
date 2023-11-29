package services;
import cl.sarayar.gestorTareasRest.entities.Usuario;
import cl.sarayar.gestorTareasRest.repositories.UsuariosRepository;
import cl.sarayar.gestorTareasRest.services.UsuariosService;
import cl.sarayar.gestorTareasRest.services.UsuariosServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class UsuariosServiceImplTest {
    @Mock
    private UsuariosRepository usRepoMock;
    private UsuariosService usuariosService;
    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this  );
        this.usuariosService = new UsuariosServiceImpl(usRepoMock);
    }
    @Test
    void testSaveUsuario() {
        Usuario usuario = new Usuario("1", "UsuarioPrueba", "correo@example.com", "clave", 1);
        when(usRepoMock.save(any(Usuario.class))).thenReturn(usuario);
        Usuario savedUsuario = usuariosService.save(usuario);
        verify(usRepoMock, times(1)).save(usuario);
        assertEquals(usuario, savedUsuario);
    }
    @Test
    void testFindByCorreo() {
        String correo = "correo@example.com";
        Usuario usuario = new Usuario("1", "UsuarioPrueba", correo, "clave", 1);
        when(usRepoMock.findByCorreo(correo)).thenReturn(Optional.of(usuario));
        Usuario foundUsuario = usuariosService.findByCorreo(correo);
        verify(usRepoMock, times(1)).findByCorreo(correo);
        assertEquals(usuario, foundUsuario);
    }
    @Test
    void testGetAll() {
        Usuario usuario1 = new Usuario("1", "Usuario1", "correo1@example.com", "clave1", 1);
        Usuario usuario2 = new Usuario("2", "Usuario2", "correo2@example.com", "clave2", 1);
        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);
        when(usRepoMock.findAll()).thenReturn(usuarios);
        List<Usuario> foundUsuarios = usuariosService.getAll();
        verify(usRepoMock, times(1)).findAll();
        assertEquals(usuarios, foundUsuarios);
    }
    @Test
    void testFindById() {
        String id = "1";
        Usuario usuario = new Usuario(id, "UsuarioPrueba", "test@test.com", "clave", 1);
        when(usRepoMock.findById(id)).thenReturn(Optional.of(usuario));
        Usuario foundUsuario = usuariosService.findById(id);
        verify(usRepoMock, times(1)).findById(id);
        assertEquals(usuario, foundUsuario);
    }
    @Test
    void testExistsByCorreo() {
        String correo = "test@test.com";
        when(usRepoMock.existsByCorreo(correo)).thenReturn(true);
        boolean exists = usuariosService.existsByCorreo(correo);
        verify(usRepoMock, times(1)).existsByCorreo(correo);
        assertTrue(exists);
    }
    @Test
    void testLoadUserByUsername() {
        String correo = "test@test.com";
        Usuario usuario = new Usuario("1", "UsuarioPrueba", correo, "clave", 1);
        when(usRepoMock.findByCorreo(correo)).thenReturn(Optional.of(usuario));
        UserDetails userDetails = usuariosService.loadUserByUsername(correo);
        verify(usRepoMock, times(1)).findByCorreo(correo);
        assertNotNull(userDetails);
    }
}


