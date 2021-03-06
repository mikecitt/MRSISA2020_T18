import { BrowserModule } from '@angular/platform-browser';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
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
import { FullCalendarModule } from '@fullcalendar/angular';

import { PatientsTableComponent } from './patients-table/patients-table.component';
import { ExaminationTypeViewComponent } from './examination-type-view/examination-type-view.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { ProfileComponent } from './profile/profile.component';
import { ClinicTableComponent } from './clinic-table/clinic-table.component';
import { RegistrationReqComponent } from './registration-req/registration-req.component';
import { RoomsTableComponent } from './rooms-table/rooms-table.component';
import { ClinicProfileComponent } from './clinic-profile/clinic-profile.component';
import { CalendarComponent } from './calendar/calendar.component';
import { VacationReqComponent } from './vacation-req/vacation-req.component';
import { DateIntervalComponent } from './date-interval/date-interval.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { RoomEditComponent } from './room-edit/room-edit.component';
import { ExaminationTypeEditComponent } from './examination-type-edit/examination-type-edit.component';
import { AppointmentHistoryComponent } from './appointment-history/appointment-history.component';

import { NgSortableHeader } from './sortable-table';
import { ClinicDetailsComponent } from './clinic-details/clinic-details.component';
import { NurseComponent } from './nurse/nurse.component';
import { NursesTableComponent } from './nurses-table/nurses-table.component';
import { AppointmentPredefComponent } from './appointment-predef/appointment-predef.component';
import { SearchExaminationComponent } from './search-examination/search-examination.component';
import { FreetimeDoctorTableComponent } from './freetime-doctor-table/freetime-doctor-table.component';
import { ApprovementComponent } from './approvement/approvement.component';
import { AppointmentStartModalComponent } from './appointment-start-modal/appointment-start-modal.component';
import { ActivationPageComponent } from './activation-page/activation-page.component';

import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { BarRatingModule } from "ngx-bar-rating";
import { NgxSpinnerModule } from 'ngx-spinner';
import { ClinicReportComponent } from './clinic-report/clinic-report.component';
import { ClinicCardComponent } from './clinic-card/clinic-card.component';
import { RoomCalendarComponent } from './room-calendar/room-calendar.component';
import { ChartsModule } from 'ng2-charts';
import { AppointmentReportComponent } from './appointment-report/appointment-report.component';
import { AppointmentDoctorModalComponent } from './appointment-doctor-modal/appointment-doctor-modal.component';
import { CheckRecipeComponent } from './check-recipe/check-recipe.component';

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
    ExaminationTypeViewComponent,
    ForbiddenComponent,
    ProfileComponent,
    ClinicTableComponent,
    RegistrationReqComponent,
    RoomsTableComponent,
    ClinicProfileComponent,
    CalendarComponent,
    WelcomeComponent,
    VacationReqComponent,
    DateIntervalComponent,
    RoomEditComponent,
    ExaminationTypeEditComponent,
    AppointmentHistoryComponent,
    NgSortableHeader,
    ClinicDetailsComponent,
    NurseComponent,
    NursesTableComponent,
    SearchExaminationComponent,
    FreetimeDoctorTableComponent,
    AppointmentPredefComponent,
    ApprovementComponent,
    AppointmentStartModalComponent,
    ActivationPageComponent,
    ClinicReportComponent,
    ClinicCardComponent,
    RoomCalendarComponent,
    AppointmentDoctorModalComponent,
    AppointmentReportComponent,
    CheckRecipeComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    NgbModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    StorageServiceModule,
    FullCalendarModule,
    BarRatingModule,
    NgxSpinnerModule,
    NgMultiSelectDropDownModule.forRoot(),
    ChartsModule
  ],
  providers: [
  {
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi: true
  },
  CookieService
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  bootstrap: [AppComponent]
})
export class AppModule { }
