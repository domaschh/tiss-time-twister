import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicConfigCardComponent } from './public-config-card.component';

describe('PublicConfigCardComponent', () => {
  let component: PublicConfigCardComponent;
  let fixture: ComponentFixture<PublicConfigCardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PublicConfigCardComponent]
    });
    fixture = TestBed.createComponent(PublicConfigCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
