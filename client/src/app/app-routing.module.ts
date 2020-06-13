import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { AdminClComponent } from './admincl/admincl.component';
import { PatientComponent } from './patient/patient.component';
import { DoctorComponent } from './doctor/doctor.component';
import { NurseComponent } from './nurse/nurse.component';
import { ClinicComponent } from './clinic/clinic.component';
import { RoomComponent } from './room/room.component';
import { ExaminationTypeComponent } from './examination-type/examination-type.component';
import { CodebookComponent } from './codebook/codebook.component';
import { PatientsTableComponent } from './patients-table/patients-table.component';
import { MainPageComponent } from './main-page/main-page.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { RegistrationReqComponent } from './registration-req/registration-req.component';
import { VacationReqComponent } from './vacation-req/vacation-req.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { AppointmentHistoryComponent } from './appointment-history/appointment-history.component';
import { AppointmentPredefComponent } from './appointment-predef/appointment-predef.component';
import { LoginGuard, MainPageGuard, AdminGuard, AdminclGuard, MedicalGuard, PatientGuard, ActivationGuard } from './guard';
import { ActivationPageComponent } from './activation-page/activation-page.component';
import { ClinicTableComponent } from './clinic-table/clinic-table.component';
import { ApprovementComponent } from './approvement/approvement.component';
import { ClinicReportComponent } from './clinic-report/clinic-report.component';
import { Router } from '@angular/router';

const routes: Routes = [
  {
    path: '', component: MainPageComponent,
    children: [
      { path: '', component: WelcomeComponent},
      { path: 'patient', component: PatientComponent, canActivate: [PatientGuard] },
      { path: 'clinic-list', component: ClinicTableComponent, canActivate: [PatientGuard] },
      { path: 'appointment-history', component: AppointmentHistoryComponent, canActivate: [PatientGuard] },
      { path: 'admin', component: AdminComponent, canActivate: [AdminGuard] },
      { path: 'admincl', component: AdminClComponent, canActivate: [AdminGuard] },
      { path: 'doctor', component: DoctorComponent, canActivate: [AdminclGuard] },
      { path: 'nurse', component: NurseComponent, canActivate: [AdminclGuard] },
      { path: 'clinic', component: ClinicComponent, canActivate: [AdminGuard] },
      { path: 'room', component: RoomComponent, canActivate: [AdminclGuard] },
      { path: 'examination-type', component: ExaminationTypeComponent, canActivate: [AdminclGuard] },
      { path: 'codebook', component: CodebookComponent, canActivate: [AdminGuard] },
      { path: 'patient-list', component: PatientsTableComponent, canActivate: [MedicalGuard] },
      { path: 'registration-req', component: RegistrationReqComponent, canActivate: [AdminGuard]},
      { path: 'vacation-req', component: VacationReqComponent, canActivate: [AdminclGuard]},
      { path: 'appointment-predef', component: AppointmentPredefComponent, canActivate: [AdminclGuard]},
      { path: 'approvement', component: ApprovementComponent, canActivate: [AdminclGuard]},
      { path: 'clinic-report', component: ClinicReportComponent, canActivate: [AdminclGuard]},
    ],
    canActivate: [MainPageGuard]
  },
  { path: 'login', component: LoginComponent, canActivate: [LoginGuard] },
  { path: 'activation', component: ActivationPageComponent, canActivate: [ActivationGuard]},
  { path: 'register', component: RegisterComponent, canActivate: [LoginGuard] },
  { path: '403', component: ForbiddenComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
