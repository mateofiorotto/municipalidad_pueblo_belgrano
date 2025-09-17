import { Component } from '@angular/core';
import { NewsDetailsFrontendComponent } from '../../components/news/news-details-frontend/news-details-frontend.component';
import { CommonModule } from '@angular/common';
import { NgIf } from '@angular/common';


@Component({
  standalone: true,
  selector: 'app-news-details.page',
  imports: [NewsDetailsFrontendComponent, CommonModule, NgIf],
  templateUrl: './news-details.page.component.html',
  styleUrl: './news-details.page.component.css'
})
export class NewsDetailsPageComponent {

}
