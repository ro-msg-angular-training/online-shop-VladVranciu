import { Address } from './address';
import { ProductInputObject } from './productInputObject';

export class OrderInputObject{
    public orderTimeStamp:Date;
    public address:Address;
    public products:ProductInputObject[];

    constructor(public addressc:Address,public productsc:ProductInputObject[]){
        this.orderTimeStamp=new Date();
        this.address=addressc;
        this.products=productsc;
    }
}