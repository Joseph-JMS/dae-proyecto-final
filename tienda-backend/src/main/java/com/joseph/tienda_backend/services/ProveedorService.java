package com.joseph.tienda_backend.services;

import com.joseph.tienda_backend.entities.Proveedor;
import com.joseph.tienda_backend.repositories.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public List<Proveedor> obtenerTodos() {
        return proveedorRepository.findAll();
    }

    public Optional<Proveedor> obtenerPorId(Long id) {
        return proveedorRepository.findById(id);
    }

    public Proveedor crear(Proveedor proveedor) throws Exception {
        try {
            if(proveedorRepository.existsByEmpresaOrTelefonoOrEmail(proveedor.getEmpresa(), proveedor.getTelefono(), proveedor.getEmail())){
                throw new Exception("El proveedor ya existe");
            }
            return proveedorRepository.save(proveedor);
        } catch (Exception e) {
            throw e;
        }
    }

    public Proveedor actualizar(Long id, Proveedor proveedorActualizado) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id " + id));

        boolean existeDuplicado = proveedorRepository.existsByEmpresaOrTelefonoOrEmail(
                proveedorActualizado.getEmpresa(), proveedorActualizado.getTelefono(), proveedorActualizado.getEmail());

        if(existeDuplicado &&
                (!proveedor.getEmpresa().equals(proveedorActualizado.getEmpresa()) ||
                        !proveedor.getTelefono().equals(proveedorActualizado.getTelefono()) ||
                        !proveedor.getEmail().equals(proveedorActualizado.getEmail()))) {
            throw new IllegalArgumentException("Ya existe un proveedor con la misma empresa, teléfono o email");
        }

        proveedor.setEmpresa(proveedorActualizado.getEmpresa());
        proveedor.setContacto(proveedorActualizado.getContacto());
        proveedor.setTelefono(proveedorActualizado.getTelefono());
        proveedor.setEmail(proveedorActualizado.getEmail());

        return proveedorRepository.save(proveedor);
    }

    public void eliminar(Long id) {
        proveedorRepository.deleteById(id);
    }
}
