import { Component } from '@angular/core';
import { NewsListByCategoryComponent } from '../../components/news/news-list-by-category/news-list-by-category.component';

@Component({
  selector: 'app-news-by-category.page',
  imports: [NewsListByCategoryComponent],
  templateUrl: './news-by-category.page.component.html',
  styleUrl: './news-by-category.page.component.css'
})
export class NewsByCategoryPageComponent {
  
}
