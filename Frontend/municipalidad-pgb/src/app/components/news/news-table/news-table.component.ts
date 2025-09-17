import { Component } from '@angular/core';
import { inject } from '@angular/core';
import { NewsService } from '../../../services/news/news.service';
import { Router, RouterLink } from '@angular/router';
import { NewsListResponse, NewsResponseDTO } from '../../../models/news.models';
import Swal from 'sweetalert2';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator } from '@angular/material/paginator';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-news-table',
  imports: [MatIconModule, MatPaginator, RouterLink],
  templateUrl: './news-table.component.html',
  styleUrl: './news-table.component.css',
})
export class NewsTableComponent {
  private _newsService = inject(NewsService);
  private _router = inject(Router);

  public newsList: NewsResponseDTO[] = [];
  public error: any;
  public pageIndex: number = 0;
  public totalElements = 0;

   private setNewsData(data: NewsListResponse): void {
      this.newsList = data.result.content;
      this.pageIndex = data.result.page.number;
      this.totalElements = data.result.page.totalElements;
      this.error = null;
    }

  ngOnInit(): void {
    this.loadNews(this.pageIndex);
  }

  public loadNews(page: number): void {
    this._newsService.getNewsList(page).subscribe({
      next: (data) => {
        this.setNewsData(data);
      },
      error: (err) => {
        this._router.navigate(['/']).then(() => {
          Swal.fire({
            icon: 'error',
            title: 'ERROR',
            text: 'Error al cargar noticias',
            showConfirmButton: true,
          });
        });
      },
    });
  }

  public onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    
    this.loadNews(this.pageIndex);
  }
}
