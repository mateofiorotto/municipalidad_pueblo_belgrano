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
  //reclamos
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
  //areas
  {
    path: 'admin/areas',
    loadComponent: () =>
      import('./pages/admin/areas/areas-list.page/areas-list.page.component').then(
        (m) => m.AreasListPageComponent
      ),
    canActivate: [authGuard, maintenanceGuard],
    data: {
      roles: ['ROLE_ADMIN', 'ROLE_INTENDENTE', 'ROLE_RESPONSABLE_RECLAMOS'],
    },
  },
  {
    path: 'admin/areas/crear',
    loadComponent: () =>
      import('./pages/admin/areas/area-create.page/area-create.page.component').then(
        (m) => m.AreaCreatePageComponent
      ),
    canActivate: [authGuard, maintenanceGuard],
    data: {
      roles: ['ROLE_ADMIN', 'ROLE_INTENDENTE', 'ROLE_RESPONSABLE_RECLAMOS'],
    },
  },
  {
    path: 'admin/areas/:id/borrar',
    loadComponent: () =>
      import('./pages/admin/areas/area-delete.page/area-delete.page.component').then(
        (m) => m.AreaDeletePageComponent
      ),
    canActivate: [authGuard, maintenanceGuard],
    data: {
      roles: ['ROLE_ADMIN', 'ROLE_INTENDENTE', 'ROLE_RESPONSABLE_RECLAMOS'],
    },
  },
  {
    path: 'admin/areas/:id/detalle',
    loadComponent: () =>
      import('./pages/admin/areas/area-details.page/area-details.page.component').then(
        (m) => m.AreaDetailsPageComponent
      ),
    canActivate: [authGuard, maintenanceGuard],
    data: {
      roles: ['ROLE_ADMIN', 'ROLE_INTENDENTE', 'ROLE_RESPONSABLE_RECLAMOS'],
    },
  },
  {
    path: 'admin/areas/:id/editar',
    loadComponent: () =>
      import('./pages/admin/areas/area-edit.page/area-edit.page.component').then(
        (m) => m.AreaEditPageComponent
      ),
    canActivate: [authGuard, maintenanceGuard],
    data: {
      roles: ['ROLE_ADMIN', 'ROLE_INTENDENTE', 'ROLE_RESPONSABLE_RECLAMOS'],
    },
  },
//noticias
  {
    path: 'admin/noticias',
    loadComponent: () =>
      import(
        './pages/admin/news/news-list.page/news.list.page.component'
      ).then((m) => m.NewsListPageComponent),
    canActivate: [authGuard, maintenanceGuard],
    data: {
      roles: ['ROLE_ADMIN', 'ROLE_INTENDENTE', 'ROLE_COMUNICACION'],
    },
  },
  {
    path: 'admin/noticias/crear',
    loadComponent: () =>
      import('./pages/admin/news/news-create.page/news.create.page.component').then(
        (m) => m.NewsCreatePageComponent
      ),
    canActivate: [authGuard, maintenanceGuard],
    data: {
      roles: ['ROLE_ADMIN', 'ROLE_INTENDENTE', 'ROLE_COMUNICACION'],
    },
  },
  {
    path: 'admin/noticias/:id/editar',
    loadComponent: () =>
      import('./pages/admin/news/news-edit.page/news.edit.page.component').then(
        (m) => m.NewsEditPageComponent
      ),
    canActivate: [authGuard, maintenanceGuard],
    data: {
      roles: ['ROLE_ADMIN', 'ROLE_INTENDENTE', 'ROLE_COMUNICACION'],
    },
  },
  {
    path: 'admin/noticias/:id/borrar',
    loadComponent: () =>
      import('./pages/admin/news/news-delete.page/news.delete.page.component').then(
        (m) => m.NewsDeletePageComponent
      ),
    canActivate: [authGuard, maintenanceGuard],
    data: {
      roles: ['ROLE_ADMIN', 'ROLE_INTENDENTE', 'ROLE_COMUNICACION'],
    },
  },
  {
    path: 'admin/noticias/:id/detalle',
    loadComponent: () =>
      import(
        './pages/admin/news/news-details.page/news.details.page.component'
      ).then((m) => m.NewsDetailsPageComponent),
    canActivate: [authGuard, maintenanceGuard],
    data: {
      roles: ['ROLE_ADMIN', 'ROLE_INTENDENTE', 'ROLE_COMUNICACION'],
    },
  },
  //categorias
  {
    path: 'admin/categorias',
    loadComponent: () =>
      import(
        './pages/admin/categories/categories-list.page/categories-list.page.component'
      ).then((m) => m.CategoriesListPageComponent),
    canActivate: [authGuard, maintenanceGuard],
    data: {
      roles: ['ROLE_ADMIN', 'ROLE_INTENDENTE', 'ROLE_COMUNICACION'],
    },
  },
  {
    path: 'admin/categorias/crear',
    loadComponent: () =>
      import(
        './pages/admin/categories/category-create.page/category-create.page.component'
      ).then((m) => m.CategoryCreatePageComponent),
    canActivate: [authGuard, maintenanceGuard],
    data: {
      roles: ['ROLE_ADMIN', 'ROLE_INTENDENTE', 'ROLE_COMUNICACION'],
    },
  },
  {
    path: 'admin/categorias/:id/borrar',
    loadComponent: () =>
      import(
        './pages/admin/categories/category-delete.page/category-delete.page.component'
      ).then((m) => m.CategoryDeletePageComponent),
    canActivate: [authGuard, maintenanceGuard],
    data: {
      roles: ['ROLE_ADMIN', 'ROLE_INTENDENTE', 'ROLE_COMUNICACION'],
    },
  },
  {
    path: 'admin/categorias/:id/editar',
    loadComponent: () =>
      import(
        './pages/admin/categories/category-edit.page/category-edit.page.component'
      ).then((m) => m.CategoryEditPageComponent),
    canActivate: [authGuard, maintenanceGuard],
    data: {
      roles: ['ROLE_ADMIN', 'ROLE_INTENDENTE', 'ROLE_COMUNICACION'],
    },
  },
  {
    path: 'admin/categorias/:id/detalle',
    loadComponent: () =>
      import(
        './pages/admin/categories/category-details.page/category-details.page.component'
      ).then((m) => m.CategoryDetailsPageComponent),
    canActivate: [authGuard, maintenanceGuard],
    data: {
      roles: ['ROLE_ADMIN', 'ROLE_INTENDENTE', 'ROLE_COMUNICACION'],
    },
  },
  //transparencias
  {
    path: 'transparencias',
    loadComponent: () =>
      import('./pages/transparencies.page/transparencies.page.component').then(
        (m) => m.TransparenciesPageComponent
      ),
      canActivate: [maintenanceGuard],
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
