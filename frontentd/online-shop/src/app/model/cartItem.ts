import { ProductInputObject } from './productInputObject';

export class CartItem{
    public name:string;

    public productId:number;
    public quantity:number;
    
    constructor(public namec:string,public productIdc:number,public quantityc:number){
        this.name=namec;
        this.productId=productIdc;
        this.quantity=quantityc;
    }
}