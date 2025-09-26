import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryDetailsPageComponent } from './category-details.page.component';

describe('CategoryDetailsPageComponent', () => {
  let component: CategoryDetailsPageComponent;
  let fixture: ComponentFixture<CategoryDetailsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CategoryDetailsPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoryDetailsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
