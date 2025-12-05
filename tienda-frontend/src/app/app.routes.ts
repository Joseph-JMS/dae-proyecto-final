import { Routes } from '@angular/router';
import { Inicio } from './inicio/inicio';
import { Inventario } from './inventario/inventario';
import { Proveedores } from './proveedores/proveedores';

export const routes: Routes = [
    { path: 'inicio', component: Inicio },
    { path: 'productos', component: Inventario },
    { path: 'proveedores', component: Proveedores },

];
