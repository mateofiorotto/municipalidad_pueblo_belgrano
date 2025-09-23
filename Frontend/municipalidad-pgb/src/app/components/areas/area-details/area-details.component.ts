import { Component } from '@angular/core';
import { AreasService } from '../../../services/areas/areas.service';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { AreaResponseDTO } from '../../../models/area.model';
import { inject } from '@angular/core';

@Component({
  selector: 'app-area-details',
  imports: [],
  templateUrl: './area-details.component.html',
  styleUrl: './area-details.component.css'
})
export class AreaDetailsComponent {
  private _route = inject(ActivatedRoute);
  private _router = inject(Router);
  private _areaService = inject(AreasService);

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
}
