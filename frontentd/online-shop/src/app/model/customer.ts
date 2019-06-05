export class Customer{
    private firstName:string;
    private lastName:string;
    private username:string;
    private password:string;
    private email:string;
    
    constructor(public firstnamec:string,public lastnamec:string,public usernamec:string,public passwordc:string,public emailc:string){
        this.firstName=firstnamec;
        this.lastName=lastnamec;
        this.username=usernamec;
        this.password=passwordc;
        this.email=emailc;
    }
}