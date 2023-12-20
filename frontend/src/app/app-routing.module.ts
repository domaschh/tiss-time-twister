import {NgModule} from '@angular/core';
import {mapToCanActivate, RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {AuthGuard} from './guards/auth.guard';
import {MessageComponent} from './components/message/message.component';
import {ImportComponent} from "./components/import/import.component";
import {CreateEditMode, CreateEditCustomEventComponent} from "./components/custom-event-modal/create-edit-custom-event.component";

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'import', component: ImportComponent},
  {path: 'event', children: [
      {path: '', redirectTo: 'create', pathMatch: 'full'},
      {path: 'create', component: CreateEditCustomEventComponent, data: {mode: CreateEditMode.create}},
      {path: 'edit/:id', component: CreateEditCustomEventComponent, data: {mode: CreateEditMode.edit}},
    ]},
  {path: 'message', canActivate: mapToCanActivate([AuthGuard]), component: MessageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
