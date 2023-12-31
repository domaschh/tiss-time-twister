export interface ConfigurationDto {
  id?: number,
  title: string,
  description: string,
  rules: RuleDto[],
  published: boolean,
  alreadyAdded?: boolean,
}

export enum MatchType {
  CONTAINS = 'Contains',
  STARTS_WITH = 'Starts with',
  EQUALS = 'Equals',
  REGEX = 'Regex'
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
  DELETE = 'Delete',
  MODIFY = 'Modify'
}

export interface EffectDto {
  id?: number,
  changedTitle?: string,
  changedDescription?: string,
  location?: string,
  effectType?: EffectType
}

export interface RuleDto {
  id?: number,
  effect?: EffectDto,
  match?: MatchDto
}
