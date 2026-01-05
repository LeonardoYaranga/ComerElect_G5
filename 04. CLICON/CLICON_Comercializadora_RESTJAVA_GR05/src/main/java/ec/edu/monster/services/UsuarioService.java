package ec.edu.monster.services;

import ec.edu.monster.models.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private static final List<Usuario> USUARIOS_QUEMADOS = new ArrayList<>();

    static {
        // Admin
        USUARIOS_QUEMADOS.add(new Usuario("MONSTER", "1111111111", "Administrador", "MONSTER9", "ADMIN"));
        // Clientes - Cédulas de la tabla de clientes
        USUARIOS_QUEMADOS.add(new Usuario("JOEL", "1234567890", "JJOEL", "JOEL9", "CLIENTE"));
        USUARIOS_QUEMADOS.add(new Usuario("DOME", "0987654321", "DOME", "DOME9", "CLIENTE"));
        USUARIOS_QUEMADOS.add(new Usuario("LEO", "1111111112", "LEO", "LEO9", "CLIENTE"));
    }

    public List<Usuario> obtenerUsuarios() {
        return USUARIOS_QUEMADOS;
    }
}