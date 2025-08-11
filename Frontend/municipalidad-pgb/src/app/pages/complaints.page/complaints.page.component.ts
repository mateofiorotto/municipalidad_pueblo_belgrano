import { Component } from '@angular/core';
import { CreateComplaintComponent } from '../../components/complaints/create-complaint/create-complaint.component';

@Component({
  selector: 'app-complaints.page',
  imports: [CreateComplaintComponent],
  templateUrl: './complaints.page.component.html',
  styleUrl: './complaints.page.component.css',
})
export class ComplaintsPageComponent {
  
}
