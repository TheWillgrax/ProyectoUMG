package me.parzibyte.sistemaventasspringboot;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Puedes agregar métodos personalizados si es necesario
}
