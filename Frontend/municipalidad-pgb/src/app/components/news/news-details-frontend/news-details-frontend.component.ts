import { Component } from '@angular/core';
import { NewsResponseDTO } from '../../../models/news.models';
import { NewsService } from '../../../services/news/news.service';
import { inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgIf } from '@angular/common';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { LoaderComponent } from '../../loader/loader.component';

@Component({
  standalone: true,
  selector: 'app-news-details-frontend',
  imports: [CommonModule, NgIf, LoaderComponent],
  templateUrl: './news-details-frontend.component.html',
  styleUrl: './news-details-frontend.component.css',
})
export class NewsDetailsFrontendComponent {
  private _route = inject(ActivatedRoute);
  private _router = inject(Router);
  private _newsService = inject(NewsService);

  news!: NewsResponseDTO;
  public loading: boolean = true;

  ngOnInit(): void {
    const id = this._route.snapshot.paramMap.get('id');

    if (id) {
      this._newsService.getNewsById(+id).subscribe({
        next: (data) => {
          this.news = data.result;

          this.loading = false;
        },
        error: (err) => {
          if (err.status === 404) {
            this._router.navigate(['/no-encontrado']);
          } else {
            this._router.navigate(['/']).then(() => {
              Swal.fire({
                icon: 'error',
                title: 'ERROR',
                text: 'Error al cargar noticia',
                showConfirmButton: true,
              });
            });
          }
        },
      });
    }
  }
}
