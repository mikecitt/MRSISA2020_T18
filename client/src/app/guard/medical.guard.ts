import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { UserService } from '../service';

@Injectable({
  providedIn: 'root'
})
export class MedicalGuard implements CanActivate {

  constructor(private router: Router, private userService: UserService) { }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.userService.currentUser) {
      let authorities = JSON.stringify(this.userService.currentUser.authorities);
      if (authorities.search('ROLE_DOCTOR') !== -1 ||
          authorities.search('ROLE_NURSE') !== -1) {
          if(authorities.search('ROLE_NURSE') !== -1 && state.url === '/appointment-rep') {
            this.router.navigate(['/403']);
            return false;
          }
        return true;
      } else {
        this.router.navigate(['/403']);
        return false;
      }

    }
  }

}
