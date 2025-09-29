import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { inject } from '@angular/core';
import { TransparenciesService } from '../../../services/transparencies/transparencies.service';
import { TransparencyResponseDTO } from '../../../models/transparency.model';
import { DatePipe, TitleCasePipe } from '@angular/common';

@Component({
  selector: 'app-transparency-details',
  imports: [TitleCasePipe, DatePipe],
  templateUrl: './transparency-details.component.html',
  styleUrl: './transparency-details.component.css',
})
export class TransparencyDetailsComponent {
  private _route = inject(ActivatedRoute);
  private _router = inject(Router);
  private _transparenciesService = inject(TransparenciesService);

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
}
