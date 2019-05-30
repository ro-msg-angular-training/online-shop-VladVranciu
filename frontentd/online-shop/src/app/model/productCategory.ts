export class ProductCategory{
    public id: number;
    public name: string;
    public description: string;
    constructor(public idc:number,public namec:string,public descriptionc:string){
        this.id=idc;
        this.name=namec;
        this.description=descriptionc;
    }

}