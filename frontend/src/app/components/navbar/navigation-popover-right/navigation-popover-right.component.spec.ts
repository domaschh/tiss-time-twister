import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavigationPopoverRightComponent } from './navigation-popover-right.component';

describe('NavigationPopoverRightComponent', () => {
  let component: NavigationPopoverRightComponent;
  let fixture: ComponentFixture<NavigationPopoverRightComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NavigationPopoverRightComponent]
    });
    fixture = TestBed.createComponent(NavigationPopoverRightComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
