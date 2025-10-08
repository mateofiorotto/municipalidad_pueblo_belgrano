import { Component } from '@angular/core';
import { LoaderComponent } from '../../loader/loader.component';

@Component({
  selector: 'app-event-details-frontend',
  imports: [LoaderComponent],
  templateUrl: './event-details-frontend.component.html',
  styleUrl: './event-details-frontend.component.css',
})
export class EventDetailsFrontendComponent {
  public loading: boolean = true;

  ngOnInit(): void {
    //this.loading = false;
  }
}
