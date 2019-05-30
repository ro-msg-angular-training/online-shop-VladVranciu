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
@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {
product$: Observable<Product>;;
categories$:Observable<ProductCategory[]>;
suppliers$:Observable<Supplier[]>
update:FormGroup;
supplierToUpdate:any;
categoryToUpdate:any;
constructor(
  private route: ActivatedRoute,
  private router: Router,
  private service: ProductService
) {}
  ngOnInit() {
    this.product$=this.route.paramMap.pipe(
      switchMap((params:ParamMap)=>
      this.service.getProduct(params.get('id'))
    ));

    this.suppliers$=this.service.getSuppliers();
    this.categories$=this.service.getProductCategories();

    this.update=new FormGroup({
      name:new FormControl(''),
      description:new FormControl(''),
      price:new FormControl(''),
      weight:new FormControl(''),
    });
  }
  delete(product:Product):void{
    this.product$=this.product$.filter(h=> h!=product);
    this.service.deleteProduct(product.id).subscribe();
  }

  updateProduct(updateValue,product:Product){
    var toUpdate=new Product(product.id,updateValue.name,updateValue.description,updateValue.price,updateValue.weight,this.categoryToUpdate,updateValue.productCategory,updateValue.supplier,this.supplierToUpdate,null);
    console.log(product.id);
    this.service.updateProduct(toUpdate,product.id);

  }
  updateSelectedForSupplier(event):void{
    this.supplierToUpdate=event.target.value
    console.log(this.supplierToUpdate);
    
  }
  updateSelectedForCategory(event):void{
    this.categoryToUpdate=event.target.value
    console.log(this.categoryToUpdate);
    
  }

  nameinput=""
  descinput=""
  priceinput=0
  weightinput=0

  nameValidation(event:any){
    this.nameinput=event.target.value;
  }
  descriptionValidation(event:any){
    this.descinput=event.target.value;
  }
  priceValidation(event:any){
    this.priceinput=+event.target.value;
  }
  weightValidation(event:any){
    this.weightinput=+event.target.value;
  }

  validate(){
    var err=0;
    if(this.nameinput.length < 5 || this.nameinput.length >20)
      err+=1
    if(this.descinput.length < 5 || this.descinput.length >20)
      err+=1
    if(this.priceinput < 10 || this.priceinput > 1000)
      err+=1
    if(this.weightinput < 10 || this.weightinput > 1000)
      err+=1
    console.log(err);
    if(err<1)
      return true;
    return false
  }
}
