import { Component, OnInit } from '@angular/core';
import {Product} from '../model/product';
import { Observable } from 'rxjs';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import {ProductService} from '../product.service';
import { switchMap } from 'rxjs/operators';
import 'rxjs/Rx';
@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {
product$: Observable<Product>;

constructor(
  private route: ActivatedRoute,
  private router: Router,
  private service: ProductService
) {}
  ngOnInit() {
    this.product$=this.route.paramMap.pipe(
      switchMap((params:ParamMap)=>
      this.service.getProduct(params.get('id')))
    );
    
  }
  delete(product:Product):void{
    this.product$=this.product$.filter(h=> h!=product);
    this.service.deleteProduct(product.id).subscribe();
  }

}
