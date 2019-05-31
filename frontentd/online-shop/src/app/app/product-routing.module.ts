import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ProductDetailComponent} from '../product-detail/product-detail.component';
import { CanDeactivateGuard } from '../can-deactivate.guard';
import { ProductsDetailResolverService } from '../products/products-detail-resolver.service';
import { AuthGuard } from '../auth/auth.guard';

const routes: Routes = [
  {
    path:'product/:id',
    component:ProductDetailComponent,
    canDeactivate:[CanDeactivateGuard],
    canActivate:[AuthGuard],
    resolve:{
      product:ProductsDetailResolverService
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductRoutingModule { }
