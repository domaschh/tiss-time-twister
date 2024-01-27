import { Component } from '@angular/core';

@Component({
  selector: 'app-help-page',
  templateUrl: './help-page.component.html',
  styleUrls: ['./help-page.component.scss']
})
export class HelpPageComponent {
  isDarkMode(): boolean {
    return document.body.classList.contains('dark');
  }
}
