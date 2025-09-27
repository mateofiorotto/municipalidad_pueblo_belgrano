import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransparencyCreateComponent } from './transparency-create.component';

describe('TransparencyCreateComponent', () => {
  let component: TransparencyCreateComponent;
  let fixture: ComponentFixture<TransparencyCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransparencyCreateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransparencyCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
