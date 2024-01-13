export interface ConfigurationDto {
  id?: number,
  title: string,
  calendarReferenceId?: number,
  clonedFromId?: number,
  description: string,
  rules: RuleDto[],
  published: boolean,
  alreadyAdded?: boolean,
}

export interface PublicConfigurationDto extends ConfigurationDto {
  initialConfigurationId: number;
  alreadyCloned?: boolean;
  mine: boolean;
}

export enum MatchType {
  CONTAINS = 'CONTAINS',
  STARTS_WITH = 'STARTS_WITH',
  EQUALS = 'EQUALS',
  REGEX = 'REGEX'
}
export interface MatchDto {
  id?: number,
  summary?: string,
  description?: string,
  location?: string,
  summaryMatchType?: MatchType,
  descriptionMatchType?: MatchType,
  locationMatchType?: MatchType,
}

export enum EffectType {
  DELETE = 'DELETE',
  MODIFY = 'MODIFY',
  TAG = 'TAG'
}

export interface EffectDto {
  id?: number,
  changedTitle?: string,
  changedDescription?: string,
  location?: string,
  tag?: string,
  effectType?: EffectType
}

export interface RuleDto {
  id?: number,
  configId?: number,
  effect?: EffectDto,
  match?: MatchDto
}
