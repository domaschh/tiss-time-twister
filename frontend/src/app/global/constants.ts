import {EventColor} from "calendar-utils";

export const colors: Record<number, EventColor> = {
  1: {
    primary: '#ff1f1f',
    secondary: '#FAE3E3',
  },
  3: {
    primary: '#03ad2b',
    secondary: '#32e65c',
  },
  0: {
    primary: '#e3bc08',
    secondary: '#FDF1BA',
  },
  2: {
    primary: '#08c9e3',
    secondary: '#00d3ff',
  },
};

export interface Shorthand {
  from: string,
  to: string
}

export const shorthands: Shorthand[] = []
