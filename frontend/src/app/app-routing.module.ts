import {NgModule} from '@angular/core';
import {mapToCanActivate, RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import { CalendarPageComponent } from './components/calendar-page/calendar-page.component';
import {AuthGuard} from './guards/auth.guard';
import {MessageComponent} from './components/message/message.component';
import {ImportComponent} from "./components/import/import.component";

const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'message', canActivate: mapToCanActivate([AuthGuard]), component: MessageComponent},
  {path: 'calendar', component: CalendarPageComponent},
  {path: 'import', component: ImportComponent},
  {path: 'message', canActivate: mapToCanActivate([AuthGuard]), component: MessageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
