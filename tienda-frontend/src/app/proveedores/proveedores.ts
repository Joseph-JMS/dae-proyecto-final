import { Component } from '@angular/core';
import { Proveedor, ProveedorService } from '../services/proveedor.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-proveedores',
  imports: [CommonModule, FormsModule],
  templateUrl: './proveedores.html',
  styleUrl: './proveedores.css',
})
export class Proveedores {
  proveedores: Proveedor[] = [];
  loading = true;
  error = '';

  showForm = false;
  editMode = false;

  selected: Proveedor = { id: 0, empresa: '', contacto: '', telefono: '', email: '' };

  // mensajes de error
  duplicadoEmpresa = false;
  duplicadoTelefono = false;
  duplicadoEmail = false;

  constructor(private proveedorService: ProveedorService) {
    this.cargarProveedores();
  }

  cargarProveedores() {
    this.loading = true;
    this.proveedorService.getProveedores().subscribe({
      next: (data) => {
        this.proveedores = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'No se pudieron cargar los proveedores';
        this.loading = false;
      }
    });
  }

  abrirFormulario(proveedor?: Proveedor) {
    this.resetDuplicados();

    if (proveedor) {
      this.editMode = true;
      this.selected = { ...proveedor };
    } else {
      this.editMode = false;
      this.selected = { id: 0, empresa: '', contacto: '', telefono: '', email: '' };
    }

    this.showForm = true;
  }

  cerrarFormulario() {
    this.showForm = false;
    this.resetDuplicados();
  }

  private resetDuplicados() {
    this.duplicadoEmpresa = false;
    this.duplicadoTelefono = false;
    this.duplicadoEmail = false;
  }

  guardar(form: any) {
    if (form.invalid) return;

    this.resetDuplicados();

    const empresa = this.selected.empresa.trim().toLowerCase();
    const telefono = this.selected.telefono.trim();
    const email = this.selected.email.trim().toLowerCase();

    // VALIDACIÓN OR — si un dato coincide, bloquea
    for (let p of this.proveedores) {
      if (p.id !== this.selected.id) {

        if (p.empresa.trim().toLowerCase() === empresa)
          this.duplicadoEmpresa = true;

        if (p.telefono.trim() === telefono)
          this.duplicadoTelefono = true;

        if (p.email.trim().toLowerCase() === email)
          this.duplicadoEmail = true;
      }
    }

    if (this.duplicadoEmpresa || this.duplicadoTelefono || this.duplicadoEmail) {
      return; // NO se envía al backend
    }

    // Guardar o actualizar
    if (this.editMode) {
      this.proveedorService.actualizarProveedor(this.selected).subscribe({
        next: () => {
          this.cargarProveedores();
          this.cerrarFormulario();
        }
      });
    } else {
      const { id, ...nuevo } = this.selected;
      this.proveedorService.crearProveedor(nuevo).subscribe({
        next: () => {
          this.cargarProveedores();
          this.cerrarFormulario();
        }
      });
    }
  }

  eliminar(id: number) {
    if (confirm('¿Deseas eliminar este proveedor?')) {
      this.proveedorService.eliminarProveedor(id).subscribe({
        next: () => this.cargarProveedores()
      });
    }
  }
}