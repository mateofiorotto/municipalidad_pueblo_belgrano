import { Component } from '@angular/core';
import { ComplaintService } from '../../../services/complaints/complaints.service';
import { inject } from '@angular/core';
import { ReactiveFormsModule, Validators } from '@angular/forms';
import { FormControl, FormGroup } from '@angular/forms';
import { ComplaintRequestDTO } from '../../../models/complaint.model';
import Swal from 'sweetalert2';

@Component({
  standalone: true,
  selector: 'app-create-complaint',
  imports: [ReactiveFormsModule],
  templateUrl: './create-complaint.component.html',
  styleUrl: './create-complaint.component.css',
})
export class CreateComplaintComponent {
  errors: { defaultMessage: string }[] = [];
  private _complaintService = inject(ComplaintService);

  complaintsForm = new FormGroup({
    motivo: new FormControl('', [
      Validators.required,
      Validators.minLength(5),
      Validators.maxLength(100),
    ]),
    descripcion: new FormControl('', [
      Validators.required,
      Validators.minLength(20),
      Validators.maxLength(1000),
    ]),
    celular: new FormControl('', [
      Validators.required,
      Validators.minLength(6),
      Validators.maxLength(20),
      Validators.pattern('^[0-9]+$'),
    ]),
    email: new FormControl('', [Validators.required, Validators.email]),
    direccion: new FormControl('', [
      Validators.required,
      Validators.minLength(5),
      Validators.maxLength(150),
    ]),
    imagen: new FormControl(''),
    nombre_apellido: new FormControl('', [
      Validators.required,
      Validators.minLength(5),
      Validators.maxLength(100),
    ]),
  });

  onSubmit(): void {
    const val = this.complaintsForm.value;

    if (
      val.motivo &&
      val.descripcion &&
      val.celular &&
      val.email &&
      val.direccion &&
      val.nombre_apellido
    ) {
      this._complaintService
        .createComplaint(val as ComplaintRequestDTO)
        .subscribe({
          next: (data) => {
            this.errors = [];
            this.complaintsForm.reset();
            Swal.fire({
              icon: 'success',
              title: 'Reclamo Recibido',
              text: 'Tu reclamo se envió correctamente',
              showConfirmButton: true,
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
            } else if (err.status == 429) {
              Swal.fire({
                icon: 'error',
                title: 'ERROR',
                text: 'Solo puedes enviar un reclamo cada 6 horas',
                showConfirmButton: true,
              });
            } else {
              Swal.fire({
                icon: 'error',
                title: 'ERROR',
                text: 'Ocurrio un error al enviar el reclamo. Lo estamos solucionando.',
                showConfirmButton: true,
              });
            }
          },
        });
    } else {
      Swal.fire({
        icon: 'error',
        title: 'ERROR',
        text: 'Completa los campos requeridos',
        showConfirmButton: true,
      });
    }
  }

  get motivo() {
    return this.complaintsForm.get('motivo');
  }

  get descripcion() {
    return this.complaintsForm.get('descripcion');
  }

  get celular() {
    return this.complaintsForm.get('celular');
  }

  get email() {
    return this.complaintsForm.get('email');
  }

  get direccion() {
    return this.complaintsForm.get('direccion');
  }

  get imagen() {
    return this.complaintsForm.get('imagen');
  }

  get nombre_apellido() {
    return this.complaintsForm.get('nombre_apellido');
  }
}
