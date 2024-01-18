import { NgModule } from '@angular/core';
import { mapToCanActivate, RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { CalendarPageComponent } from './components/calendar-page/calendar-page.component';
import { AuthGuard } from './guards/auth.guard';
import { MessageComponent } from './components/message/message.component';
import { ImportComponent } from "./components/import/import.component";
import { CreateEditMode, CreateEditCustomEventComponent } from "./components/create-edit-custom-event/create-edit-custom-event.component";
import { CreateConfigComponent } from "./components/create-config/create-config.component";
import { PublicPageComponent } from "./components/public-page/public-page.component";
import { RegistrationComponent } from './components/registration/registration.component';
import { ResetPasswordMode, ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { PreviewConfigComponent } from './components/preview-config/preview-config.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'message', canActivate: mapToCanActivate([AuthGuard]), component: MessageComponent },
  { path: 'calendar', component: CalendarPageComponent },
  { path: 'import', component: ImportComponent },
  { path: 'createConfig', component: CreateConfigComponent },
  { path: 'publicConfigs', component: PublicPageComponent },
  { path: 'previewConfig', component: PreviewConfigComponent },
  {
    path: 'event', children: [
      { path: '', redirectTo: 'create', pathMatch: 'full' },
      { path: 'create', component: CreateEditCustomEventComponent, data: { mode: CreateEditMode.create } },
      { path: 'edit/:id', component: CreateEditCustomEventComponent, data: { mode: CreateEditMode.edit } },
    ]
  },
  { path: 'register', component: RegistrationComponent },
  { path: 'forgotPassword', component: ResetPasswordComponent, data: { mode: ResetPasswordMode.Forgot } },
  { path: 'resetPassword', component: ResetPasswordComponent, data: { mode: ResetPasswordMode.Reset } },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
