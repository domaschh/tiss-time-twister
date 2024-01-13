import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RuleFoldInputComponent } from './rule-fold-input.component';

describe('RuleFoldInputComponent', () => {
  let component: RuleFoldInputComponent;
  let fixture: ComponentFixture<RuleFoldInputComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RuleFoldInputComponent]
    });
    fixture = TestBed.createComponent(RuleFoldInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
