import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogoutSuccessModalComponent } from './logout-success-modal.component';

describe('LogoutSuccessModalComponent', () => {
  let component: LogoutSuccessModalComponent;
  let fixture: ComponentFixture<LogoutSuccessModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LogoutSuccessModalComponent]
    });
    fixture = TestBed.createComponent(LogoutSuccessModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
