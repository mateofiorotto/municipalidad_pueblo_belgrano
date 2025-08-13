import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComplaintDeletePageComponent } from './complaint-delete.page.component';

describe('ComplaintDeletePageComponent', () => {
  let component: ComplaintDeletePageComponent;
  let fixture: ComponentFixture<ComplaintDeletePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComplaintDeletePageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ComplaintDeletePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
