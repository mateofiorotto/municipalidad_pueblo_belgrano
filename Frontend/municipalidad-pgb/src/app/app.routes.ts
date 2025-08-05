import { Routes } from '@angular/router';

export const routes: Routes = [
    { path: '', loadComponent: () => import('./pages/home.page/home.page.component').then((m) => m.HomePageComponent) },
    { path: 'noticias', loadComponent: () => import('./pages/news.page/news.page.component').then((m) => m.NewsPageComponent) }
];
