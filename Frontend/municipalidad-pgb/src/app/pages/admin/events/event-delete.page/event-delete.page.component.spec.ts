import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventDeletePageComponent } from './event-delete.page.component';

describe('EventDeletePageComponent', () => {
  let component: EventDeletePageComponent;
  let fixture: ComponentFixture<EventDeletePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EventDeletePageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EventDeletePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
