import { Component } from '@angular/core';
import { TransparenciesService } from '../../../services/transparencies/transparencies.service';
import { TransparencyResponseDTO } from '../../../models/transparency.model';
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

@Component({
  selector: 'app-transparency-edit',
  imports: [ReactiveFormsModule],
  templateUrl: './transparency-edit.component.html',
  styleUrl: './transparency-edit.component.css',
})
export class TransparencyEditComponent {
  errors: { defaultMessage: string }[] = [];

  private _transparenciesService = inject(TransparenciesService);
  private _router = inject(Router);
  private _route = inject(ActivatedRoute);

  public transparency!: TransparencyResponseDTO;

  transparencyForm = new FormGroup({
    titulo: new FormControl('', [Validators.required]),
    pdf: new FormControl('', [Validators.required]),
    fecha: new FormControl('', [Validators.required]),
    tipo: new FormControl('', [Validators.required])
  });

  ngOnInit(): void {
    this.loadTransparency();
  }

  onSubmit(): void {
    const val = this.transparencyForm.value;

    if (val.pdf && val.fecha) {
      const transparencyRequest: any = val;

      this._transparenciesService
        .updateTransparency(
          transparencyRequest,
          Number(this._route.snapshot.paramMap.get('id'))
        )
        .subscribe({
          next: (data) => {
            this.errors = [];
            this.transparencyForm.reset();

            this._router.navigate(['/admin/transparencias']).then(() => {
              Swal.fire({
                icon: 'success',
                title: 'Transparencia editada',
                text: 'Transparencia editada correctamente',
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
                text: 'Ocurrio un error al editar la transparencia. Lo estamos solucionando.',
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

  public loadTransparency(): void {
    const id = Number(this._route.snapshot.paramMap.get('id'));

    this._transparenciesService.getTransparencyById(id).subscribe({
      next: (data) => {
        this.transparency = data.result;

        this.transparencyForm.patchValue({
          titulo: this.transparency.titulo,
          pdf: this.transparency.pdf,
          fecha: this.transparency.fecha,
          tipo: this.transparency.tipo
        });
      },
      error: (err) => {
        if (err.status === 404) {
          this._router.navigate(['/no-encontrado']);
        } else {
          this._router.navigate(['/admin/transparencias']).then(() => {
            Swal.fire({
              icon: 'error',
              title: 'ERROR',
              text: 'Error al cargar la transparencia',
              showConfirmButton: true,
            });
          });
        }
      },
    });
  }

  get titulo() {
    return this.transparencyForm.get('titulo');
  }

  get pdf() {
    return this.transparencyForm.get('pdf');
  }

  get fecha() {
    return this.transparencyForm.get('fecha');
  }
  
  get tipo() {
    return this.transparencyForm.get('tipo');
  }
}
