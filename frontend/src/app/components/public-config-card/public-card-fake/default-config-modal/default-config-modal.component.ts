import {AfterViewInit, Component, Input, OnInit, TemplateRef, ViewChild, ViewContainerRef} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-default-config-modal',
  templateUrl: './default-config-modal.component.html',
  styleUrls: ['./default-config-modal.component.scss']
})
export class DefaultConfigModalComponent implements  AfterViewInit{
  title: string;
  alreadyAdded: boolean;
  description: string;
  @Input() bodyToShow: number;

  @ViewChild('customComponent1') customComponent1Template: TemplateRef<any>;
  @ViewChild('customComponent2') customComponent2Template: TemplateRef<any>;
  @ViewChild('customComponent3') customComponent3Template: TemplateRef<any>;

  componentToShow: TemplateRef<any>;

  constructor(private readonly modal: NgbModal) {
  }

  ngAfterViewInit() {
    this.showComponent();
  }


  closeAll() {
    this.modal.dismissAll()
  }

  showComponent() {
    if (this.bodyToShow === 1) {
      this.componentToShow = this.customComponent1Template;
    } else if (this.bodyToShow === 2) {
      this.componentToShow = this.customComponent2Template;
    } else if (this.bodyToShow === 3) {
      this.componentToShow = this.customComponent3Template;
    }
  }

  removeFromCalendar() {

  }

  addToCalendar() {

  }
}
