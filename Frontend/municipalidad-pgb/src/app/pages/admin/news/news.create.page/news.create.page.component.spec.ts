import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewsCreatePageComponent } from './news.create.page.component';

describe('NewsCreatePageComponent', () => {
  let component: NewsCreatePageComponent;
  let fixture: ComponentFixture<NewsCreatePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewsCreatePageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewsCreatePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
