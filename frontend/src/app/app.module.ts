import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './components/header/header.component';
import {FooterComponent} from './components/footer/footer.component';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {MessageComponent} from './components/message/message.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {httpInterceptorProviders} from './interceptors';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { CalendarPageComponent } from './components/calendar-page/calendar-page.component';
import { ImportComponent } from './components/import/import.component';
import { EventPipePipe } from './pipes/event.pipe';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LogoutSuccessModalComponent } from './components/logout-success-modal/logout-success-modal.component';
import { RegistrationComponent } from './components/registration/registration.component';

import { CreateEditCustomEventComponent } from './components/create-edit-custom-event/create-edit-custom-event.component';
import { ToastrModule } from 'ngx-toastr';
import { CreateConfigComponent } from './components/create-config/create-config.component';
import { RuleModalComponent } from './components/create-config/rule-modal/rule-modal.component';
import { PublicConfigCardComponent } from './components/public-config-card/public-config-card.component';
import { PublicPageComponent } from './components/public-page/public-page.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ConfirmationModal } from './components/delete-modal/confirmation-modal.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    LoginComponent,
    MessageComponent,
    CalendarPageComponent,
    ImportComponent,
    LogoutSuccessModalComponent,
    CreateEditCustomEventComponent,
    CreateConfigComponent,
    RuleModalComponent,
    PublicConfigCardComponent,
    PublicPageComponent,
    NavbarComponent,
    ConfirmationModal,
    RegistrationComponent,
    ResetPasswordComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    FormsModule,
    CalendarModule.forRoot({ provide: DateAdapter, useFactory: adapterFactory }),
    EventPipePipe,
    BrowserAnimationsModule,
    CalendarModule.forRoot({provide: DateAdapter, useFactory: adapterFactory}),
    ToastrModule.forRoot()
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
