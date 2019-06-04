export class ProductInputObject{
    public productId:number;
    public quantity:number;
    
    constructor(public productIdc:number,public quantityc:number){
        this.productId=productIdc;
        this.quantity=quantityc;
    }
}