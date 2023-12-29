import {Component, OnInit} from '@angular/core';
import {ConfigurationService} from "../../services/configuration.service";
import {ConfigurationDto} from "../../dtos/configuration-dto";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-public-page',
  templateUrl: './public-page.component.html',
  styleUrls: ['./public-page.component.scss']
})
export class PublicPageComponent implements OnInit {
  a = [...Array(12).keys()]

  publicConfigs: ConfigurationDto[] = []

  constructor(private readonly configurationService: ConfigurationService, private readonly toastrService: ToastrService) {
  }

  ngOnInit() {
    this.loadConfigurations();
  }

  private loadConfigurations() {
    this.configurationService.getAllPublic().subscribe({
      next: (loadedConfigs) => {
        this.publicConfigs = loadedConfigs
      },error:() => {
        this.toastrService.error("Error")
      }
    })
  }
}
