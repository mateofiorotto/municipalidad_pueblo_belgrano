import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransparenciesDecreesPageComponent } from './transparencies-decrees.page.component';

describe('TransparenciesDecreesPageComponent', () => {
  let component: TransparenciesDecreesPageComponent;
  let fixture: ComponentFixture<TransparenciesDecreesPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransparenciesDecreesPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransparenciesDecreesPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
