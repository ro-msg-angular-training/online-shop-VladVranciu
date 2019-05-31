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
    return component.dialogService.confirm('Discard changes?');
  }
}
