import { Component } from '@angular/core';
import { inject } from '@angular/core';
import { ComplaintService } from '../../../services/complaints/complaints.service';
import { ComplaintResponseDTO } from '../../../models/complaint.model';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-complaint-details',
  imports: [],
  templateUrl: './complaint-details.component.html',
  styleUrl: './complaint-details.component.css',
})
export class ComplaintDetailsComponent {
  private _route = inject(ActivatedRoute);
  private _router = inject(Router);
  private _complaintsService = inject(ComplaintService);

  public complaint!: ComplaintResponseDTO;

  ngOnInit(): void {
    const id = this._route.snapshot.paramMap.get('id');

    if (id) {
      this._complaintsService.getComplaintById(+id).subscribe({
        next: (data) => {
          this.complaint = data.result;
        },
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
}
