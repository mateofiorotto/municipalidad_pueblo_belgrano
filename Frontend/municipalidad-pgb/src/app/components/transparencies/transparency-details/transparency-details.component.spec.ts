import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransparencyDetailsComponent } from './transparency-details.component';

describe('TransparencyDetailsComponent', () => {
  let component: TransparencyDetailsComponent;
  let fixture: ComponentFixture<TransparencyDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransparencyDetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransparencyDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
