import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, NavigationExtras, CanActivateChild } from '@angular/router';
import { AuthService } from './auth.service';
@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate, CanActivateChild {
  
  constructor(private authService: AuthService, private router: Router) {}
  
  
  
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
    console.log('AuthGuard#canActivate called');
    let url:string=state.url;
    return this.checkLogin(url);
  }
  canActivateChild(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
    return this.canActivate(route, state);
  }
  checkLogin(url: string): boolean {
    if (this.authService.isLoggedIn) { return true; }
 
    this.authService.redirectUrl = url;
    let sessionId=1234;
    let navigationExtras:NavigationExtras={
      queryParams:{
        'session_id':sessionId},
        fragment: 'anchor'
      };
    this.router.navigate(['/products'],navigationExtras);
    return false;
  }
  
}
