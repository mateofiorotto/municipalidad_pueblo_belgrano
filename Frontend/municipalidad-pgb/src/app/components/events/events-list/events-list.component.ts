import { Component } from '@angular/core';
import { inject } from '@angular/core';
import { EventCardComponent } from '../event-card/event-card.component';
import { CommonModule } from '@angular/common';
import { MatPaginatorModule } from '@angular/material/paginator';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { EventsService } from '../../../services/events/events.service';
import { EventListResponse, EventResponseDTO } from '../../../models/event.model';
import { LoaderComponent } from '../../loader/loader.component';

@Component({
  selector: 'app-events-list',
  imports: [EventCardComponent, CommonModule, MatPaginatorModule, LoaderComponent],
  templateUrl: './events-list.component.html',
  styleUrl: './events-list.component.css',
})
export class EventsListComponent {
  private _eventsService = inject(EventsService);
  private _router = inject(Router);

  public eventsList: EventResponseDTO[] = [];
  public error: any;
  public pageIndex: number = 0;
  public totalElements = 0;
  public loading: boolean = true;

  private setEventsData(data: EventListResponse): void {
    this.eventsList = data.result.content;
    this.pageIndex = data.result.page.number;
    this.totalElements = data.result.page.totalElements;
    this.error = null;
  }

  ngOnInit(): void {
    this.loadEvents(this.pageIndex);
  }

  public loadEvents(page: number): void {
    this._eventsService.getEventListPaginated(page).subscribe({
      next: (data) => {
        this.setEventsData(data);

        this.loading = false;
      },
      error: (err) => {
        this._router.navigate(['/']).then(() => {
          Swal.fire({
            icon: 'error',
            title: 'ERROR',
            text: 'Error al cargar eventos',
            showConfirmButton: true,
          });
        });
      },
    });
  }

  public onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;

    this.loadEvents(this.pageIndex);

    window.scrollTo(0, 100);
  }
}
