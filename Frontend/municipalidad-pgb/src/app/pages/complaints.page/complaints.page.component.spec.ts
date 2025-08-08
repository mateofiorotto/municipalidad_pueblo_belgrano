import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComplaintsPageComponent } from './complaints.page.component';

describe('ComplaintsPageComponent', () => {
  let component: ComplaintsPageComponent;
  let fixture: ComponentFixture<ComplaintsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComplaintsPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ComplaintsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
