import {Component} from '@angular/core';
import {FormGroup, UntypedFormBuilder, Validators} from "@angular/forms";
import {CalendarReferenceService} from "../../services/calendar.reference.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {Calendar} from 'src/app/dtos/Calendar';
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

  selectedCal: Calendar = {
    id: 1,
    isActive: false,
    name: '',
    color: "ababa",
    link: '',
    token: ''
  };

  currentRule: RuleDto;

  allRules: RuleDto[] = []

  configurationForm: FormGroup = this.formBuilder.group({
    name: ['', [Validators.required]],
    description: ['', [Validators.required]],
    public: [false, [Validators.required]],
    calendar: [Calendar, [Validators.required]],
  });

  submitted = false;
  // Error flag
  error = false;
  errorMessage = '';
  // defaultRule: RuleDto = {
  //   id: 1,
  //   effect: {
  //     changedDescription: "dasd",
  //     changedTitle: "dasd",
  //     location: "dasdasd",
  //     effectType:EffectType.MODIFY
  //   },
  //   match:{
  //     summary: "newUsmmart",
  //     description: "newUsmmart",
  //     location: "newUsmmart",
  //     summaryMatchType: MatchType.CONTAINS,
  //     locationMatchType: MatchType.CONTAINS,
  //     descriptionMatchType: MatchType.CONTAINS,
  //   }
  // };


  constructor(private route: ActivatedRoute, private formBuilder: UntypedFormBuilder, private calendarReferenceService: CalendarReferenceService, private router: Router, private readonly toastrService: ToastrService) {

    this.configurationForm.patchValue({ calendar: null });
    const data = router.getCurrentNavigation().extras.state;
    this.calendars = data?.calendars ?? null;
  }


  ngOnInit(): void {
    this.loadCalendars()
    this.setActiveNewRule();
  }

  loadCalendars() {
    this.calendarReferenceService.getAll().subscribe({
      next: (calendars) => {
        this.calendars = calendars;
        console.log(calendars)
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
    console.log(this.allRules)
    // this.openPreview({
    //   description: this.configurationForm.value.description,
    //   id: 0,
    //   published: false,
    //   rules: this.allRules,
    //   title: this.configurationForm.value.name
    // });
  }

  setActiveNewRule() {
    let newRule: RuleDto = {
      id: this.allRules.length,
      match: {
        summary: '',
        description: '',
        location: '',
      },
      effect: {
        changedDescription: '',
        changedTitle: '',
        location: '',
        effectType: EffectType.MODIFY
      }
    }
    this.currentRule =newRule;
    // this.setActiveRule(newRule);
  }

  addRule() {
    if (this.ruleAddMode) {
      this.allRules.push(this.currentRule);
    }
    this.setActiveNewRule();
  }
  removeRule() {
    this.allRules = this.allRules.filter(rule => rule != this.currentRule);
    this.setActiveNewRule();
  }

  public getEnumValues(enumObject: any): string[] {
    return Object.values(enumObject) as string[];
  }

  openPreview(config: ConfigurationDto) {
    this.router.navigate(['previewConfig'], {
      state: { calId: this.selectedCal.id, config: config }
    });
  }

  isDeleteEffect() {
    return this.currentRule.effect.effectType === EffectType.DELETE;
  }

  deleteRule($event: RuleDto) {
    this.allRules = this.allRules.filter(ruleToFind => ruleToFind.id !== $event.id)
  }
}
