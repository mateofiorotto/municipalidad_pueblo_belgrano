import { Component } from '@angular/core';
import { EventsListComponent } from '../../components/events/events-list/events-list.component';

@Component({
  selector: 'app-events.page',
  imports: [EventsListComponent],
  templateUrl: './events.page.component.html',
  styleUrl: './events.page.component.css'
})
export class EventsPageComponent {

}
