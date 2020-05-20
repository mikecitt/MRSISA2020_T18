import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { AdminClComponent } from './admincl/admincl.component';
import { PatientComponent } from './patient/patient.component';
import { DoctorComponent } from './doctor/doctor.component';
import { ClinicComponent } from './clinic/clinic.component';
import { RoomComponent } from './room/room.component';
import { ExaminationTypeComponent } from './examination-type/examination-type.component';
import { CodebookComponent } from './codebook/codebook.component';
import { NursePanelComponent } from './nurse-panel/nurse-panel.component';
import { MainPageComponent } from './main-page/main-page.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { RegistrationReqComponent } from './registration-req/registration-req.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { LoginGuard, MainPageGuard, AdminGuard, AdminclGuard, MedicalGuard, PatientGuard } from './guard';
import { Router } from '@angular/router';

const routes: Routes = [
  {
    path: '', component: MainPageComponent,
    children: [
      { path: '', component: WelcomeComponent},
      { path: 'patient', component: PatientComponent, canActivate: [PatientGuard] },
      { path: 'admin', component: AdminComponent, canActivate: [AdminGuard] },
      { path: 'admincl', component: AdminClComponent, canActivate: [AdminGuard] },
      { path: 'doctor', component: DoctorComponent, canActivate: [AdminclGuard] },
      { path: 'clinic', component: ClinicComponent, canActivate: [AdminGuard] },
      { path: 'room', component: RoomComponent, canActivate: [AdminclGuard] },
      { path: 'examination-type', component: ExaminationTypeComponent, canActivate: [AdminclGuard] },
      { path: 'codebook', component: CodebookComponent, canActivate: [AdminGuard] },
      { path: 'nurse', component: NursePanelComponent, canActivate: [MedicalGuard] },
      { path: 'registration-req', component: RegistrationReqComponent, canActivate: [AdminGuard]}
    ],
    canActivate: [MainPageGuard]
  },
  { path: 'login', component: LoginComponent, canActivate: [LoginGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [LoginGuard] },
  { path: '403', component: ForbiddenComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
