import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransparencyDeletePageComponent } from './transparency-delete.page.component';

describe('TransparencyDeletePageComponent', () => {
  let component: TransparencyDeletePageComponent;
  let fixture: ComponentFixture<TransparencyDeletePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransparencyDeletePageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransparencyDeletePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
