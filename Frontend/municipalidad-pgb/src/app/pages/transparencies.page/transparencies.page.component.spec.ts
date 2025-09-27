import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransparenciesPageComponent } from './transparencies.page.component';

describe('TransparenciesPageComponent', () => {
  let component: TransparenciesPageComponent;
  let fixture: ComponentFixture<TransparenciesPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransparenciesPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransparenciesPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
