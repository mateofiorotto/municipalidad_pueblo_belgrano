import { Component } from '@angular/core';
import { ComplaintService } from '../../../services/complaints/complaints.service';
import { inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ComplaintResponseDTO } from '../../../models/complaint.model';
import Swal from 'sweetalert2';

@Component({
  standalone: true,
  selector: 'app-complaint-delete',
  imports: [],
  templateUrl: './complaint-delete.component.html',
  styleUrl: './complaint-delete.component.css',
})
export class ComplaintDeleteComponent {
  private _complaintsService = inject(ComplaintService);
  private _route = inject(ActivatedRoute);
  private _router = inject(Router);

  public error: boolean = false;
  public complaint!: ComplaintResponseDTO;
  private complaintId!: number;

  ngOnInit(): void {
    const id = this._route.snapshot.paramMap.get('id');

    if (id) {
      this.complaintId = +id;
      this._complaintsService.getComplaintById(this.complaintId).subscribe({
        next: (res) => (this.complaint = res.result),
        error: (err) => {
          if (err.status === 404) {
            this._router.navigate(['/no-encontrado']);
          } else {
            this._router.navigate(['/']).then(() => {
              Swal.fire({
                icon: 'error',
                title: 'ERROR',
                text: 'Error al cargar reclamos o no estas autorizado/a',
                showConfirmButton: true,
              });
            });
          }
        },
      });
    }
  }

  deleteComplaint(): void {
    this._complaintsService.deleteComplaint(this.complaintId).subscribe({
      next: () => {
        this._router.navigate(['/admin/reclamos']);
        Swal.fire({
          icon: 'success',
          title: 'Reclamo Eliminado',
          text: 'Reclamo eliminado correctamente',
          showConfirmButton: true,
        });
      },
      error: (err) => {
        this._router.navigate(['/admin/reclamos']).then(() => {
          Swal.fire({
            icon: 'error',
            title: 'ERROR',
            text: 'Error al eliminar reclamo',
            showConfirmButton: true,
          });
        });
      },
    });
  }

  cancel(): void {
    this._router.navigate(['/admin/reclamos']);
  }
}
