import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewsListByCategoryComponent } from './news-list-by-category.component';

describe('NewsListByCategoryComponent', () => {
  let component: NewsListByCategoryComponent;
  let fixture: ComponentFixture<NewsListByCategoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewsListByCategoryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewsListByCategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
