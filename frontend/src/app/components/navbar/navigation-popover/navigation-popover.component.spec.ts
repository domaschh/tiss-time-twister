import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavigationPopoverComponent } from './navigation-popover.component';

describe('NavigationPopoverComponent', () => {
  let component: NavigationPopoverComponent;
  let fixture: ComponentFixture<NavigationPopoverComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NavigationPopoverComponent]
    });
    fixture = TestBed.createComponent(NavigationPopoverComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
