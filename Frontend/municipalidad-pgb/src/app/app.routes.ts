import { Routes } from '@angular/router';
import { authGuard } from './core/guard/auth/auth.guard';
import { maintenanceGuard } from './core/guard/maintenance/maintenance.guard';

export const routes: Routes = [
      {
        path: '',
        canActivate: [maintenanceGuard],
        loadComponent: () =>
          import('./pages/home.page/home.page.component').then(
            (m) => m.HomePageComponent
          ),
      },
      {
        path: 'noticias',
        canActivate: [maintenanceGuard],
        loadComponent: () =>
          import('./pages/news.page/news.page.component').then(
            (m) => m.NewsPageComponent
          ),
      },
      {
        path: 'noticias/:id',
        canActivate: [maintenanceGuard],
        loadComponent: () =>
          import('./pages/news-details.page/news-details.page.component').then(
            (m) => m.NewsDetailsPageComponent
          ),
      },
      {
        path: 'login',
        canActivate: [maintenanceGuard],
        loadComponent: () =>
          import('./pages/login.page/login.page.component').then(
            (m) => m.LoginPageComponent
          ),
      },
      {
        path: 'reclamos',
        canActivate: [maintenanceGuard],
        loadComponent: () =>
          import('./pages/complaints.page/complaints.page.component').then(
            (m) => m.ComplaintsPageComponent
          ),
      },
      {
        path: 'eventos',
        canActivate: [maintenanceGuard],
        loadComponent: () =>
          import('./pages/events.page/events.page.component').then(
            (m) => m.EventsPageComponent
          ),
      },
      {
        path: 'admin/dashboard',
        loadComponent: () =>
          import('./pages/admin/dashboard.page/dashboard.page.component').then(
            (m) => m.DashboardPageComponent
          ),
        canActivate: [authGuard, maintenanceGuard],
        data: {
          roles: [
            'ROLE_ADMIN',
            'ROLE_SECRETARIA',
            'ROLE_COMUNICACION',
            'ROLE_RESPONSABLE_RECLAMOS',
            'ROLE_INTENDENTE',
          ],
        },
      },
      {
        path: 'admin/reclamos',
        loadComponent: () =>
          import(
            './pages/admin/complaints/complaints.list.page/complaints.list.page.component'
          ).then((m) => m.ComplaintsListPageComponent),
        canActivate: [authGuard, maintenanceGuard],
        data: {
          roles: ['ROLE_ADMIN', 'ROLE_RESPONSABLE_RECLAMOS', 'ROLE_INTENDENTE'],
        },
      },
      {
        path: 'admin/reclamos/:id/detalle',
        loadComponent: () =>
          import(
            './pages/admin/complaints/complaint-details.page/complaint-details.page.component'
          ).then((m) => m.ComplaintDetailsPageComponent),
        canActivate: [authGuard, maintenanceGuard],
        data: {
          roles: ['ROLE_ADMIN', 'ROLE_RESPONSABLE_RECLAMOS', 'ROLE_INTENDENTE'],
        },
      },
      {
        path: 'admin/reclamos/:id/editar',
        loadComponent: () =>
          import(
            './pages/admin/complaints/complaint-edit.page/complaint-edit.page.component'
          ).then((m) => m.ComplaintEditPageComponent),
        canActivate: [authGuard, maintenanceGuard],
        data: {
          roles: ['ROLE_ADMIN', 'ROLE_RESPONSABLE_RECLAMOS', 'ROLE_INTENDENTE'],
        },
      },
      {
        path: 'admin/reclamos/:id/borrar',
        loadComponent: () =>
          import(
            './pages/admin/complaints/complaint-delete.page/complaint-delete.page.component'
          ).then((m) => m.ComplaintDeletePageComponent),
        canActivate: [authGuard, maintenanceGuard],
        data: {
          roles: ['ROLE_ADMIN', 'ROLE_RESPONSABLE_RECLAMOS', 'ROLE_INTENDENTE'],
        },
      },
  {
    path: 'mantenimiento',
    loadComponent: () =>
      import('./pages/maintenance.page/maintenance.page.component').then(
        (m) => m.MaintenancePageComponent
      ),
  },
  {
    path: '**',
    canActivate: [maintenanceGuard],
    loadComponent: () =>
      import('./pages/not-found.page/not-found.page.component').then(
        (m) => m.NotFoundPageComponent
      ),
  },
];
