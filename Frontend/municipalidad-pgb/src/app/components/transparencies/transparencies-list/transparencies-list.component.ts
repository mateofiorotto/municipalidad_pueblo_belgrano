import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-transparencies-list',
  imports: [
    RouterLink
  ],
  templateUrl: './transparencies-list.component.html',
  styleUrl: './transparencies-list.component.css',
})
export class TransparenciesListComponent {


  navigateAndScroll(): void {
      window.scrollTo({ top: 0, behavior: 'smooth' });
  }
}
