import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComplaintDeleteComponent } from './complaint-delete.component';

describe('ComplaintDeleteComponent', () => {
  let component: ComplaintDeleteComponent;
  let fixture: ComponentFixture<ComplaintDeleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComplaintDeleteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ComplaintDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
