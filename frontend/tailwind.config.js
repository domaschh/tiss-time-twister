/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,ts}"],
  darkMode: 'class',
  theme: {
    colors:{
      transparent: 'transparent',
      black: '#000000',
      popover: 'rgb(51,60,65)',
      'popover-light': 'rgba(95,103,107,0.55)',
      added: 'rgba(70,173,19,0.4)',
      myinfo: 'rgba(19,173,155,0.4)',
      notadded: 'rgba(252,228,0,0.64)',
      notaddedDeactivated: 'rgba(121,112,16,0.4)',
      remove: 'rgba(225,206,10,0.4)',
      white: '#ffffff',
      'font-col': '#ADBAC7',
      blue: {
        DEFAULT: '#0095ff',
        dark: '#04559c',
        light: '#E8F3FB',
      },
      red:'#EB4463',
      redDeactivated:'rgba(122,46,62,0.51)',
    },
    extend: {
      textColor: {
        'warning': '#EB4463',
      },
    },
  },
  variants: {
    extend: {}
  },
  plugins: [],
}
