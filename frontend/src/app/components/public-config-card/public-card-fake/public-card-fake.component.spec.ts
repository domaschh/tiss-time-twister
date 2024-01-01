import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicCardFakeComponent } from './public-card-fake.component';

describe('PublicCardFakeComponent', () => {
  let component: PublicCardFakeComponent;
  let fixture: ComponentFixture<PublicCardFakeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PublicCardFakeComponent]
    });
    fixture = TestBed.createComponent(PublicCardFakeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
