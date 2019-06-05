import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';

import { Router } from '@angular/router';
import { FormGroup, FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Customer } from '../model/customer';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private service: UserService, private http: HttpClient, private router: Router) {
  }

  register:FormGroup;

  ngOnInit() {
    this.register=new FormGroup({
      firstname:new FormControl(''),
      lastname:new FormControl(''),
      user:new FormControl(''),
      pass:new FormControl(''),
      pass2:new FormControl(''),
      email:new FormControl('')
    })
  }

  registerUser(registerValue){
    var customer=new Customer(registerValue.firstname,registerValue.lastname,registerValue.user,registerValue.pass,registerValue.email);
    console.log(customer);
    this.service.register(customer);
    this.router.navigate(['/login']);
  }

}
