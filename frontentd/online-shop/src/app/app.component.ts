import { Component } from '@angular/core';
import {Product} from './model/product';
import { MatSnackBar } from '@angular/material';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'online-shop';

  loggedIn(){
    // var user=JSON.parse(localStorage.getItem('user'));
    // // console.log(user);
    // if(user==null){
    //   return false;
    // }
    return true;
  }
}
