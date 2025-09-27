import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransparencyDetailsPageComponent } from './transparency-details.page.component';

describe('TransparencyDetailsPageComponent', () => {
  let component: TransparencyDetailsPageComponent;
  let fixture: ComponentFixture<TransparencyDetailsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransparencyDetailsPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransparencyDetailsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
