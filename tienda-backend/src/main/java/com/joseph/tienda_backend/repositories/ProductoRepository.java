package com.joseph.tienda_backend.repositories;

import com.joseph.tienda_backend.entities.Producto;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<@NonNull Producto,@NonNull Long> {
    boolean existsByNombre(String nombre);
}
