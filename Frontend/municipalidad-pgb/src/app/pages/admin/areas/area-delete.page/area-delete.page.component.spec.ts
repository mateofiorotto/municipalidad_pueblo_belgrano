import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AreaDeletePageComponent } from './area-delete.page.component';

describe('AreaDeletePageComponent', () => {
  let component: AreaDeletePageComponent;
  let fixture: ComponentFixture<AreaDeletePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AreaDeletePageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AreaDeletePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
