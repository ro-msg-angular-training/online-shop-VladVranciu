import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
}                                 from '@angular/router';
import { Observable, of, EMPTY }  from 'rxjs';
import { mergeMap, take }         from 'rxjs/operators';
import { Product } from '../model/Product';
import { ProductService } from '../product.service';
@Injectable({
  providedIn: 'root'
})
export class ProductsDetailResolverService implements Resolve<Product>{


  
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Product> | Observable<never> {
    let id = route.paramMap.get('id');
 
    return this.service.getProduct(id).pipe(
      take(1),
      mergeMap(product => {
        if (product) {
          return of(product);
        } else { // id not found
          this.router.navigate(['/products']);
          return EMPTY;
        }
      })
    );
  }

  constructor(private service:ProductService,private router:Router) { }

}
