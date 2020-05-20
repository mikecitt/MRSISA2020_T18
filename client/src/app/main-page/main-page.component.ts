import { Component, OnInit } from '@angular/core';
import { AuthService, MedicalService } from '../service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ProfileComponent } from '../profile/profile.component';
import { ClinicProfileComponent } from '../clinic-profile/clinic-profile.component';

import { trigger, state, style, transition, animate, keyframes} from '@angular/animations';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css'],
  animations: [
    trigger('sidebaranimation', [
      state('shown', style({
        marginLeft: '-15rem'
      })),
      state('hidden', style({
        marginLeft: '0px'
      })),

      transition('shown <=> hidden', animate('400ms ease-in'))
    ])
  ]
})
export class MainPageComponent implements OnInit {
  public isSideBarCollapsed = false
  public isMenuCollapsed = true
  public is

  daysOff = null;
  events = null;
  authority;

  constructor(private service: AuthService, private modalService: NgbModal,
              private medicalService: MedicalService) { }

  toggle() {
    this.isSideBarCollapsed = !this.isSideBarCollapsed;
  }

  ngOnInit(): void {
    this.service.whoAmI().subscribe(data => {
      this.authority = data['authorities'][0]['authority'];
      if(this.authority == 'ROLE_NURSE' || this.authority == 'ROLE_DOCTOR' ) {
        this.medicalService.getTimeOffs(this.authority).subscribe(data => {
          this.daysOff = data['data'];

          if(this.authority == 'ROLE_DOCTOR') {
            this.medicalService.getEvents(this.authority).subscribe(data => {
              this.events = data['events'];
            });
          }
          else {
            this.events = [];
          }
        });
      }
      else {
        this.daysOff = [];
        this.events = []
      }
    })
  }

  onLogout(): void {
    this.authority = null;
    this.service.logout();
  }

  isHomePage(): boolean {
    return this.service.getCurrentRoute() == '/';
  }

  openProfile(): void {
    this.modalService.open(ProfileComponent, { size: 'lg' });
  }

  openClinicProfile(): void {
    this.modalService.open(ClinicProfileComponent, { size: 'lg' });
  }
}
