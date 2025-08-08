import { Component, Input } from '@angular/core';
import { NewsResponseDTO } from '../../models/news.models';
import { RouterModule } from '@angular/router';


@Component({
  standalone: true,
  selector: 'app-news-card',
  imports: [RouterModule],
  templateUrl: './news-card.component.html',
  styleUrl: './news-card.component.css'
})
export class NewsCardComponent {
  @Input() news!: NewsResponseDTO;
}
