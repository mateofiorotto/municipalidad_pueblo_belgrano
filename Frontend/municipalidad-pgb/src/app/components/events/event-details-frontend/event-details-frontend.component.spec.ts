import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventDetailsFrontendComponent } from './event-details-frontend.component';

describe('EventDetailsFrontendComponent', () => {
  let component: EventDetailsFrontendComponent;
  let fixture: ComponentFixture<EventDetailsFrontendComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EventDetailsFrontendComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EventDetailsFrontendComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
