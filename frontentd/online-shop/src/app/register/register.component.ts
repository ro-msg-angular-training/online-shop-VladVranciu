import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';

import { Router } from '@angular/router';
import { FormGroup, FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Customer } from '../model/customer';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private service: UserService, private http: HttpClient, private router: Router,private snackBar:MatSnackBar) {
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
    this.openSnackBar("User registered successfully!",null);
    this.service.register(customer);
    this.router.navigate(['/login']);
  }


  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 2000,
      panelClass : ['background-red']
    });
  }


 fnameinput="";
 lnameinput="";
 emailinput="";
 pass1input="";
 pass2input="";
 userinput="";

  fnameValidation(event:any){
    this.fnameinput=event.target.value;
  }
  lnameValidation(event:any){
    this.lnameinput=event.target.value;
  }
  userValidation(event:any){
    this.userinput=event.target.value;
  }
  emailValidation(event:any){
    this.emailinput=event.target.value;
  }
  pass1Validation(event:any){
    this.pass1input=event.target.value;
  }
  pass2Validation(event:any){
    this.pass2input=event.target.value;
  }

  validate(){
    var err=0;
    if(this.fnameinput.length < 5 || this.fnameinput.length >20)
      err+=1
    if(this.userinput.length < 5 || this.userinput.length >20)
      err+=1
    if(this.lnameinput.length < 5 || this.lnameinput.length >20)
      err+=1
    if(this.emailinput.length < 5 || this.emailinput.length >40)
      err+=1
    if(this.pass1input.length < 5 || this.pass1input.length >20)
      err+=1
    if(this.pass1input!=this.pass2input)
      err+=1
    if(err<1)
      return true;
    return false
  }


}
