import { Component } from '@angular/core';
import { inject } from '@angular/core';
import { ComplaintService } from '../../../services/complaints/complaints.service';
import { ComplaintResponseDTO, ComplaintListResponse } from '../../../models/complaint.model';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { AreaResponseDTO } from '../../../models/area.model';
import { AreasService } from '../../../services/areas/areas.service';

@Component({
  standalone: true,
  selector: 'app-complaints-table',
  imports: [MatPaginatorModule, MatIconModule],
  templateUrl: './complaints-table.component.html',
  styleUrl: './complaints-table.component.css',
})
export class ComplaintsTableComponent {
  private _complaintService = inject(ComplaintService);
  private _areasService = inject(AreasService);

  public error: any;
  public pageIndex: number = 0;
  public totalElements = 0;
  public complaintsList: ComplaintResponseDTO[] = [];
  public areasList: AreaResponseDTO[] = [];
  public selectedAreaId: number = 0;

  private setComplaintsData(data: ComplaintListResponse): void {
    this.complaintsList = data.result.content;
    this.pageIndex = data.result.page.number;
    this.totalElements = data.result.page.totalElements;
    this.error = null;
  }

  ngOnInit(): void {
    this.loadAreas();
    this.loadComplaints(this.pageIndex);
  }

  loadComplaints(page: number): void {
    this._complaintService.getComplaintsList(page).subscribe({
      next: (data) => { this.setComplaintsData(data) },
      error: (err) => {
        this.error = 'Error al cargar reclamos o no estas autorizado/a';
      },
    });
  }

  loadAreas(): void {
    this._areasService.getAreasList().subscribe({
      next: (data) => {
        this.areasList = data.result;
      },
      error: (err) => console.error('Error al cargar areas', err),
    });
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;

    if (this.selectedAreaId && this.selectedAreaId !== 0) {

    this._complaintService.getComplaintsListByArea(this.pageIndex, this.selectedAreaId).subscribe({
      next: (data) => {this.setComplaintsData(data)},
      error: (err) => {
        this.error = 'Error al cargar reclamos o no estás autorizado/a';
      },
    });
  } else {
    
    this.loadComplaints(this.pageIndex);
  }
  }

  onAreaChange(selectedValue: number): void {
    this.selectedAreaId = selectedValue;
    this.pageIndex = 0;
    
    if (this.selectedAreaId == 0) {
      this.loadComplaints(this.pageIndex);
      return;
    }

    this._complaintService.getComplaintsListByArea(this.pageIndex, this.selectedAreaId).subscribe({
      next: (data) => {this.setComplaintsData(data)},
      error: (err) => {
        this.error = 'Error al cargar reclamos o no estas autorizado/a';
      },
    });
  }
}
