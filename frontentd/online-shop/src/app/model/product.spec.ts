import { Product } from './product';

describe('Product', () => {
  it('should create an instance', () => {
    expect(new Product(1,"Product1","Desc1",12,150,1,"Supplier1",1,"")).toBeTruthy();
  });
});
