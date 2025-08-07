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
        path: 'dashboard', 
        loadComponent: () => import('./pages/dashboard.page/dashboard.page.component').then((m) => m.DashboardPageComponent),
        canActivate: [authGuard] 
    },
    { 
        path: '**', 
        loadComponent: () => import('./pages/notfound.page/notfound.page.component').then((m) => m.NotfoundPageComponent)
    }
];
