import {Product} from './model/product';
import {PRODUCTS} from './mock-products';
import { Observable, of, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders, HttpErrorResponse } from '@angular/common/http';

@Injectable({
    providedIn: 'root',
  })
 
export class ProductService{

    productsUrl='http://localhost:8080/products';

    constructor(private http:HttpClient){
    }
    getProducts():Observable<Product[]>{
        let hd=new HttpHeaders({
            'Content-Type':  'application/json'
        })
        return this.http.get<Product[]>(this.productsUrl,{headers:hd})
            .pipe(catchError(this.handleError));
            

    }
    
    deleteProduct(id:number | string){
        let hd=new HttpHeaders({
            'Content-Type':  'application/json'
        })
        const deleteUrl=`${this.productsUrl}/${id}`
        return this.http.delete(deleteUrl,{headers:hd})
            .pipe(catchError(this.handleError));
    }

    

    getProduct(id:number | string){
        return this.getProducts().pipe(
            map((products:Product[])=>
            products.find(product=> product.id===+id))
        )
    };

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