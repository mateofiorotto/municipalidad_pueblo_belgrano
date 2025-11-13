import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransparenciesOrdinancesPageComponent } from './transparencies-ordinances.page.component';

describe('TransparenciesOrdinancesPageComponent', () => {
  let component: TransparenciesOrdinancesPageComponent;
  let fixture: ComponentFixture<TransparenciesOrdinancesPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransparenciesOrdinancesPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransparenciesOrdinancesPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
