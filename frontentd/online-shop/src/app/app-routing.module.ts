import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DisplayProductComponent } from './display-product/display-product.component';

const routes: Routes = [
  // {
  //   path:"", redirectTo:"/products", pathMatch:"full"
  // },
  {
    
    path:"products",component:DisplayProductComponent
  }
  
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
