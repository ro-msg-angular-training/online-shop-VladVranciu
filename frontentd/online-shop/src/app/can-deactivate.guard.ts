import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { CanDeactivate } from '@angular/router';
import { ProductDetailComponent } from './product-detail/product-detail.component';

export interface CanComponentDeactivate {
  canDeactivate: () => Observable<boolean> | Promise<boolean> | boolean;
 }

@Injectable({
  providedIn: 'root'
})
export class CanDeactivateGuard implements CanDeactivate<ProductDetailComponent> {
  canDeactivate(
    component: ProductDetailComponent,
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | boolean {
    console.log("AuthGuard#canDeactivate");
    
    if (!component.product$ || !component.weightinput && !component.priceinput && !component.descinput && !component.nameinput) {
      return true;
    }
    if(component.validate()){
      return true;
    }
    //if(component.weightinput > 10 && component.weightinput < 100 && component.descinput.length > 5 && component.descinput.length < 20 && component.nameinput.length > 5 && component.nameinput.length < 20 && component.priceinput > 10 && component.priceinput < 1000)
    return component.dialogService.confirm('Discard changes?');
    //return false;
  }
}
