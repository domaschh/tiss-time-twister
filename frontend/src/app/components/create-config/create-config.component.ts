import { Component } from '@angular/core';
import { FormGroup, UntypedFormBuilder, Validators } from "@angular/forms";
import { CalendarReferenceService } from "../../services/calendar.reference.service";
import { Router } from "@angular/router";
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

  selectedCal: Calendar = {
    id: 1,
    isActive: false,
    name: '',
    color: "ababa",
    link: '',
    token:''
  };

  currentRule: RuleDto = { id: 0, match: { summary: "Test", summaryMatchType: MatchType.CONTAINS}, effect: { effectType: EffectType.MODIFY } };
  lastSelectedRule: RuleDto;

  demoRules: RuleDto[] = [
    { id: 1, match: { summary: "Rule 1", summaryMatchType: MatchType.CONTAINS }, effect: { effectType: EffectType.MODIFY } },
    { id: 2, match: { summary: "Rule 2", summaryMatchType: MatchType.REGEX }, effect: { effectType: EffectType.MODIFY } },
    { id: 3, match: { summary: "Rule 3", summaryMatchType: MatchType.STARTS_WITH }, effect: { effectType: EffectType.DELETE } }
  ];

  configurationForm: FormGroup = this.formBuilder.group({
    name: ['', [Validators.required]],
    description: ['', [Validators.required]],
    public: [false, [Validators.required]],
    calendar: [Calendar, [Validators.required]],
    rules: [this.demoRules, [Validators.required]]
  });
  submitted = false;
  // Error flag
  error = false;
  errorMessage = '';

  calendars: Calendar[] = [
    { name: "TISS", id: 999, color: "abab", isActive: true, link:'', token:''},
    { name: "TUWEL", id: 998, color: "abab", isActive: true, link:'', token:''}
  ];

  constructor(private formBuilder: UntypedFormBuilder, private calendarReferenceService: CalendarReferenceService, private router: Router, private readonly toastrService: ToastrService) {

    this.configurationForm.patchValue({ calendar: null });
  }


  ngOnInit(): void {
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
    console.log(this.configurationForm.value);
  }

  setActiveNewRule(){
    let newRule: RuleDto = {
      id: 999,
      match: {
        summary:''
      },
      effect: {
        effectType: EffectType.MODIFY
      }
    }
    this.setActiveRule(newRule);
  }
  setActiveRule(rule: RuleDto) {
    //set current rule for editing
    //TODO morgen config creation page fertig machen
    //und dann preview page anfangen
    console.log(rule);
    this.currentRule = {id: rule.id, match: {...rule.match}, effect: {...rule.effect}};
    this.lastSelectedRule = rule;
  }
  addRule() {
    this.demoRules = this.demoRules.filter(rule => rule != this.lastSelectedRule);
    this.demoRules.push(this.currentRule);
    this.setActiveNewRule();
  }
  removeRule() {
    this.demoRules = this.demoRules.filter(rule => rule != this.lastSelectedRule);
    this.setActiveNewRule();
  }

  public getEnumValues(enumObject: any): string[] {
    return Object.values(enumObject) as string[];
  }
}
