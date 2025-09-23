import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AreaDetailsPageComponent } from './area-details.page.component';

describe('AreaDetailsPageComponent', () => {
  let component: AreaDetailsPageComponent;
  let fixture: ComponentFixture<AreaDetailsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AreaDetailsPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AreaDetailsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
