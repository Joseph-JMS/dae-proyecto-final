package com.joseph.tienda_backend.services;

import com.joseph.tienda_backend.entities.Producto;
import com.joseph.tienda_backend.repositories.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public List<Producto> obtenerConStock() {
        return productoRepository.findAll()
                .stream()
                .filter(prod -> prod.getStock() > 0)
                .collect(Collectors.toList());
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Producto crear(Producto producto) throws Exception {
        try {
            if(productoRepository.existsByNombre(producto.getNombre())){
                throw new Exception("El producto con nombre " + producto.getNombre() + " ya existe");
            }
            if (validarProducto(producto)) {
                throw new IllegalArgumentException("El producto no puede tener precio o stock negativo");
            }
            if (producto.getEstado() == null) {
                producto.setEstado(true);
            }
            return productoRepository.save(producto);
        } catch (Exception e) {
            throw e;
        }
    }

    public Producto actualizar(Long id, Producto productoActualizado) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id " + id));

        if (!producto.getNombre().equals(productoActualizado.getNombre()) &&
                productoRepository.existsByNombre(productoActualizado.getNombre())) {
            throw new IllegalArgumentException("El producto con nombre " + productoActualizado.getNombre() + " ya existe");
        }
        if (validarProducto(productoActualizado)) {
            throw new IllegalArgumentException("El producto no puede tener precio o stock negativo");
        }

        producto.setNombre(productoActualizado.getNombre());
        producto.setPrecio(productoActualizado.getPrecio());
        producto.setStock(productoActualizado.getStock());
        producto.setEstado(productoActualizado.getEstado());

        return productoRepository.save(producto);
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    private boolean validarProducto(Producto producto) {
        return producto.getPrecio() >= 0 && producto.getStock() >= 0;
    }
}
