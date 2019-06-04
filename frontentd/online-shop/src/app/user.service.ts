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

@Injectable({
    providedIn: 'root',
  })
 
export class UserService{

    productsUrl='http://localhost:8080/products';
    

    constructor(private http:HttpClient){
    }

    login(credentials, callback) {

        const headers = new HttpHeaders(credentials ? {
            authorization : 'Basic ' + btoa(credentials.username + ':' + credentials.password)
        } : {});

        this.http.get('http://localhost:8080/user', {headers: headers}).subscribe(response => {
            
                localStorage.setItem('user',JSON.stringify(response))
            
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