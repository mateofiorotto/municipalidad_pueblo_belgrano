import { Component } from '@angular/core';
import { inject } from '@angular/core';
import { ComplaintService } from '../../../services/complaints/complaints.service';
import { ComplaintResponseDTO } from '../../../models/complaint.model';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';

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
            this._router.navigate(['/']).then(() => {
              Swal.fire({
                icon: 'error',
                title: 'ERROR',
                text: 'Error al cargar reclamo o no estas autorizado/a',
                showConfirmButton: true,
              });
            });
          }
        },
      });
    }
  }

  downloadPDF() {}

  seePDF() {
  this._complaintsService.generateComplaintPDF(this.complaint.id)
    .subscribe((pdfBlob: Blob) => {
      const fileURL = URL.createObjectURL(pdfBlob);
      window.open(fileURL, "_blank");
    }, (error: any) => {
      console.error('Error al generar PDF', error);
    });
}
}
