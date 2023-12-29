import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'SE PR Group Phase';

  getDarkModeClass() {
    // Your logic to determine if dark mode should be enabled
    return true ? 'dark' : '';
  }

}
