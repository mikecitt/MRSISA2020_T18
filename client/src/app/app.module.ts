import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http'
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import { AdminComponent } from './admin/admin.component';
import { AppRoutingModule } from './app-routing.module';
import { PatientComponent } from './patient/patient.component';
import { AdminClComponent } from './admincl/admincl.component';
import { MainPageComponent } from './main-page/main-page.component';
import { LoginComponent } from './login/login.component';
import { DoctorsTableComponent } from './doctors-table/doctors-table.component';
import { ClinicComponent } from './clinic/clinic.component';
import { DoctorComponent } from './doctor/doctor.component';
import { RegisterComponent } from './register/register.component';
import { CodebookComponent } from './codebook/codebook.component';
import { RoomComponent } from './room/room.component';
import { ExaminationTypeComponent } from './examination-type/examination-type.component';
import { MedicalRecordComponent } from './medical-record/medical-record.component';

import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './interceptor/token.interceptor';

import { CookieService } from 'ngx-cookie-service';
import { StorageServiceModule } from 'ngx-webstorage-service';

import { PatientsTableComponent } from './patients-table/patients-table.component';
import { NursePanelComponent } from './nurse-panel/nurse-panel.component';
import { ExaminationTypeViewComponent } from './examination-type-view/examination-type-view.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { ProfileComponent } from './profile/profile.component';
import { ClinicTableComponent } from './clinic-table/clinic-table.component';

import { NgbdSortableHeader } from './clinic-table/clinic-table.component';


@NgModule({
  declarations: [
    AppComponent,
    AdminComponent,
    AdminClComponent,
    PatientComponent,
    MainPageComponent,
    LoginComponent,
    DoctorsTableComponent,
    ClinicComponent,
    DoctorComponent,
    RegisterComponent,
    CodebookComponent,
    RoomComponent,
    ExaminationTypeComponent,
    MedicalRecordComponent,
    PatientsTableComponent,
    NursePanelComponent,
    ExaminationTypeViewComponent,
    ForbiddenComponent,
    ProfileComponent,
    ClinicTableComponent,
    NgbdSortableHeader,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    NgbModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    StorageServiceModule
  ],
  providers: [
  {
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi: true
  },
  CookieService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
