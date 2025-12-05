package com.joseph.tienda_backend.repositories;

import com.joseph.tienda_backend.entities.Proveedor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<@NonNull Proveedor,@NonNull Long> {
    boolean existsByEmpresaOrTelefonoOrEmail(String empresa, String telefono, String email);
    Optional<Proveedor> findByEmpresaOrTelefonoOrEmail(String empresa, String telefono, String email);
}
