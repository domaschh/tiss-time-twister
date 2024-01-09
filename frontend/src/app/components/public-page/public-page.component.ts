import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ConfigurationService} from "../../services/configuration.service";
import {ConfigurationDto, PublicConfigurationDto} from "../../dtos/configuration-dto";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-public-page',
  templateUrl: './public-page.component.html',
  styleUrls: ['./public-page.component.scss']
})
export class PublicPageComponent implements OnInit {

  publicConfigs: PublicConfigurationDto[] = []

  constructor(private readonly configurationService: ConfigurationService, private readonly toastrService: ToastrService, private cdRef: ChangeDetectorRef) {
  }

  ngOnInit() {
    this.loadConfigurations();
  }

  private loadConfigurations() {
    this.configurationService.getAllPublic().subscribe({
      next: (loadedConfigs) => {
        this.publicConfigs = loadedConfigs
        this.loadMyConfigs();
      },error:() => {
        this.toastrService.error("Error")
      }
    })
  }

  loadMyConfigs() {
    this.configurationService.getAll().subscribe({
      next: (configs) => {
        this.publicConfigs.forEach((pc) => {
          configs.forEach(myConf => {
            if (myConf.clonedFromId === pc.id) {
              pc.alreadyCloned = true;
            }
          })
        });
      },
      error: () => {
        this.toastrService.error("Coudln't fetch configurations")
      }
    })
  }

  removeCard(id: number) {
    this.publicConfigs = this.publicConfigs.filter(conf => conf.id !== id);
  }
}
