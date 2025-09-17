import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewsDeletePageComponent } from './news.delete.page.component';

describe('NewsDeletePageComponent', () => {
  let component: NewsDeletePageComponent;
  let fixture: ComponentFixture<NewsDeletePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewsDeletePageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewsDeletePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
