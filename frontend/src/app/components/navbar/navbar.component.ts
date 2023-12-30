import {Component} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {

  constructor(private readonly router: Router) {
  }

  routeToPage(s: string) {
    this.router.navigate([s])
  }

  showNavbar() {
    return localStorage.getItem("authToken")
  }

  toggleDarkMode(): void {
    document.body.classList.toggle('dark');
  }
  isDarkMode(): boolean {
    return document.body.classList.contains('dark');
  }


}
