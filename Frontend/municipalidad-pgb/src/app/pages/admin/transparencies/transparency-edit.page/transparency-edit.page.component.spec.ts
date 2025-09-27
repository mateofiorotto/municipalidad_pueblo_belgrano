import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransparencyEditPageComponent } from './transparency-edit.page.component';

describe('TransparencyEditPageComponent', () => {
  let component: TransparencyEditPageComponent;
  let fixture: ComponentFixture<TransparencyEditPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransparencyEditPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransparencyEditPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
