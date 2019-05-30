import Big from 'big.js';

export class Product{
    public id: number;
    public name: string;
    public description: string;
    public price: any;
    public weight:any;
    public productCategory:number;
    public productCategoryName:string;
    public supplierName:string;
    public supplierId:number;
    public imgUrl:string;
    
    constructor(public idc:number,public namec:string,public descriptionc:string,public pricec:any,public weightc:any,public productCategoryc:number,public productCategoryNamec:string,public supplierNamec:string, public supplierIdc:number,public imgUrlc:string){
        this.id=idc;
        this.name=namec;
        this.description=descriptionc;
        this.price=pricec;
        this.weight=weightc;
        this.productCategory=productCategoryc;
        this.productCategoryName=productCategoryNamec;
        this.supplierName=supplierNamec;
        this.supplierId=supplierIdc;
        this.imgUrl=imgUrlc;
    }

}