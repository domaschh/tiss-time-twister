import {AfterViewInit, ChangeDetectorRef, Component, effect, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {EffectType, MatchType, RuleDto} from "../../../dtos/configuration-dto";
import {CalendarView} from "angular-calendar";
import {TagDto} from "../../../dtos/tag-dto";
import {TagService} from "../../../services/tag.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-rule-fold-input',
  templateUrl: './rule-fold-input.component.html',
  styleUrls: ['./rule-fold-input.component.scss']
})
export class RuleFoldInputComponent implements OnInit{
  @Input() rule: RuleDto
  @Output() deleteRuleEvent: EventEmitter<RuleDto> = new EventEmitter<RuleDto>();

  @Input() tags: TagDto[];
  selectedTag: TagDto = null;
  newTag: string;


  infoOpen = false;

  constructor(private tagService: TagService,
              private readonly toastrService: ToastrService, private cdref: ChangeDetectorRef) {
  }
  sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }

  ngOnInit(): void {
    this.sleep(300).then(() => {
      this.selectedTag = this.tags.find(t => t.tag === this.rule.effect.tag);
    });
  }

  openRuleInfo() {
    this.infoOpen = !this.infoOpen;
  }

  isDeleteEffect() {
    return this.rule.effect.effectType === EffectType.DELETE;
  }

  isModifyEffect() {
    return this.rule.effect.effectType === EffectType.MODIFY;
  }

  isTagEffect() {
    return this.rule.effect.effectType === EffectType.TAG;
  }

  protected readonly CalendarView = CalendarView;
  protected readonly MatchType = MatchType;
  protected readonly EffectType = EffectType;

  deleteRule() {
    this.deleteRuleEvent.emit(this.rule)
  }

  tagClicked(tag: TagDto) {
    if (this.selectedTag === tag) {
      this.selectedTag = null;
      this.rule.effect.tag = null;
    } else {
      this.selectedTag = tag;
      this.rule.effect.tag = tag.tag;
    }
  }
}
