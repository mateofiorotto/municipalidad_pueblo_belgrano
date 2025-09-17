import { Component } from '@angular/core';
import { NewsResponseDTO } from '../../../models/news.models';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { inject } from '@angular/core';
import { NewsService } from '../../../services/news/news.service';

@Component({
  selector: 'app-news-details',
  imports: [],
  templateUrl: './news-details.component.html',
  styleUrl: './news-details.component.css'
})
export class NewsDetailsComponent {
private _route = inject(ActivatedRoute);
  private _router = inject(Router);
  private _newsService = inject(NewsService);

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
}