import { Component } from '@angular/core';
import {Shorthand, shorthands} from "../../../../global/constants";

@Component({
  selector: 'app-lva-shorthands',
  templateUrl: './lva-shorthands.component.html',
  styleUrls: ['./lva-shorthands.component.scss']
})
export class LvaShorthandsComponent {
  shorthands: Shorthand[] = shorthands;
}
