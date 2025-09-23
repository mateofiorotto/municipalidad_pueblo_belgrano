import { Component } from '@angular/core';
import { NewsTableComponent } from '../../../../components/news/news-table/news-table.component';

@Component({
  selector: 'app-news.list.page',
  imports: [NewsTableComponent],
  templateUrl: './news.list.page.component.html',
  styleUrl: './news.list.page.component.css'
})
export class NewsListPageComponent {

}
