package ro.msg.learning.shop.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.DTO.ProductDTO;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.model.Supplier;
import ro.msg.learning.shop.repository.ProductCategoryRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.SupplierRepository;

@AllArgsConstructor
public class SwitchProduct {


    private ProductCategoryRepository productCategoryRepository;

    private SupplierRepository supplierRepository;


    public Product fromProductDTOtoProduct(ProductDTO product){


        Product product1 = new Product();
        ProductCategory productCategory = productCategoryRepository.findById(product.getProductCategory()).get();
        product1.setProductCategory(productCategory);
        product1.setDescription(product.getDescription());
        product1.setImgUrl(product.getImgUrl());
        product1.setName(product.getName());
        product1.setOrderDetails(product.getOrderDetails());
        product1.setPrice(product.getPrice());
        product1.setStocks(product.getStocks());
        Supplier supplier = supplierRepository.findById(product.getSupplierId()).get();
        product1.setSupplier(supplier);
        product1.setWeight(product.getWeight());
        return product1;
    }

    public ProductDTO fromProductToProductDTO(Product product){
        ProductDTO product1 = new ProductDTO();
        product1.setId(product.getId());
        product1.setProductCategory(product.getProductCategory().getId());
        product1.setProductCategoryName(product.getProductCategory().getName());
        product1.setDescription(product.getDescription());
        product1.setImgUrl(product.getImgUrl());
        product1.setName(product.getName());
        product1.setPrice(product.getPrice());;
        product1.setSupplierId(product.getSupplier().getId());
        product1.setSupplierName(product.getSupplier().getName());
        product1.setWeight(product.getWeight());
        return product1;

    }
}
