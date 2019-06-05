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
 
export class ProductService{

    productsUrl='http://localhost:8080/products';
    categoriesUrl='http://localhost:8080/categories';
    suppliersUrl='http://localhost:8080/suppliers';
    ordersUrl='http://localhost:8080/orders';

    constructor(private http:HttpClient){
    }

    getProductCategories():Observable<ProductCategory[]>{
        var credentials=JSON.parse(localStorage.getItem('user'));
        const headers = new HttpHeaders(credentials ? {
            authorization : 'Basic ' + btoa(credentials.user + ':' + credentials.pass)
        } : {});
        return this.http.get<ProductCategory[]>(this.categoriesUrl,{headers:headers})
                    .pipe(catchError(this.handleError));
    }

    getSuppliers():Observable<Supplier[]>{
        var credentials=JSON.parse(localStorage.getItem('user'));
        const headers = new HttpHeaders(credentials ? {
            authorization : 'Basic ' + btoa(credentials.user + ':' + credentials.pass)
        } : {});
        return this.http.get<Supplier[]>(this.suppliersUrl,{headers:headers})
                    .pipe(catchError(this.handleError));
    }

    getProducts():Observable<Product[]>{
        var credentials=JSON.parse(localStorage.getItem('user'));
        const headers = new HttpHeaders(credentials ? {
            authorization : 'Basic ' + btoa(credentials.user + ':' + credentials.pass)
        } : {});
        return this.http.get<Product[]>(this.productsUrl,{headers:headers})
            .pipe(catchError(this.handleError));
            

    }
    updateProduct(product:Product,id:any){
        var credentials=JSON.parse(localStorage.getItem('user'));
        const headers = new HttpHeaders(credentials ? {
            authorization : 'Basic ' + btoa(credentials.user + ':' + credentials.pass)
        } : {});
        const updateUrl=`${this.productsUrl}/${id}`
        return this.http.put<Product>(updateUrl,product,{headers:headers}).subscribe(res=>{

        });
    }
    deleteProduct(id:number | string){
        var credentials=JSON.parse(localStorage.getItem('user'));
        const headers = new HttpHeaders(credentials ? {
            authorization : 'Basic ' + btoa(credentials.user + ':' + credentials.pass)
        } : {});
        const deleteUrl=`${this.productsUrl}/${id}`
        return this.http.delete(deleteUrl,{headers:headers})
            .pipe(catchError(this.handleError));
    }

    addProduct(product:Product){
        var credentials=JSON.parse(localStorage.getItem('user'));
        const headers = new HttpHeaders(credentials ? {
            authorization : 'Basic ' + btoa(credentials.user + ':' + credentials.pass)
        } : {});
        return this.http.post<Product>(this.productsUrl,product,{headers:headers}).subscribe(res=>{

        });
    }

    placeOrder(oio:OrderInputObject){
        var credentials=JSON.parse(localStorage.getItem('user'));
        const headers = new HttpHeaders(credentials ? {
            authorization : 'Basic ' + btoa(credentials.user + ':' + credentials.pass)
        } : {});
        return this.http.post<Observable<Exception>>(this.ordersUrl,oio,{headers:headers}).subscribe(res=>{
            localStorage.setItem("lastorder",JSON.stringify(res));
          })
        
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