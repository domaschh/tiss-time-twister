import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RuleModalComponent } from './rule-modal.component';

describe('RuleModalComponent', () => {
  let component: RuleModalComponent;
  let fixture: ComponentFixture<RuleModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RuleModalComponent]
    });
    fixture = TestBed.createComponent(RuleModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
