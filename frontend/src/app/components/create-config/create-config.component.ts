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

  optionalId: number;

  submitted = false;
  // Error flag
  error = false;
  errorMessage = '';
  prefilled: { calId: number, config: ConfigurationDto };


  constructor(private route: ActivatedRoute, private formBuilder: UntypedFormBuilder, private calendarReferenceService: CalendarReferenceService, private router: Router, private readonly toastrService: ToastrService) {
    this.configurationForm.patchValue({calendar: null});
    this.prefilled = this.router.getCurrentNavigation().extras.state as { calId: number, config: ConfigurationDto };
    if (this.prefilled) {
      this.optionalId = this.prefilled.config.id;
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

  vanishError() {
    this.error = false;
  }

  private ruleHasValues(rule: RuleDto) {
    if (
      rule.match.description == null &&
      rule.match.summary == null &&
      rule.match.location == null &&
      rule.effect.changedDescription == null &&
      rule.effect.changedTitle == null &&
      rule.effect.location == null
    ) {
      return false;
    } else {
      return true;
    }
  }

  previewConfiguration() {
    if (this.ruleHasValues(this.currentRule)) {
      this.allRules.push({...this.currentRule, id: this.allRules.length + 1});
    }
    let config: ConfigurationDto = {
      description: this.configurationForm.value.description,
      published: this.configurationForm.value.public,
      rules: this.allRules,
      title: this.configurationForm.value.name
    };

    if (this.optionalId) {
      config = {...config, id: this.optionalId}
    }
    this.openPreview(config);
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
      state: {calId: this.configurationForm.controls.calendar.value, config: config}
    });
  }

  isDeleteEffect() {
    return this.currentRule.effect.effectType === EffectType.DELETE;
  }

  deleteRule($event: RuleDto) {
    this.allRules = this.allRules.filter(ruleToFind => ruleToFind.id !== $event.id)
  }
}
