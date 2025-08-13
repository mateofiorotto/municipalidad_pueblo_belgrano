import { Component } from '@angular/core';
import { ComplaintService } from '../../../services/complaints/complaints.service';
import { inject } from '@angular/core';
import { ReactiveFormsModule, Validators } from '@angular/forms';
import { FormControl, FormGroup } from '@angular/forms';
import { ComplaintRequestDTO } from '../../../models/complaint.model';

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
          },
          error: (err) => {
            if (err.error && Array.isArray(err.error.errors)) {
              this.errors = err.error.errors;
            } else {
              this.errors = [{ defaultMessage: 'Error desconocido' }];
            }
          },
        });
    } else {
      this.errors = [{ defaultMessage: 'Todos los campos son requeridos' }];
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
