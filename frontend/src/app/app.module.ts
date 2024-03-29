import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { NgxFileDropModule } from 'ngx-file-drop';

import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { MessageComponent } from './components/message/message.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { httpInterceptorProviders } from './interceptors';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { CalendarPageComponent } from './components/calendar-page/calendar-page.component';
import { ImportComponent } from './components/import/import.component';
import { EventPipePipe } from './pipes/event.pipe';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RegistrationComponent } from './components/registration/registration.component';

import { CreateEditCustomEventComponent } from './components/create-edit-custom-event/create-edit-custom-event.component';
import { ToastrModule } from 'ngx-toastr';
import { CreateConfigComponent } from './components/create-config/create-config.component';
import { RuleModalComponent } from './components/create-config/rule-modal/rule-modal.component';
import { PublicConfigCardComponent } from './components/public-config-card/public-config-card.component';
import { PublicPageComponent } from './components/public-page/public-page.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ConfirmationModal } from './components/delete-modal/confirmation-modal.component';
import { PreviewConfigComponent } from './components/preview-config/preview-config.component';
import { ConfigModalComponent } from './components/public-config-card/config-modal/config-modal.component';
import { RuleFoldComponent } from './components/public-config-card/rule-fold/rule-fold.component';
import { NavigationPopoverComponent } from './components/navbar/navigation-popover/navigation-popover.component';
import { NavigationPopoverRightComponent } from './components/navbar/navigation-popover-right/navigation-popover-right.component';
import { MatTooltipModule } from '@angular/material/tooltip';
import { RuleFoldInputComponent } from './components/public-config-card/rule-fold-input/rule-fold-input.component';
import { PublicCardFakeComponent } from './components/public-config-card/public-card-fake/public-card-fake.component';
import { DefaultConfigModalComponent } from './components/public-config-card/public-card-fake/default-config-modal/default-config-modal.component';
import { LvaShorthandsComponent } from './components/public-config-card/public-card-fake/lva-shorthands/lva-shorthands.component';
import { RoomMappingComponent } from './components/public-config-card/public-card-fake/room-mapping/room-mapping.component';
import { ConfigImportComponent } from "./components/calendar-import/config-import.component";
import { CdkDropList } from "@angular/cdk/drag-drop";
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { HelpPageComponent } from './components/help-page/help-page.component';

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
    CreateEditCustomEventComponent,
    CreateConfigComponent,
    RuleModalComponent,
    PublicConfigCardComponent,
    PublicPageComponent,
    NavbarComponent,
    ConfirmationModal,
    RegistrationComponent,
    ResetPasswordComponent,
    PreviewConfigComponent,
    ConfigModalComponent,
    RuleFoldComponent,
    NavigationPopoverComponent,
    NavigationPopoverRightComponent,
    RuleFoldInputComponent,
    ConfigImportComponent,
    PublicCardFakeComponent,
    DefaultConfigModalComponent,
    LvaShorthandsComponent,
    RoomMappingComponent,
    HelpPageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatTooltipModule,
    NgxFileDropModule,
    NgbModule,
    FormsModule,
    CalendarModule.forRoot({ provide: DateAdapter, useFactory: adapterFactory }),
    EventPipePipe,
    BrowserAnimationsModule,
    CalendarModule.forRoot({ provide: DateAdapter, useFactory: adapterFactory }),
    ToastrModule.forRoot(),
    CdkDropList
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
