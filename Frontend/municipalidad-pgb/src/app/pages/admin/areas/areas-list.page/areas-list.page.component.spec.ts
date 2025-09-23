import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AreasListPageComponent } from './areas-list.page.component';

describe('AreasListPageComponent', () => {
  let component: AreasListPageComponent;
  let fixture: ComponentFixture<AreasListPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AreasListPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AreasListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
