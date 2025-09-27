import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransparencyCardComponent } from './transparency-card.component';

describe('TransparencyCardComponent', () => {
  let component: TransparencyCardComponent;
  let fixture: ComponentFixture<TransparencyCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransparencyCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransparencyCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
