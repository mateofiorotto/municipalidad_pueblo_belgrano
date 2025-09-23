import { Component } from '@angular/core';
import { inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { AreasService } from '../../../services/areas/areas.service';
import { AreaResponseDTO } from '../../../models/area.model';

@Component({
  selector: 'app-area-delete',
  imports: [],
  templateUrl: './area-delete.component.html',
  styleUrl: './area-delete.component.css',
})
export class AreaDeleteComponent {
  private _route = inject(ActivatedRoute);
  private _router = inject(Router);
  private _areaService = inject(AreasService);
  private areaId!: number;

  public area!: AreaResponseDTO;

  ngOnInit(): void {
    const id = this._route.snapshot.paramMap.get('id');

    if (id) {
      this._areaService.getAreaById(+id).subscribe({
        next: (data) => {
          this.area = data.result;
        },
        error: (err) => {
          if (err.status === 404) {
            this._router.navigate(['/no-encontrado']);
          } else {
            this._router.navigate(['/']).then(() => {
              Swal.fire({
                icon: 'error',
                title: 'ERROR',
                text: 'Error al cargar área o no estas autorizado/a',
                showConfirmButton: true,
              });
            });
          }
        },
      });
    }
  }

  deleteArea(): void {
    this.areaId = this.area.id;

    this._areaService.deleteArea(this.areaId).subscribe({
      next: () => {
        this._router.navigate(['/admin/areas']);
        Swal.fire({
          icon: 'success',
          title: 'Área Eliminada',
          text: 'Área eliminada correctamente',
          showConfirmButton: true,
        });
      },
      error: (err) => {
        if (err.status === 409) {
          this._router.navigate(['/admin/areas']).then(() => {
            Swal.fire({
              icon: 'error',
              title: 'ERROR',
              text: 'El área tiene reclamos asociados actualmente o fue asociado a un reclamo viejo.',
              showConfirmButton: true,
            });
          });
        } else {
          this._router.navigate(['/admin/areas']).then(() => {
            Swal.fire({
              icon: 'error',
              title: 'ERROR',
              text: 'Error al eliminar el área',
              showConfirmButton: true,
            });
          });
        }
      },
    });
  }

  cancel(): void {
    this._router.navigate(['/admin/noticias']);
  }
}
