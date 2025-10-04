import { Routes } from '@angular/router';
import {authGuard} from './wrapper/guard/auth.guard';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'dashboard'
  },
  {
    path: 'auth',
    children: [
      {
        path: 'login',
        loadComponent: () => import('./components/login/login.component').then(it => it.LoginComponent)
      }
    ]
  },
  {
    path: '',
    canActivate: [authGuard],
    loadComponent: () => import('./pages/default-layout/default-layout.component').then(it => it.DefaultLayoutComponent),
    children: [
      {
        path: 'dashboard',
        loadComponent: () => import('./components/dashboard/dashboard.component').then(it => it.DashboardComponent)
      },
    ]
  },
];
