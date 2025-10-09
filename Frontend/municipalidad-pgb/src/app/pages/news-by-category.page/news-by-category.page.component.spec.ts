import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewsByCategoryPageComponent } from './news-by-category.page.component';

describe('NewsByCategoryPageComponent', () => {
  let component: NewsByCategoryPageComponent;
  let fixture: ComponentFixture<NewsByCategoryPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewsByCategoryPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewsByCategoryPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
