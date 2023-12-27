import {Component, OnInit} from '@angular/core';
import {ConfigurationService} from "../../services/configuration.service";

@Component({
  selector: 'app-public-page',
  templateUrl: './public-page.component.html',
  styleUrls: ['./public-page.component.scss']
})
export class PublicPageComponent implements OnInit {
  a = [...Array(12).keys()]


  constructor(private readonly configurationService: ConfigurationService) {
    this.loadPublicConfigs();
  }

  ngOnInit() {

  }

  loadPublicConfigs() {
    this.configurationService.getAllPublic();
  }
}
