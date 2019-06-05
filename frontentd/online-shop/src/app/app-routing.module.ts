import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DisplayProductComponent } from './display-product/display-product.component';
import { AddProductComponent } from './add-product/add-product.component';
import { CartComponent } from './cart/cart.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';

const routes: Routes = [
  // {
  //   path: '', redirectTo: '/login', pathMatch: 'full'
  // },
  {
    path:"products",component:DisplayProductComponent
  },
  {
    path:'add',component:AddProductComponent
  },{
    path:'cart',component:CartComponent
  },
  {
    path:'login',component:LoginComponent
  },{
    path:'register',component:RegisterComponent
  }
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
