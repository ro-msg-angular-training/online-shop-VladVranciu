export class Address{
    public country:string;
    public city:string;
    public county:string;
    public streetAddress:string;

    constructor(public countryc:string,public cityc:string, public countyc:string,public streetAddressc:string){
        this.country=countryc;
        this.city=cityc;
        this.county=countyc;
        this.streetAddress=streetAddressc;
    }
}