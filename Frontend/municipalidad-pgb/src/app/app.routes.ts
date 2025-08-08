import { Routes } from '@angular/router';
import { authGuard } from './core/auth.guard';

export const routes: Routes = [
    { 
        path: '', 
        loadComponent: () => import('./pages/home.page/home.page.component').then((m) => m.HomePageComponent) 
    },
    { 
        path: 'noticias', 
        loadComponent: () => import('./pages/news.page/news.page.component').then((m) => m.NewsPageComponent) 
    },
    { 
        path: 'noticias/:id', 
        loadComponent: () => import('./pages/news-details.page/news-details.page.component').then((m) => m.NewsDetailsPageComponent) 
    },
    { 
        path: 'login', 
        loadComponent: () => import('./pages/login.page/login.page.component').then((m) => m.LoginPageComponent) 
    },
    {
        path: 'reclamos',
        loadComponent: () => import('./pages/complaints.page/complaints.page.component').then((m) => m.ComplaintsPageComponent)
    },
    {
        path: 'eventos',
        loadComponent: () => import('./pages/events.page/events.page.component').then((m) => m.EventsPageComponent)
    },
    { 
        path: 'dashboard', 
        loadComponent: () => import('./pages/admin/dashboard.page/dashboard.page.component').then((m) => m.DashboardPageComponent),
        canActivate: [authGuard],
        data: { roles: [
            'ROLE_ADMIN',
            'ROLE_SECRETARIA',
            'ROLE_COMUNICACION',
            'ROLE_RESPONSABLE_RECLAMOS',
            "ROLE_INTENDENTE"
        ] }
    },
    { 
        path: '**', 
        loadComponent: () => import('./pages/notfound.page/notfound.page.component').then((m) => m.NotfoundPageComponent)
    }
];
