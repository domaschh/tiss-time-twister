import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditCustomEventComponent } from './create-edit-custom-event.component';

describe('CustomEventModalComponent', () => {
  let component: CreateEditCustomEventComponent;
  let fixture: ComponentFixture<CreateEditCustomEventComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateEditCustomEventComponent]
    });
    fixture = TestBed.createComponent(CreateEditCustomEventComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
