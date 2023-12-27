import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateConfigComponent } from './create-config.component';

describe('CreateConfigComponent', () => {
  let component: CreateConfigComponent;
  let fixture: ComponentFixture<CreateConfigComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateConfigComponent]
    });
    fixture = TestBed.createComponent(CreateConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
