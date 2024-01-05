import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RuleFoldComponent } from './rule-fold.component';

describe('RuleFoldComponent', () => {
  let component: RuleFoldComponent;
  let fixture: ComponentFixture<RuleFoldComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RuleFoldComponent]
    });
    fixture = TestBed.createComponent(RuleFoldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
