import { Component } from '@angular/core';
import { NewsResponseDTO } from '../../../models/news.models';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { inject } from '@angular/core';
import { NewsService } from '../../../services/news/news.service';


@Component({
  selector: 'app-news-delete',
  imports: [],
  templateUrl: './news-delete.component.html',
  styleUrl: './news-delete.component.css'
})
export class NewsDeleteComponent {
  private _route = inject(ActivatedRoute);
  private _router = inject(Router);
  private _newsService = inject(NewsService);
  private newsId!: number;
  
  public news!: NewsResponseDTO;

  ngOnInit(): void {
    const id = this._route.snapshot.paramMap.get('id');

    if (id) {
      this._newsService.getNewsById(+id).subscribe({
        next: (data) => {
          this.news = data.result;
        },
        error: (err) => {
          if (err.status === 404) {
            this._router.navigate(['/no-encontrado']);
          } else {
            this._router.navigate(['/']).then(() => {
              Swal.fire({
                icon: 'error',
                title: 'ERROR',
                text: 'Error al cargar noticia o no estas autorizado/a',
                showConfirmButton: true,
              });
            });
          }
        },
      });
    }
}

deleteNews(): void {
    this.newsId = this.news.id;

    this._newsService.deleteNews(this.newsId).subscribe({
      next: () => {
        this._router.navigate(['/admin/noticias']);
        Swal.fire({
          icon: 'success',
          title: 'Noticia Eliminada',
          text: 'Noticia eliminada correctamente',
          showConfirmButton: true,
        });
      },
      error: (err) => {
        this._router.navigate(['/admin/noticias']).then(() => {
          Swal.fire({
            icon: 'error',
            title: 'ERROR',
            text: 'Error al eliminar la noticia',
            showConfirmButton: true,
          });
        });
      },
    });
}

cancel(): void {
    this._router.navigate(['/admin/noticias']);
  }
}