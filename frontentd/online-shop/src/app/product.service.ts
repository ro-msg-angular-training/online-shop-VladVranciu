import {Product} from './model/product';
import {PRODUCTS} from './mock-products';
import { Observable, of, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { ProductCategory } from './model/productCategory';
import { Supplier } from './model/supplier';

@Injectable({
    providedIn: 'root',
  })
 
export class ProductService{

    productsUrl='http://localhost:8080/products';
    categoriesUrl='http://localhost:8080/categories';
    suppliersUrl='http://localhost:8080/suppliers'


    constructor(private http:HttpClient){
    }

    getProductCategories():Observable<ProductCategory[]>{
        let hd=new HttpHeaders({
            'Content-Type':  'application/json'
        });
        return this.http.get<ProductCategory[]>(this.categoriesUrl,{headers:hd})
                    .pipe(catchError(this.handleError));
    }

    getSuppliers():Observable<Supplier[]>{
        let hd=new HttpHeaders({
            'Content-Type':  'application/json'
        });
        return this.http.get<Supplier[]>(this.suppliersUrl,{headers:hd})
                    .pipe(catchError(this.handleError));
    }

    getProducts():Observable<Product[]>{
        let hd=new HttpHeaders({
            'Content-Type':  'application/json'
        })
        return this.http.get<Product[]>(this.productsUrl,{headers:hd})
            .pipe(catchError(this.handleError));
            

    }
    updateProduct(product:Product,id:any){
        let hd=new HttpHeaders({
            'Content-Type':  'application/json'
        })
        const updateUrl=`${this.productsUrl}/${id}`
        return this.http.put<Product>(updateUrl,product,id).subscribe(res=>{

        });
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