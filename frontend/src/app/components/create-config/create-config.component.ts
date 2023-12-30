import { Component } from '@angular/core';
import { FormGroup, UntypedFormBuilder, Validators } from "@angular/forms";
import { CalendarReferenceService } from "../../services/calendar.reference.service";
import { ActivatedRoute, Router } from "@angular/router";
import { ToastrService } from "ngx-toastr";
import { Calendar } from 'src/app/dtos/Calendar';
import { ConfigurationDto, EffectType, RuleDto, MatchType } from 'src/app/dtos/configuration-dto';
import { Rule } from 'eslint';

@Component({
  selector: 'app-create-config',
  templateUrl: './create-config.component.html',
  styleUrls: ['./create-config.component.scss']
})
export class CreateConfigComponent {

  MatchType = MatchType;
  EffectType = EffectType;

  createEditLabel: String = '';
  currentRuleLabel: String = 'New Rule';


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

  calendars: Calendar[] = [
    { name: "TISS", id: 999, color: "abab", isActive: true, link: '', token: '' },
    { name: "TUWEL", id: 998, color: "abab", isActive: true, link: '', token: '' }
  ];

  constructor(private route: ActivatedRoute, private formBuilder: UntypedFormBuilder, private calendarReferenceService: CalendarReferenceService, private router: Router, private readonly toastrService: ToastrService) {

    this.configurationForm.patchValue({ calendar: null });
    const data = router.getCurrentNavigation().extras.state;
    this.calendars = data?.calendars ?? null;
    if (data?.edit) {
      this.createEditLabel = 'Edit Configuration';
    } else {
      this.createEditLabel = 'Create Configuration';
    }
  }


  ngOnInit(): void {
    this.setActiveNewRule();
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

  createConfiguration() {
    this.openPreview({
      description: this.configurationForm.value.description,
      id: 0,
      published: false,
      rules: this.allRules,
      title: this.configurationForm.value.name
    });
    console.log(this.configurationForm.value);
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
    this.setActiveRule(newRule);
  }
  setActiveRule(rule: RuleDto) {
    if (rule.id === this.allRules.length) {
      this.addRuleLabel = 'Add';
      this.currentRuleLabel = 'New Rule';
    } else {
      this.currentRuleLabel = 'Rule ' + (this.allRules.indexOf(rule) + 1);
      this.addRuleLabel = 'Save';
    }
    this.currentRule = rule;
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
      state: { calId: this.selectedCal, config: config }
    });
  }
}
