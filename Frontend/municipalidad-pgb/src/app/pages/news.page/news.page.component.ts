import { Component } from '@angular/core';
import { NewsListComponent } from '../../components/news-list/news-list.component';

@Component({
  standalone: true,
  selector: 'app-news.page',
  imports: [NewsListComponent],
  templateUrl: './news.page.component.html',
  styleUrl: './news.page.component.css'
})
export class NewsPageComponent {
  
}
