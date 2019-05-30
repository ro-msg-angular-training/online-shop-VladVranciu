import { Component, OnInit } from '@angular/core';
import { Product } from '../model/Product';
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';
import {ProductService} from '../product.service'

@Component({
  selector: 'display-product',
  templateUrl: './display-product.component.html',
  styleUrls: ['./display-product.component.css']
})
export class DisplayProductComponent implements OnInit {
products:Observable<Product[]>;
selectedId:number;
  constructor(private service:ProductService,private route: ActivatedRoute){
  }

  ngOnInit() {
   this.products=this.route.paramMap.pipe(switchMap(params=>{
     this.selectedId=+params.get('id');
     return this.service.getProducts();
   }))
  }

}
