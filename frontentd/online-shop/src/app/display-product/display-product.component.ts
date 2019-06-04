import { Component, OnInit } from '@angular/core';
import { Product } from '../model/Product';
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';
import {ProductService} from '../product.service'
import { ProductInputObject } from '../model/productInputObject';
import { CartItem } from '../model/cartItem';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'display-product',
  templateUrl: './display-product.component.html',
  styleUrls: ['./display-product.component.css']
})
export class DisplayProductComponent implements OnInit {
products:Observable<Product[]>;
selectedId:number;
  constructor(private service:ProductService,private route: ActivatedRoute,private snackBar: MatSnackBar){
  }

  ngOnInit() {
   this.products=this.route.paramMap.pipe(switchMap(params=>{
     this.selectedId=+params.get('id');
     return this.service.getProducts();
   }))
  }

  addToCart(product:Product){
    var item=new CartItem(product.name,product.id,1);
    if(localStorage.getItem("cart")!=null){
      var cart=JSON.parse(localStorage.getItem("cart"));
      var ok=0;
      for(let i=0;i<cart.length;i++){
        if(cart[i].productId===product.id){
          ok=1;
        }
      }
      if(ok===0){
        cart.push(item);
          localStorage.setItem("cart",JSON.stringify(cart));
          this.openSnackBar("Item "+item.name+" has been aded to cart","#"+cart.length);
      }else{
        this.openSnackBar("Item "+item.name+" has been already been aded to cart","#"+cart.length);
      }
      
    }else{
      cart=[];
      cart.push(item);
      localStorage.setItem("cart",JSON.stringify(cart));
      this.openSnackBar("Item "+item.name+" has been aded to cart","#"+cart.length);
    }
  }
  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 2000,
      panelClass : ['background-red']
    });
  }
}
