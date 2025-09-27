import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransparencyEditComponent } from './transparency-edit.component';

describe('TransparencyEditComponent', () => {
  let component: TransparencyEditComponent;
  let fixture: ComponentFixture<TransparencyEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransparencyEditComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransparencyEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
