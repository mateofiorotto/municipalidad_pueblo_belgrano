import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransparencyDeleteComponent } from './transparency-delete.component';

describe('TransparencyDeleteComponent', () => {
  let component: TransparencyDeleteComponent;
  let fixture: ComponentFixture<TransparencyDeleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransparencyDeleteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransparencyDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
