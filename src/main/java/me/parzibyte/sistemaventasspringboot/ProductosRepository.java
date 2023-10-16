package me.parzibyte.sistemaventasspringboot;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductosRepository extends CrudRepository<Producto, Integer> {
    Producto findFirstByCodigo(String codigo);

    @Query("SELECT p FROM Producto p Where p.nombre LIKE %?1%"+"OR p.codigo LIKE %?1%")
    public List<Producto> findAll(String palabraClave);
}
