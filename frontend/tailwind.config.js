/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,ts}"],
  darkMode: 'class',
  theme: {
    colors:{
      transparent: 'transparent',
      black: '#000000',
      popover: 'rgb(51,60,65)',
      added: 'rgba(70,173,19,0.4)',
      notadded: 'rgba(225,206,10,0.4)',
      remove: 'rgba(225,206,10,0.4)',
      white: '#ffffff',
      blue: {
        DEFAULT: '#0095ff',
        dark: '#04559c',
        light: '#E8F3FB',
      },
      red:'#EB4463',
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
