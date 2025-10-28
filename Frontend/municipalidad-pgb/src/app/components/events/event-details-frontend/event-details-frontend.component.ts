import { Component } from '@angular/core';
import Swal from 'sweetalert2';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { DatePipe, NgIf } from '@angular/common';
import { CommonModule } from '@angular/common';
import { LoaderComponent } from '../../loader/loader.component';
import { EventsService } from '../../../services/events/events.service';
import { EventResponseDTO } from '../../../models/event.model';
import { inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-event-details-frontend',
  imports: [CommonModule, NgIf, LoaderComponent, RouterLink, DatePipe],
  templateUrl: './event-details-frontend.component.html',
  styleUrl: './event-details-frontend.component.css',
})
export class EventDetailsFrontendComponent {
  private _route = inject(ActivatedRoute);
  private _router = inject(Router);
  private _eventsService = inject(EventsService);

  public loading: boolean = true;
  event!: EventResponseDTO;

  ngOnInit(): void {
    const id = this._route.snapshot.paramMap.get('id');

    if (id) {
      this._eventsService.getEventById(+id).subscribe({
        next: (data) => {
          this.event = data.result;

          this.loading = false;
        },
        error: (err) => {
          if (err.status === 404) {
            this._router.navigate(['/no-encontrado']);
          } else {
            this._router.navigate(['/']).then(() => {
              Swal.fire({
                icon: 'error',
                title: 'ERROR',
                text: 'Error al cargar noticia',
                showConfirmButton: true,
              });
            });
          }
        },
      });
    }
  }
}
