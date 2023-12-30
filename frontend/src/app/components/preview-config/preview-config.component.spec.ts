import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewConfigComponent } from './preview-config.component';

describe('PreviewConfigComponent', () => {
  let component: PreviewConfigComponent;
  let fixture: ComponentFixture<PreviewConfigComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PreviewConfigComponent]
    });
    fixture = TestBed.createComponent(PreviewConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
