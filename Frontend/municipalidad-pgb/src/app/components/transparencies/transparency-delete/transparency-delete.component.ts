import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { inject } from '@angular/core';
import { TransparenciesService } from '../../../services/transparencies/transparencies.service';
import { TransparencyResponseDTO } from '../../../models/transparency.model';
import { DatePipe, TitleCasePipe } from '@angular/common';

@Component({
  selector: 'app-transparency-delete',
  imports: [TitleCasePipe, DatePipe],
  templateUrl: './transparency-delete.component.html',
  styleUrl: './transparency-delete.component.css',
})
export class TransparencyDeleteComponent {
  private _route = inject(ActivatedRoute);
  private _router = inject(Router);
  private _transparenciesService = inject(TransparenciesService);
  private transparencyId!: number;

  public transparency!: TransparencyResponseDTO;

  ngOnInit(): void {
    const id = this._route.snapshot.paramMap.get('id');

    if (id) {
      this._transparenciesService.getTransparencyById(+id).subscribe({
        next: (data) => {
          this.transparency = data.result;
        },
        error: (err) => {
          if (err.status === 404) {
            this._router.navigate(['/no-encontrado']);
          } else {
            this._router.navigate(['/']).then(() => {
              Swal.fire({
                icon: 'error',
                title: 'ERROR',
                text: 'Error al cargar transparencia',
                showConfirmButton: true,
              });
            });
          }
        },
      });
    }
  }

  deleteTransparency(): void {
    this.transparencyId = this.transparency.id;

    this._transparenciesService.deleteTransparency(this.transparencyId).subscribe({
      next: () => {
        this._router.navigate(['/admin/transparencias']);
        Swal.fire({
          icon: 'success',
          title: 'Transparencia Eliminada',
          text: 'Transparencia eliminada correctamente',
          showConfirmButton: true,
        });
      },
      error: (err) => {
        this._router.navigate(['/admin/transparencias']).then(() => {
          Swal.fire({
            icon: 'error',
            title: 'ERROR',
            text: 'Error al eliminar la transparencia',
            showConfirmButton: true,
          });
        });
      },
    });
  }

  cancel(): void {
    this._router.navigate(['/admin/transparencias']);
  }
}
