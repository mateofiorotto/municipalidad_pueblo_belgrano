import { Component } from '@angular/core';
import { ComplaintCreateComponent } from '../../components/complaints/complaint-create/complaint-create.component';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-complaints.page',
  imports: [ComplaintCreateComponent],
  templateUrl: './complaints.page.component.html',
  styleUrl: './complaints.page.component.css',
})
export class ComplaintsPageComponent {
  scrollToComplaintsInfo() {
    const element = document.getElementById('info-reclamos');
    if (element) {
      const headerOffset = 100;
      const elementPosition =
        element.getBoundingClientRect().top + window.pageYOffset;
      const offsetPosition = elementPosition - headerOffset;

      window.scrollTo({
        top: offsetPosition,
        behavior: 'smooth',
      });
    }
  }
}
