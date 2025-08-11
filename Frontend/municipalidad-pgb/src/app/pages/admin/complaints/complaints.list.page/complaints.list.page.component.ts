import { Component } from '@angular/core';
import { ComplaintsTableComponent } from '../../../../components/complaints/complaints-table/complaints-table.component';

@Component({
  selector: 'app-complaints.list.page',
  imports: [ComplaintsTableComponent],
  templateUrl: './complaints.list.page.component.html',
  styleUrl: './complaints.list.page.component.css'
})
export class ComplaintsListPageComponent {

}
