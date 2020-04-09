import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { AdminClComponent } from './admincl/admincl.component';
import { PatientComponent } from './patient/patient.component';

const routes: Routes = [
  { path: 'admin', component: AdminComponent },
  { path: 'admincl', component: AdminClComponent },
  { path: 'patient', component: PatientComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
