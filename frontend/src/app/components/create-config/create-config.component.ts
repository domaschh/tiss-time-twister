import {Component} from '@angular/core';
import {FormGroup, UntypedFormBuilder, Validators} from "@angular/forms";
import {CalendarReferenceService} from "../../services/calendar.reference.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {ConfigurationDto, EffectType, MatchType, RuleDto} from 'src/app/dtos/configuration-dto';
import {CalendarReferenceDto} from "../../dtos/calendar-reference-dto";
import {TagDto} from "../../dtos/tag-dto";
import {TagService} from "../../services/tag.service";
import {ru} from "date-fns/locale";

@Component({
  selector: 'app-create-config',
  templateUrl: './create-config.component.html',
  styleUrls: ['./create-config.component.scss']
})
export class CreateConfigComponent {
  calendars: CalendarReferenceDto[] = [];
  tags: TagDto[] = [];
  selectedTag: TagDto = null;
  newTag: string;


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

  optionalConfigId: number;

  submitted = false;
  // Error flag
  error = false;
  errorMessage = '';
  prefilled: { calId: number, config: ConfigurationDto };


  constructor(
    private tagService: TagService,
    private route: ActivatedRoute, private formBuilder: UntypedFormBuilder, private calendarReferenceService: CalendarReferenceService, private router: Router, private readonly toastrService: ToastrService) {
    this.configurationForm.patchValue({calendar: null});
    this.prefilled = this.router.getCurrentNavigation().extras.state as { calId: number, config: ConfigurationDto };
    if (this.prefilled) {
      this.optionalConfigId = this.prefilled.config.id;
      this.configurationForm.controls.name.setValue(this.prefilled.config.title)
      this.configurationForm.controls.description.setValue(this.prefilled.config.description)
      this.configurationForm.controls.public.setValue(this.prefilled.config.published)
      this.allRules = this.prefilled.config.rules
    }
  }


  ngOnInit(): void {
    this.loadTags();
    this.loadCalendars();
    this.setActiveNewRule();
  }

  loadTags() {
    this.tagService.getAll().subscribe({
      next: tags => {
        this.tags = tags;
      },
      error: () => {}
    });
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
    if(rule.effect.effectType === EffectType.TAG && rule.effect.tag == null) {
      this.toastrService.error("You have a Tag rule with no tag selected");
      return;
    }
    if (
      (rule.match.description == null || rule.match.description == '') &&
      (rule.match.summary == null || rule.match.summary == '') &&
      (rule.match.location == null || rule.match.location == '') &&
        (rule.effect.changedDescription == null ||rule.effect.changedDescription == "") &&
          (rule.effect.changedTitle == null || rule.effect.changedTitle == "") &&
            (rule.effect.location == null || rule.effect.location == "")
    ) {
      return false;
    } else {
      return true;
    }
  }

  previewConfiguration() {
    if (this.ruleHasValues(this.currentRule)) {
      this.allRules.push(this.currentRule);
    }
    let config: ConfigurationDto = {
      description: this.configurationForm.value.description,
      published: this.configurationForm.value.public,
      rules: this.allRules,
      title: this.configurationForm.value.name
    };

    if (this.optionalConfigId) {
      config = {...config, id: this.optionalConfigId}
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
      if (this.ruleHasValues(this.currentRule)) {
        this.allRules.push(this.currentRule);
      }
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

  tagClicked(tag: TagDto) {
    if (this.selectedTag === tag) {
      this.selectedTag = null;
      this.currentRule.effect.tag = null;
    } else {
      this.selectedTag = tag;
      this.currentRule.effect.tag = tag.tag;
    }
  }

  isDeleteEffect() {
    return this.currentRule.effect.effectType === EffectType.DELETE;
  }

  isModifyEffect() {
    return this.currentRule.effect.effectType === EffectType.MODIFY;
  }

  isTagEffect() {
    return this.currentRule.effect.effectType === EffectType.TAG;
  }

  deleteRule($event: RuleDto) {
    this.allRules = this.allRules.filter(ruleToFind => ruleToFind.id !== $event.id)
  }

  addTag() {
    if (!this.newTag || this.newTag == '') {
      this.toastrService.error("Please enter a tag!");
    } else if(this.tags.filter(t => t.tag == this.newTag).length !== 0) {
      this.toastrService.error("Tag already exists!");
    } else {
      let newTag = new TagDto();
      newTag.tag = this.newTag;
      this.tagService.createTag(newTag).subscribe({
        next: () => {
          this.toastrService.success("Tag created successfully!");
          this.newTag = '';
          this.loadTags();
        },
        error: e => {
          this.toastrService.error("Error creating tag!");
          console.error("Error creating tag: ", e);
        }
      });
    }
  }

  currentRuleValid() {
    return this.ruleHasValues(this.currentRule)
  }

  configValid() {
    if (this.allRules.length > 0) {
      return true
    } else {
      return this.currentRuleValid();
    }
  }
}
