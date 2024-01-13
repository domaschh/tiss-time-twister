import {Component, Input} from '@angular/core';
import {EffectType, RuleDto} from "../../../dtos/configuration-dto";

@Component({
  selector: 'app-rule-fold',
  templateUrl: './rule-fold.component.html',
  styleUrls: ['./rule-fold.component.scss']
})
export class RuleFoldComponent {
  @Input() rule: RuleDto
  infoOpen = false
  openRuleInfo() {
    this.infoOpen = !this.infoOpen;
  }

  isDeleteEffect() {
    return this.rule.effect.effectType === EffectType.DELETE;
  }

  isTagEffect() {
    return this.rule.effect.effectType === EffectType.TAG;
  }

  protected readonly EffectType = EffectType;
}
