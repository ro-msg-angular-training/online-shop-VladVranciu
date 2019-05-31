import { TestBed } from '@angular/core/testing';

import { ProductsDetailResolverService } from './products-detail-resolver.service';

describe('ProductsDetailResolverService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ProductsDetailResolverService = TestBed.get(ProductsDetailResolverService);
    expect(service).toBeTruthy();
  });
});
