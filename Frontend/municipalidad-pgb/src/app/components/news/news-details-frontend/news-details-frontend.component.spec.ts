import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewsDetailsFrontendComponent } from './news-details-frontend.component';

describe('NewsDetailsFrontendComponent', () => {
  let component: NewsDetailsFrontendComponent;
  let fixture: ComponentFixture<NewsDetailsFrontendComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewsDetailsFrontendComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewsDetailsFrontendComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
