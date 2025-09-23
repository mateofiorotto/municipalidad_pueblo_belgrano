import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AreaDeleteComponent } from './area-delete.component';

describe('AreaDeleteComponent', () => {
  let component: AreaDeleteComponent;
  let fixture: ComponentFixture<AreaDeleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AreaDeleteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AreaDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
