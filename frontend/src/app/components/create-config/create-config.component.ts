import {Component} from '@angular/core';
import {FormGroup, UntypedFormBuilder, Validators} from "@angular/forms";
import {CalendarReferenceService} from "../../services/calendar.reference.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {ConfigurationDto, EffectType, MatchType, RuleDto} from 'src/app/dtos/configuration-dto';
import {CalendarReferenceDto} from "../../dtos/calendar-reference-dto";

@Component({
  selector: 'app-create-config',
  templateUrl: './create-config.component.html',
  styleUrls: ['./create-config.component.scss']
})
export class CreateConfigComponent {
  calendars: CalendarReferenceDto[] = [];

  MatchType = MatchType;
  EffectType = EffectType;

  addRuleLabel: String = 'Add';
  ruleAddMode: boolean = true; //true -> add new Rule/ false -> replace existing rule

  currentRule: RuleDto;

  allRules: RuleDto[] = []

  configurationForm: FormGroup = this.formBuilder.group({
    name: ['', [Validators.required]],
    description: ['', [Validators.required]],
    public: [false, [Validators.required]],
    calendar: [CalendarReferenceDto, [Validators.required]],
  });

  submitted = false;
  // Error flag
  error = false;
  errorMessage = '';
  prefilled: {calId: number, config: ConfigurationDto};


  constructor(private route: ActivatedRoute, private formBuilder: UntypedFormBuilder, private calendarReferenceService: CalendarReferenceService, private router: Router, private readonly toastrService: ToastrService) {
    this.configurationForm.patchValue({ calendar: null });
    this.prefilled = this.router.getCurrentNavigation().extras.state as {calId: number, config: ConfigurationDto};
    if (this.prefilled) {
      this.configurationForm.controls.name.setValue(this.prefilled.config.title)
      this.configurationForm.controls.description.setValue(this.prefilled.config.description)
      this.configurationForm.controls.public.setValue(this.prefilled.config.published)
      this.allRules = this.prefilled.config.rules
    }
  }


  ngOnInit(): void {
    this.loadCalendars()
    this.setActiveNewRule();
  }

  loadCalendars() {
    this.calendarReferenceService.getAll().subscribe({
      next: (calendars) => {
        this.calendars = calendars;
        const calendar = calendars.find(cal => cal.id == this.prefilled.calId);
        this.configurationForm.controls.calendar.setValue(calendar.id)
      },
      error: () => {
      }
    })
  }

  private defaultServiceErrorHandling(error: any) {
    console.log(error);
    this.error = true;
    if (typeof error.error === 'object') {
      this.errorMessage = error.error.error;
    } else {
      this.errorMessage = error.error;
    }
  }

  vanishError() {
    this.error = false;
  }

  previewConfiguration() {
    this.openPreview({
      description: this.configurationForm.value.description,
      published: this.configurationForm.value.public,
      rules: this.allRules,
      title: this.configurationForm.value.name
    });
  }

  setActiveNewRule() {
    this.currentRule = {
      match: {
        summary: undefined,
        summaryMatchType: MatchType.CONTAINS,
        description: undefined,
        descriptionMatchType: MatchType.CONTAINS,
        locationMatchType: MatchType.CONTAINS,
        location: undefined,
      },
      effect: {
        changedDescription: undefined,
        changedTitle: undefined,
        location: undefined,
        effectType: EffectType.MODIFY
      }
    };
  }

  addRule() {
    if (this.ruleAddMode) {
      this.allRules.push(this.currentRule);
    }
    this.setActiveNewRule();
  }

  public getEnumValues(enumObject: any): string[] {
    return Object.values(enumObject) as string[];
  }

  openPreview(config: ConfigurationDto) {
    this.router.navigate(['previewConfig'], {
      state: { calId: this.configurationForm.controls.calendar.value, config: config }
    });
  }

  isDeleteEffect() {
    return this.currentRule.effect.effectType === EffectType.DELETE;
  }

  deleteRule($event: RuleDto) {
    this.allRules = this.allRules.filter(ruleToFind => ruleToFind.id !== $event.id)
  }
}
