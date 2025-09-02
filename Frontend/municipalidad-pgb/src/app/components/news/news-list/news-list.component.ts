import { Component, OnInit } from '@angular/core';
import { inject } from '@angular/core';
import { NewsService } from '../../../services/news/news.service';
import { NewsCardComponent } from '../news-card/news-card.component';
import { NewsResponseDTO } from '../../../models/news.models';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-news-list',
  imports: [NewsCardComponent, CommonModule],
  templateUrl: './news-list.component.html',
  styleUrl: './news-list.component.css'
})
export class NewsListComponent implements OnInit {
  private newsService = inject(NewsService);
  
  public newsList: NewsResponseDTO[] = [];

  ngOnInit(): void {
    this.newsService.getNewsList().subscribe({
      next: (data) => { this.newsList = data.result.content, console.log(data)},
      error: (err) => console.error('Error al cargar noticias', err),
    });
  }

}
