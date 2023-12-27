import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-public-config-card',
  templateUrl: './public-config-card.component.html',
  styleUrls: ['./public-config-card.component.scss']
})
export class PublicConfigCardComponent {

  @Input()
  configTitle: string;
  @Input()
  configDescription: string

}
