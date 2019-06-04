import { Component, OnInit } from '@angular/core';
import {Product} from '../model/product';
import { Observable } from 'rxjs';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import {ProductService} from '../product.service';
import { switchMap } from 'rxjs/operators';
import { FormGroup, FormControl, Validators} from '@angular/forms';
import 'rxjs/Rx';
import { ProductCategory } from '../model/productCategory';
import {Supplier} from '../model/supplier';
import { DialogService } from '../dialog.service';
import { ProductInputObject } from '../model/productInputObject';
import { Address } from '../model/address';
import { OrderInputObject } from '../model/orderInputObject';
import { MatSnackBar } from '@angular/material';
import { Exception } from '../model/exception';
@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  constructor(private route: ActivatedRoute,
    private router: Router,
    private service: ProductService,
    private snackBar:MatSnackBar) { }

  cart=[];
  ngOnInit() {
    this.cart=JSON.parse(localStorage.getItem("cart"));
  }

  emptyCart(){
    localStorage.removeItem("cart");
    this.router.navigate(['/products']);
  }
  
  updateQuantity(e,item,value)
  {
    item.quantity=value;
    for (let i = 0; i < this.cart.length; i++) {
      if(this.cart[i].productId===item.productId)
        this.cart[i]=item;
    }
    localStorage.removeItem("cart");
    localStorage.setItem("cart",JSON.stringify(this.cart));
  }
  message;
  placeOrder(){
    let products=[]
    for (let i = 0; i < this.cart.length; i++) {
        var pio=new ProductInputObject(this.cart[i].productId,this.cart[i].quantity);
        products.push(pio);
    }
    let address=new Address("Romania","Suceava","Suceava","Address1");
    var oio=new OrderInputObject(address,products);
    var success;
    
    this.service.placeOrder(oio).subscribe(res=>{
      success=res as Exception
      this.message=success.message;
      
    })
    console.log(this.message);
    // var success=JSON.parse(localStorage.getItem('lastorder'));
    if(this.message===null){
      this.openSnackBar("Order placed successfully",null);
    }else{
      this.openSnackBar("Order could not be placed",null);
      
    }
    this.router.navigate(['/products']);
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 2000,
      panelClass : ['background-red']
    });
  }
  cartIsEmpty(){
    if(this.cart===null)
      return true;
    return false;
  }
  
}
