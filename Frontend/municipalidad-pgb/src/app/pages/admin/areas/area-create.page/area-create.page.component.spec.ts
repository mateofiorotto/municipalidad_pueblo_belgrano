import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AreaCreatePageComponent } from './area-create.page.component';

describe('AreaCreatePageComponent', () => {
  let component: AreaCreatePageComponent;
  let fixture: ComponentFixture<AreaCreatePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AreaCreatePageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AreaCreatePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
