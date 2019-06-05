import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { UserService } from '../user.service';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  
  login:FormGroup;

  constructor(private service: UserService, private http: HttpClient, private router: Router) {
  }
  ngOnInit(){
    this.login=new FormGroup({
      user:new FormControl(''),
      pass:new FormControl('')
    })

  }

loginUser(loginValue) {
  var credentials = {user: loginValue.user, pass: loginValue.pass};
  this.service.login(credentials, () => {
      this.router.navigate(['/products']);
  });
  return false;
}
}
