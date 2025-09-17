import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewsEditPageComponent } from './news.edit.page.component';

describe('NewsEditPageComponent', () => {
  let component: NewsEditPageComponent;
  let fixture: ComponentFixture<NewsEditPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewsEditPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewsEditPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
