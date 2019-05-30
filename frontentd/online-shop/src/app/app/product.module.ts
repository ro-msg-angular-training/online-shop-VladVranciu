import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductRoutingModule } from './product-routing.module';
import {ProductDetailComponent} from '../product-detail/product-detail.component';
import { BrowserModule } from '@angular/platform-browser';
@NgModule({
  declarations: [
    
  ],
  imports: [
    CommonModule,
    ProductRoutingModule,
    BrowserModule
  ],
  
})
export class ProductModule { }
