import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransparencyCreatePageComponent } from './transparency-create.page.component';

describe('TransparencyCreatePageComponent', () => {
  let component: TransparencyCreatePageComponent;
  let fixture: ComponentFixture<TransparencyCreatePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransparencyCreatePageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransparencyCreatePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
