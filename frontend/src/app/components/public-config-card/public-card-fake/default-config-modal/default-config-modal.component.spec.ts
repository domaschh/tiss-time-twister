import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DefaultConfigModalComponent } from './default-config-modal.component';

describe('DefaultConfigModalComponent', () => {
  let component: DefaultConfigModalComponent;
  let fixture: ComponentFixture<DefaultConfigModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DefaultConfigModalComponent]
    });
    fixture = TestBed.createComponent(DefaultConfigModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
