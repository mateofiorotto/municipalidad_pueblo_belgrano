import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { inject } from '@angular/core';
import { EventResponseDTO } from '../../../models/event.model';
import { EventsService } from '../../../services/events/events.service';

@Component({
  selector: 'app-event-details',
  imports: [],
  templateUrl: './event-details.component.html',
  styleUrl: './event-details.component.css',
})
export class EventDetailsComponent {
  private _route = inject(ActivatedRoute);
  private _router = inject(Router);
  private _eventsService = inject(EventsService);

  public event!: EventResponseDTO;

  ngOnInit(): void {
    const id = this._route.snapshot.paramMap.get('id');

    if (id) {
      this._eventsService.getEventById(+id).subscribe({
        next: (data) => {
          this.event = data.result;
        },
        error: (err) => {
          if (err.status === 404) {
            this._router.navigate(['/no-encontrado']);
          } else {
            this._router.navigate(['/']).then(() => {
              Swal.fire({
                icon: 'error',
                title: 'ERROR',
                text: 'Error al cargar evento',
                showConfirmButton: true,
              });
            });
          }
        },
      });
    }
  }
}
