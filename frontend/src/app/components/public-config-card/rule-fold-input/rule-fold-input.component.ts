import {Component, EventEmitter, Input, Output} from '@angular/core';
import {EffectType, MatchType, RuleDto} from "../../../dtos/configuration-dto";
import {CalendarView} from "angular-calendar";

@Component({
  selector: 'app-rule-fold-input',
  templateUrl: './rule-fold-input.component.html',
  styleUrls: ['./rule-fold-input.component.scss']
})
export class RuleFoldInputComponent {
  @Input() rule: RuleDto
  @Output() deleteRuleEvent: EventEmitter<RuleDto> = new EventEmitter<RuleDto>();

  infoOpen = false
  openRuleInfo() {
    this.infoOpen = !this.infoOpen;
  }

  isDeleteEffect() {
    return this.rule.effect.effectType === EffectType.DELETE;
  }

  protected readonly CalendarView = CalendarView;
  protected readonly MatchType = MatchType;
  protected readonly EffectType = EffectType;

  deleteRule() {
    this.deleteRuleEvent.emit(this.rule)
  }
}
