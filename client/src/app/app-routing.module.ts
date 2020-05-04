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
import { MainPageComponent } from './main-page/main-page.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { Router } from '@angular/router';

const routes: Routes = [
  { path: '', component: MainPageComponent, children: [
    { path: 'patient', component: PatientComponent },
    { path: 'admin', component: AdminComponent },
    { path: 'admincl', component: AdminClComponent },
    { path: 'doctor', component: DoctorComponent },
    { path: 'clinic', component: ClinicComponent },
    { path: 'room', component: RoomComponent },
    { path: 'examination-type', component: ExaminationTypeComponent },
    { path: 'codebook', component: CodebookComponent }
  ]},
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
