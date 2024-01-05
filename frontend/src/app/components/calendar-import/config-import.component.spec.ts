import { ComponentFixture, TestBed } from '@angular/core/testing';
import {ConfigImportComponent} from "./config-import.component";


describe('CalendarImportComponent', () => {
  let component: ConfigImportComponent;
  let fixture: ComponentFixture<ConfigImportComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConfigImportComponent]
    });
    fixture = TestBed.createComponent(ConfigImportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
