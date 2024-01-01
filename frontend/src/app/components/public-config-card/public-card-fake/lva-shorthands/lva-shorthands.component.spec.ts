import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LvaShorthandsComponent } from './lva-shorthands.component';

describe('LvaShorthandsComponent', () => {
  let component: LvaShorthandsComponent;
  let fixture: ComponentFixture<LvaShorthandsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LvaShorthandsComponent]
    });
    fixture = TestBed.createComponent(LvaShorthandsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
