import { Component } from '@angular/core';
import { ComplaintService } from '../../../services/complaints/complaints.service';
import { inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ComplaintResponseDTO } from '../../../models/complaint.model';

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
            console.error('Error al cargar reclamos o no estas autorizado/a');
          }
      },
      });
    }
  }

  deleteComplaint(): void {
    this._complaintsService.deleteComplaint(this.complaintId).subscribe({
      next: () => {
        this._router.navigate(['/admin/reclamos']);
      },
      error: (err) => {
         console.error('Error al cargar reclamos o no estas autorizado/a'); 
      },
    });
  }

  cancel(): void {
    this._router.navigate(['/admin/reclamos']);
  }
}
