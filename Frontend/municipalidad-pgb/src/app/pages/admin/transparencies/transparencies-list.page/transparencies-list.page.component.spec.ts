import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransparenciesListPageComponent } from './transparencies-list.page.component';

describe('TransparenciesListPageComponent', () => {
  let component: TransparenciesListPageComponent;
  let fixture: ComponentFixture<TransparenciesListPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransparenciesListPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransparenciesListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
