import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

export interface Producto {
  id: number;
  nombre: string;
  precio: number;
  stock: number;
  estado: boolean;
}

@Injectable({
  providedIn: 'root',
})

export class ProductoService {

  private apiUrl = 'http://localhost:8080/api/productos'; // Cambia según tu backend

  constructor(private http: HttpClient) {}

  getProductos(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.apiUrl);
  }

  getProductosActivos(): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.apiUrl}/activos`);
  }

  getProducto(id: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.apiUrl}/${id}`);
  }

  crearProducto(producto: Omit<Producto, 'id'>): Observable<Producto> {
    return this.http.post<Producto>(this.apiUrl, producto);
  }

  actualizarProducto(producto: Producto): Observable<Producto> {
    return this.http.put<Producto>(`${this.apiUrl}/${producto.id}`, producto);
  }

  eliminarProducto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}