import { NgModule } from '@angular/core';
import { BrowserModule, Title } from '@angular/platform-browser';
import {APP_BASE_HREF} from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './Components/login/login.component';
import { LayoutComponent } from './layout/layout.component';
import { RegisterComponent } from './Components/register/register.component';
import { ProfileComponent } from './Components/profile/profile.component';
import { DashboardComponent } from './Components/dashboard/dashboard.component';
import { HospitalComponent } from './Components/hospital/hospital.component';
import { PatientComponent } from './Components/patient/patient.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LayoutComponent,
    RegisterComponent,
    ProfileComponent,
    DashboardComponent,
    HospitalComponent,
    PatientComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
  ],
  providers: [Title, {provide: APP_BASE_HREF, useValue : '/' }],
  bootstrap: [AppComponent]
})
export class AppModule { }
