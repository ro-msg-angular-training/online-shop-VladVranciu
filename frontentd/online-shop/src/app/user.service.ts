import {Product} from './model/product';
import {PRODUCTS} from './mock-products';
import { Observable, of, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { ProductCategory } from './model/productCategory';
import { Supplier } from './model/supplier';
import { OrderInputObject } from './model/orderInputObject';
import { Exception } from './model/exception';
import { Customer } from './model/customer';

@Injectable({
    providedIn: 'root',
  })
 
export class UserService{

    productsUrl='http://localhost:8080/products';
    

    constructor(private http:HttpClient){
    }

       register(customer:Customer){
        this.http.post<Customer>("http://localhost:8080/register",customer).subscribe(res=>{

        });
       }
        login(credentials, callback) {

            const headers = new HttpHeaders(credentials ? {
                authorization : 'Basic ' + btoa(credentials.user + ':' + credentials.pass)
            } : {});
    
            this.http.get('http://localhost:8080/login', {headers: headers}).subscribe(response => {
                if (response) {
                    // this.authenticated = true;
                    localStorage.setItem('user',JSON.stringify(credentials));
                } 
                return callback && callback();
            });
    
        }
    private handleError(error:HttpErrorResponse){
        if(error.error instanceof ErrorEvent){
            console.error('An error occurred:',error.error.message);
        }else{
            console.error(`Backend returned code ${error.status}, ` +
            `body was: ${error.error}`);
        }
        return throwError('Something bad happened;please try again later.');
    }
}