import { Component, OnInit } from '@angular/core';
import {Product} from '../model/product';
import { Observable } from 'rxjs';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import {ProductService} from '../product.service';
import { FormGroup, FormControl, Validators} from '@angular/forms';
import 'rxjs/Rx';
import { ProductCategory } from '../model/productCategory';
import {Supplier} from '../model/supplier';
import { DialogService } from '../dialog.service';
import { MatSnackBar } from '@angular/material';
@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  product$: Observable<Product>;;
  categories$:Observable<ProductCategory[]>;
  suppliers$:Observable<Supplier[]>
  add:FormGroup;
  supplierToUpdate:any;
  categoryToUpdate:any;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: ProductService,
    public dialogService:DialogService,
    private snackBar:MatSnackBar
  ) {}
  
  editName;
  editDesc;
  editPrice;
  editWeight;
  ngOnInit() {
      this.suppliers$=this.service.getSuppliers();
      this.categories$=this.service.getProductCategories();
    
        this.add=new FormGroup({
        name:new FormControl(''),
        description:new FormControl(''),
        price:new FormControl(''),
        weight:new FormControl(''),
        });
  }


  addProduct(addValue){
    var toUpdate=new Product(null,addValue.name,addValue.description,addValue.price,addValue.weight,this.categoryToUpdate,addValue.productCategory,addValue.supplier,this.supplierToUpdate,null);
    this.service.addProduct(toUpdate);
    this.router.navigate(['/products'])
    this.openSnackBar("Product added",addValue.name)
    
  }

  updateSelectedForSupplier(event):void{
    this.supplierToUpdate=event.target.value
    console.log(this.supplierToUpdate);
    
  }
  updateSelectedForCategory(event):void{
    this.categoryToUpdate=event.target.value
    console.log(this.categoryToUpdate);
    
  }
  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 2000,
      panelClass : ['background-red']
    });
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
    if(err<1)
      return true;
    return false
  }

}
