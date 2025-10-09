import { Component, OnInit } from '@angular/core';
import { inject } from '@angular/core';
import { NewsService } from '../../../services/news/news.service';
import { NewsCardComponent } from '../news-card/news-card.component';
import { NewsResponseDTO } from '../../../models/news.models';
import { CommonModule } from '@angular/common';
import { MatPaginatorModule } from '@angular/material/paginator';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { NewsListResponse } from '../../../models/news.models';
import { LoaderComponent } from '../../loader/loader.component';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-news-list-by-category',
  imports: [
    NewsCardComponent,
    CommonModule,
    MatPaginatorModule,
    LoaderComponent,
  ],
  templateUrl: './news-list-by-category.component.html',
  styleUrl: './news-list-by-category.component.css',
})
export class NewsListByCategoryComponent {
  private _newsService = inject(NewsService);
  private _router = inject(Router);
  private _route = inject(ActivatedRoute);

  public loading: boolean = true;
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
    this._newsService
      .getNewsListByCategory(this._route.snapshot.params['id'], page)
      .subscribe({
        next: (data) => {
          this.setNewsData(data);

          this.loading = false;
        },
        error: (err) => {
          this._router.navigate(['/']).then(() => {
            if (err.status === 404) {
              this._router.navigate(['/no-encontrado']);
            } else {
              Swal.fire({
                icon: 'error',
                title: 'ERROR',
                text: 'Error al cargar noticias',
                showConfirmButton: true,
              });
            }
          });
        },
      });
  }

  public onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;

    this.loadNews(this.pageIndex);

    window.scrollTo(0, 100);
  }
}
