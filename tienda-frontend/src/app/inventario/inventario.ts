import { Component } from '@angular/core';
import { Producto, ProductoService } from '../services/producto.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-inventario',
  imports: [CommonModule, FormsModule],
  templateUrl: './inventario.html',
  styleUrl: './inventario.css',
})
export class Inventario {
  productos: Producto[] = [];
  loading = true;
  error = '';
  showForm = false;
  editMode = false;
  selected: Producto = { id: 0, nombre: '', precio: 0, stock: 0, estado: true };
  nombreDuplicado: boolean = false;
  filtrarActivos = false;

  constructor(private productoService: ProductoService) {
    this.cargarProductos();
  }

  filtrarPorStock() {
  this.filtrarActivos = !this.filtrarActivos;

  if (this.filtrarActivos) {
    this.loading = true;
    this.productoService.getProductosActivos().subscribe({
      next: (data) => {
        this.productos = data;
        this.loading = false;
      }
    });
  } else {
    this.cargarProductos();
  }
}

  cargarProductos() {
    this.loading = true;
    this.productoService.getProductos().subscribe({
      next: (data) => {
        this.productos = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'No se pudieron cargar los productos';
        console.error(err);
        this.loading = false;
      }
    });
  }

  abrirFormulario(prod?: Producto) {
    if (prod) {
      this.editMode = true;
      this.selected = { ...prod };
    } else {
      this.editMode = false;
      this.selected = { id: 0, nombre: '', precio: 0, stock: 0, estado: true };
    }
    this.showForm = true;
  }

  cerrarFormulario() {
    this.showForm = false;
  }

  guardar(form: any) {
    if (form.invalid) return;

    this.nombreDuplicado = false;
    const existeNombre = this.productos.some(
      e =>
        e.nombre.toLowerCase() === this.selected.nombre.toLowerCase() &&
        e.id !== this.selected.id
    );

    if (existeNombre) {
      this.nombreDuplicado = true;    // <-- activar mensaje
      return;                         // no enviar al backend
    }

    if (this.editMode) {
      this.productoService.actualizarProducto(this.selected).subscribe({
        next: () => {
          this.cargarProductos();
          this.cerrarFormulario();
        }
      });
    } else {
      const { id, ...nuevo } = this.selected;
      this.productoService.crearProducto(nuevo).subscribe({
        next: () => {
          this.cargarProductos();
          this.cerrarFormulario();
        }
      });
    }
  }

  eliminar(id: number) {
    if (confirm('¿Deseas eliminar este producto?')) {
      this.productoService.eliminarProducto(id).subscribe({
        next: () => this.cargarProductos()
      });
    }
  }
}
