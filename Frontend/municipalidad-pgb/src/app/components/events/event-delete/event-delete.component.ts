import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { inject } from '@angular/core';
import { EventsService } from '../../../services/events/events.service';
import { EventResponseDTO } from '../../../models/event.model';

@Component({
  selector: 'app-event-delete',
  imports: [],
  templateUrl: './event-delete.component.html',
  styleUrl: './event-delete.component.css',
})
export class EventDeleteComponent {
  private _route = inject(ActivatedRoute);
  private _router = inject(Router);
  private _eventsService = inject(EventsService);
  private eventId!: number;

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
                text: 'Error al cargar evento o no estas autorizado/a',
                showConfirmButton: true,
              });
            });
          }
        },
      });
    }
  }

  deleteEvent(): void {
    this.eventId = this.event.id;

    this._eventsService.deleteEvent(this.eventId).subscribe({
      next: () => {
        this._router.navigate(['/admin/eventos']);
        Swal.fire({
          icon: 'success',
          title: 'Evento Eliminado',
          text: 'Evento eliminado correctamente',
          showConfirmButton: true,
        });
      },
      error: (err) => {
        this._router.navigate(['/admin/eventos']).then(() => {
          if (err.status === 409) {
            Swal.fire({
              icon: 'error',
              title: 'ERROR',
              text: 'Error al eliminar el evento porque tiene noticias asociadas',
              showConfirmButton: true,
            });
          } else {

          Swal.fire({
            icon: 'error',
            title: 'ERROR',
            text: 'Error al eliminar el evento',
            showConfirmButton: true,
          });
          }
        });
      
      },
    });
  }

  cancel(): void {
    this._router.navigate(['/admin/eventos']);
  }
}
