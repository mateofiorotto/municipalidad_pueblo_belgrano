import { Component } from '@angular/core';
import { inject } from '@angular/core';
import { NewsService } from '../../services/news/news.service';
import { NewsResponseDTO } from '../../models/news.models';
import { NewsCardComponent } from '../../components/news/news-card/news-card.component';
import { NgFor } from '@angular/common';
import { LoaderComponent } from '../../components/loader/loader.component';

@Component({
  standalone: true,
  selector: 'app-home.page',
  imports: [NewsCardComponent, NgFor, LoaderComponent],
  templateUrl: './home.page.component.html',
  styleUrl: './home.page.component.css'
})
export class HomePageComponent {
    private _newsService = inject(NewsService)

    public lastThreeNews: NewsResponseDTO[] = [];
    public loading: boolean = true;
    
      ngOnInit(): void {
        this._newsService.getLastThreeNews().subscribe({
          next: (data) => { this.lastThreeNews = data.result, this.loading = false },
          
        });
      }
}
