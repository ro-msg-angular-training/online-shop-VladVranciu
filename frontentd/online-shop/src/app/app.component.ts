import { Component } from '@angular/core';
import {Product} from './model/product';
import { MatSnackBar } from '@angular/material';
import { Router } from '@angular/router';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'online-shop';

  constructor(public router: Router){
    
  }
  loggedIn(){
    var user=JSON.parse(localStorage.getItem('user'));
    // console.log(user);
    if(user==null){
      return false;
    }
    
    return true;
  }
  logout(){
    localStorage.removeItem('user');
  }
}
