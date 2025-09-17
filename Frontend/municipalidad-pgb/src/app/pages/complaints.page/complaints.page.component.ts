import { Component } from '@angular/core';
import { ComplaintCreateComponent } from '../../components/complaints/complaint-create/complaint-create.component';


@Component({
  selector: 'app-complaints.page',
  imports: [ComplaintCreateComponent],
  templateUrl: './complaints.page.component.html',
  styleUrl: './complaints.page.component.css',
})
export class ComplaintsPageComponent {
  
}
