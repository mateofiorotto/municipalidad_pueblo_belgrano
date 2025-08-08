import { Component } from '@angular/core';
import { NewsResponseDTO } from '../../models/news.models';
import { NewsService } from '../../services/news/news.service';
import { inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgIf } from '@angular/common';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-news-details',
  imports: [CommonModule, NgIf],
  templateUrl: './news-details.component.html',
  styleUrl: './news-details.component.css',
})
export class NewsDetailsComponent {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private newsService = inject(NewsService);

  news!: NewsResponseDTO;

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      this.newsService.getNewsById(+id).subscribe({
        next: (data) => {
          this.news = data.result;
        },
        error: (err) => {
          if (err.status === 404) {
            this.router.navigate(['/not-found']);
          } else {
            console.error('Error al cargar noticias', err);
          }
      },
      });
    }
  }
}
