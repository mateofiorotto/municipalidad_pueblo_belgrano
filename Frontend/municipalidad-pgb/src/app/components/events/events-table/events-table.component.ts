import { Component } from '@angular/core';
import { inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import Swal from 'sweetalert2';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator } from '@angular/material/paginator';
import { PageEvent } from '@angular/material/paginator';
import { EventsService } from '../../../services/events/events.service';
import { EventListResponse, EventResponseDTO } from '../../../models/event.model';

@Component({
  selector: 'app-events-table',
  imports: [MatIconModule, MatPaginator, RouterLink],
  templateUrl: './events-table.component.html',
  styleUrl: './events-table.component.css',
})
export class EventsTableComponent {
  private _eventsService = inject(EventsService);
  private _router = inject(Router);

  public eventsList: EventResponseDTO[] = [];
  public error: any;
  public pageIndex: number = 0;
  public totalElements = 0;

  private setEventsData(data: EventListResponse): void {
    this.eventsList = data.result.content;
    this.pageIndex = data.result.page.number;
    this.totalElements = data.result.page.totalElements;
    this.error = null;
  }

  ngOnInit(): void {
    this.loadNews(this.pageIndex);
  }

  public loadNews(page: number): void {
    this._eventsService.getEventListPaginated(page).subscribe({
      next: (data) => {
        this.setEventsData(data);
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

    this.loadNews(this.pageIndex);
  }
}
