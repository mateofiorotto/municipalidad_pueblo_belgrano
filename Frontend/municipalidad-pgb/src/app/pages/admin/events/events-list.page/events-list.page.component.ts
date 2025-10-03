import { Component } from '@angular/core';
import { EventsTableComponent } from '../../../../components/events/events-table/events-table.component';

@Component({
  selector: 'app-events-list.page',
  imports: [EventsTableComponent],
  templateUrl: './events-list.page.component.html',
  styleUrl: './events-list.page.component.css'
})
export class EventsListPageComponent {

}
