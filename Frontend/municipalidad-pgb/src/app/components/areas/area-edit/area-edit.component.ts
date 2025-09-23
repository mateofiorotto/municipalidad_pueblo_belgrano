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
import { AreasService } from '../../../services/areas/areas.service';
import { AreaResponseDTO } from '../../../models/area.model';

@Component({
  selector: 'app-area-edit',
  imports: [ReactiveFormsModule],
  templateUrl: './area-edit.component.html',
  styleUrl: './area-edit.component.css'
})
export class AreaEditComponent {
errors: { defaultMessage: string }[] = [];
  private _areaService = inject(AreasService);
  private _router = inject(Router);
  private _route = inject(ActivatedRoute);

  public area!: AreaResponseDTO;

  areaForm = new FormGroup({
    nombre: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(100),
    ])
  });

  ngOnInit(): void {
    this.loadArea();
  }

  onSubmit(): void {
    const val = this.areaForm.value;

    if (
      val.nombre
    ) {
      const areaRequest: any = val;

      this._areaService.updateArea(areaRequest, Number(this._route.snapshot.paramMap.get('id'))).subscribe({
        next: (data) => {
          this.errors = [];
          this.areaForm.reset();

          this._router.navigate(['/admin/areas']).then(() => {
            Swal.fire({
              icon: 'success',
              title: 'Área editada',
              text: 'Área editada correctamente',
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
          } else if (err.status == 409) {
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
              text: 'Ocurrio un error al editar el Área. Lo estamos solucionando.',
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

  public loadArea(): void {
    const id = Number(this._route.snapshot.paramMap.get('id'));

    this._areaService.getAreaById(id).subscribe({
      next: (data) => {
        this.area = data.result;

        this.areaForm.patchValue({
          nombre: this.area.nombre
        });
      },
      error: (err) => {
        if (err.status === 404) {
            this._router.navigate(['/no-encontrado']);
          } else {
            this._router.navigate(['/admin/areas']).then(() => {
              Swal.fire({
                icon: 'error',
                title: 'ERROR',
                text: 'Error al cargar el área o no estas autorizado/a',
                showConfirmButton: true,
              });
            });
          }
      },
    });
  }

  get nombre() {
    return this.areaForm.get('nombre');
  }
}
