import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Input } from '@angular/core';
import { EventResponseDTO } from '../../../models/event.model';

@Component({
  selector: 'app-event-card',
  imports: [RouterModule],
  templateUrl: './event-card.component.html',
  styleUrl: './event-card.component.css'
})
export class EventCardComponent {
  @Input() event!: EventResponseDTO;
}
