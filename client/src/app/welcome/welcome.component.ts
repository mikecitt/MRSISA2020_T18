import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  constructor(private service:AuthService) { }

  ngOnInit(): void {
  }

  showCalendar(): boolean {
    return this.service.hasRole('ROLE_DOCTOR', 'ROLE_NURSE', 'ROLE_CLINIC_ADMIN');
  }
}
