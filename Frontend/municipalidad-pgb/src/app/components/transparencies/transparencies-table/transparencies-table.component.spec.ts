import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransparenciesTableComponent } from './transparencies-table.component';

describe('TransparenciesTableComponent', () => {
  let component: TransparenciesTableComponent;
  let fixture: ComponentFixture<TransparenciesTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransparenciesTableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransparenciesTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
