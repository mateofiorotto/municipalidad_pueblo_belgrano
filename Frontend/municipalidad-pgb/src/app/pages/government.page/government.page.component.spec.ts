import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GovernmentPageComponent } from './government.page.component';

describe('GovernmentPageComponent', () => {
  let component: GovernmentPageComponent;
  let fixture: ComponentFixture<GovernmentPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GovernmentPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GovernmentPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
