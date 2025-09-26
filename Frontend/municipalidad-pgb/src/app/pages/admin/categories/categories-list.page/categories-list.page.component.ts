import { Component } from '@angular/core';
import { CategoriesTableComponent } from '../../../../components/categories/categories-table/categories-table.component';

@Component({
  selector: 'app-categories-list.page',
  imports: [CategoriesTableComponent],
  templateUrl: './categories-list.page.component.html',
  styleUrl: './categories-list.page.component.css'
})
export class CategoriesListPageComponent {

}
