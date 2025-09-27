import { Component } from '@angular/core';
import { TransparencyResponseDTO } from '../../../models/transparency.model';
import { Input } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-transparency-card',
  imports: [RouterLink],
  templateUrl: './transparency-card.component.html',
  styleUrl: './transparency-card.component.css'
})
export class TransparencyCardComponent {
  @Input() transparency!: TransparencyResponseDTO;
}
