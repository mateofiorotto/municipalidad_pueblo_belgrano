import { Component } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EventsService } from '../../../services/events/events.service';
import { EventResponseDTO } from '../../../models/event.model';

@Component({
  selector: 'app-event-edit',
  imports: [ReactiveFormsModule],
  templateUrl: './event-edit.component.html',
  styleUrl: './event-edit.component.css',
})
export class EventEditComponent {
  errors: { defaultMessage: string }[] = [];

  private _eventsService = inject(EventsService)
  private _router = inject(Router);
  private _route = inject(ActivatedRoute);

  public event!: EventResponseDTO;

  eventForm = new FormGroup({
    titular: new FormControl('', [
      Validators.required,
      Validators.minLength(15),
      Validators.maxLength(100),
    ]),
    fecha: new FormControl('', [Validators.required]),
    imagen: new FormControl('', [Validators.required]),
    descripcion: new FormControl('', [
      Validators.required,
      Validators.minLength(20),
      Validators.maxLength(1500),
    ]),
    descripcion_adicional: new FormControl('', [Validators.maxLength(1500)]),
  });

  ngOnInit(): void {
    this.loadEvent();
  }

  onSubmit(): void {
    const val = this.eventForm.value;

    if (
      val.titular &&
      val.fecha &&
      val.imagen &&
      val.descripcion
    ) {
      const eventRequest: any = val;

      this._eventsService
        .updateEvent(
          eventRequest,
          Number(this._route.snapshot.paramMap.get('id'))
        )
        .subscribe({
          next: (data) => {
            this.errors = [];
            this.eventForm.reset();

            this._router.navigate(['/admin/eventos']).then(() => {
              Swal.fire({
                icon: 'success',
                title: 'Evento editado',
                text: 'Evento editado correctamente',
                showConfirmButton: true,
              });
            });
          },
          error: (err) => {
            if (err.error && Array.isArray(err.error.errors)) {
              this.errors = err.error.errors;

              const listaErrores = `
              <ul style="text-align:left">
                ${this.errors
                  .map((e) => `<li>${e.defaultMessage}</li>`)
                  .join('')}
              </ul>
            `;

              Swal.fire({
                icon: 'error',
                title: 'ERROR',
                html: listaErrores,
                showConfirmButton: true,
              });
            } else if (err.status == 400) {
              Swal.fire({
                icon: 'error',
                title: 'ERROR',
                text: err.error.message,
                showConfirmButton: true,
              });
            } else {
              Swal.fire({
                icon: 'error',
                title: 'ERROR',
                text: 'Ocurrio un error al editar el evento. Lo estamos solucionando.',
                showConfirmButton: true,
              });
            }
          },
        });
    } else {
      Swal.fire({
        icon: 'error',
        title: 'ERROR',
        text: 'Todos los campos son requeridos',
        showConfirmButton: true,
      });
    }
  }

  public loadEvent(): void {
    const id = Number(this._route.snapshot.paramMap.get('id'));

    this._eventsService.getEventById(id).subscribe({
      next: (data) => {
        this.event = data.result;

        // patchValue para setear los valores en el form
        this.eventForm.patchValue({
          titular: this.event.titular,
          fecha: this.event.fecha,
          imagen: this.event.imagen,
          descripcion: this.event.descripcion,
          descripcion_adicional: this.event.descripcion_adicional,
        });
      },
      error: (err) => {
        if (err.status === 404) {
          this._router.navigate(['/no-encontrado']);
        } else {
          this._router.navigate(['/admin/eventos']).then(() => {
            Swal.fire({
              icon: 'error',
              title: 'ERROR',
              text: 'Error al cargar el evento o no estas autorizado/a',
              showConfirmButton: true,
            });
          });
        }
      },
    });
  }

  get titular() {
    return this.eventForm.get('titular');
  }

  get fecha() {
    return this.eventForm.get('fecha');
  }

  get imagen() {
    return this.eventForm.get('imagen');
  }

  get descripcion() {
    return this.eventForm.get('descripcion');
  }

  get descripcion_adicional() {
    return this.eventForm.get('descripcion_adicional');
  }
}
