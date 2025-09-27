import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransparenciesListComponent } from './transparencies-list.component';

describe('TransparenciesListComponent', () => {
  let component: TransparenciesListComponent;
  let fixture: ComponentFixture<TransparenciesListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransparenciesListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransparenciesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
