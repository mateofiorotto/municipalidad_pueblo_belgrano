import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComplaintsListPageComponent } from './complaints.list.page.component';

describe('ComplaintsListPageComponent', () => {
  let component: ComplaintsListPageComponent;
  let fixture: ComponentFixture<ComplaintsListPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComplaintsListPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ComplaintsListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
