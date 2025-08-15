import { Component } from '@angular/core';
import { inject } from '@angular/core';
import { ComplaintService } from '../../../services/complaints/complaints.service';
import {
  ComplaintUpdateDTO,
  ComplaintResponseDTO,
} from '../../../models/complaint.model';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup } from '@angular/forms';
import { AreasService } from '../../../services/areas/areas.service';
import { AreaResponseDTO } from '../../../models/area.model';
import Swal from 'sweetalert2';

@Component({
  standalone: true,
  selector: 'app-complaint-edit',
  imports: [ReactiveFormsModule],
  templateUrl: './complaint-edit.component.html',
  styleUrl: './complaint-edit.component.css',
})
export class ComplaintEditComponent {
  private _fb = inject(FormBuilder);
  private _route = inject(ActivatedRoute);
  private _router = inject(Router);
  private _complaintsService = inject(ComplaintService);
  private _areaService = inject(AreasService);

  public areasList: AreaResponseDTO[] = [];
  public complaint!: ComplaintResponseDTO;
  public updateComplaintForm!: FormGroup;
  private complaintId!: number;

  ngOnInit(): void {
    this.updateComplaintForm = this._fb.group({
      cerrado: [false],
      comentario: [''],
      area: this._fb.group({
        id: [null],
      }),
    });

    this.areasList = this.loadAreas();

    const id = this._route.snapshot.paramMap.get('id');
    if (id) {
      this.complaintId = +id;
      this._complaintsService.getComplaintById(this.complaintId).subscribe({
        next: (res) => {
          this.complaint = res.result;

          this.updateComplaintForm.patchValue({
            cerrado: res.result.cerrado,
            comentario: res.result.comentario,
            area: { id: res.result.area?.id ?? '' },
          });
        },
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

  onSubmit(): void {
    if (this.updateComplaintForm.valid) {
      const updateData: ComplaintUpdateDTO = this.updateComplaintForm.value;

      if (!updateData.area.id) {
        delete (updateData as any).area;
      }

      this._complaintsService
        .updateComplaint(updateData, this.complaintId)
        .subscribe({
          next: () => {
            console.log('Actualizado correctamente');
            this._router.navigate(['admin/reclamos']).then(() => {
              Swal.fire({
                icon: 'success',
                title: 'Reclamo Actualizado',
                text: 'Reclamo actualizado correctamente',
                showConfirmButton: true,
              });
            });
          },
          error: (err) => {
            this._router.navigate(['/admin/reclamos']).then(() => {
              Swal.fire({
                icon: 'error',
                title: 'ERROR',
                text: 'Error al actualizar reclamo',
                showConfirmButton: true,
              });
            });
          },
        });
    }
  }

  public loadAreas(): AreaResponseDTO[] {
    this._areaService.getAreasList().subscribe({
      next: (data) => {
        this.areasList = data.result;
      },
      error: (err) => {
        this._router.navigate(['/']).then(() => {
          Swal.fire({
            icon: 'error',
            title: 'ERROR',
            text: 'Error al cargar areas o no estas autorizado/a',
            showConfirmButton: true,
          });
        });
      },
    });
    return this.areasList;
  }
}
