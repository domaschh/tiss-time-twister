import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoomMappingComponent } from './room-mapping.component';

describe('RoomMappingComponent', () => {
  let component: RoomMappingComponent;
  let fixture: ComponentFixture<RoomMappingComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RoomMappingComponent]
    });
    fixture = TestBed.createComponent(RoomMappingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
