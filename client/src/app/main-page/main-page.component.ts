import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';

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

  authority;

  constructor(private service: AuthService) { }

  toggle() {
    this.isSideBarCollapsed = !this.isSideBarCollapsed;
  }

  ngOnInit(): void {
    this.service.whoAmI().subscribe(data => {
      this.authority = data['authorities'][0]['authority'];
    })
  }

}
