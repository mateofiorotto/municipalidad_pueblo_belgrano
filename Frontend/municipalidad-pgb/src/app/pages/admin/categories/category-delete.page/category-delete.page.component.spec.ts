import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryDeletePageComponent } from './category-delete.page.component';

describe('CategoryDeletePageComponent', () => {
  let component: CategoryDeletePageComponent;
  let fixture: ComponentFixture<CategoryDeletePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CategoryDeletePageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoryDeletePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
