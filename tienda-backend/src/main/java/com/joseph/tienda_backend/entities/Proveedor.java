package com.joseph.tienda_backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "proveedores")
public class Proveedor {

    private Long id;
    private String empresa;
    private String contacto;
    private String telefono;

}
