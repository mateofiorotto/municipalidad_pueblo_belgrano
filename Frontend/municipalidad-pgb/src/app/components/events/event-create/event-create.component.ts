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
import { EventsService } from '../../../services/events/events.service';

@Component({
  selector: 'app-event-create',
  imports: [ReactiveFormsModule],
  templateUrl: './event-create.component.html',
  styleUrl: './event-create.component.css'
})
export class EventCreateComponent {
  errors: { defaultMessage: string }[] = [];

  private _eventsService = inject(EventsService);
  private _router = inject(Router);

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
    descripcion_adicional: new FormControl('', [Validators.maxLength(1500)])
  });

  onSubmit(): void {
    const val = this.eventForm.value;

    if (
      val.titular &&
      val.fecha &&
      val.imagen &&
      val.descripcion
    ) {
      const eventRequest: any = val;

      this._eventsService.createEvent(eventRequest).subscribe({
        next: (data) => {
          this.errors = [];
          this.eventForm.reset();

          this._router.navigate(['/admin/eventos']).then(() => {
            Swal.fire({
              icon: 'success',
              title: 'Evento creado',
              text: 'Evento creado correctamente',
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
              text: 'Ocurrio un error al crear el evento. Lo estamos solucionando.',
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
