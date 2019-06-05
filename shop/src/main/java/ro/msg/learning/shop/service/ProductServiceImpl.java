package ro.msg.learning.shop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.ProductNotFoundException;
import ro.msg.learning.shop.model.DTO.ProductCategoryDTO;
import ro.msg.learning.shop.model.DTO.ProductDTO;
import ro.msg.learning.shop.model.DTO.SupplierDTO;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.model.Supplier;
import ro.msg.learning.shop.repository.ProductCategoryRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.SupplierRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ProductCategoryRepository productCategoryRepository;
    private SupplierRepository supplierRepository;
    private SwitchProduct switchProduct;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public ProductServiceImpl(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.supplierRepository = supplierRepository;
        this.bCryptPasswordEncoder=new BCryptPasswordEncoder();
        this.switchProduct=new SwitchProduct(productCategoryRepository,supplierRepository,bCryptPasswordEncoder);
    }




    @Override
    public ProductDTO createProduct(ProductDTO product) {
        Product product1=switchProduct.fromProductDTOtoProduct(product);
        productRepository.save(product1);
        return switchProduct.fromProductToProductDTO(productRepository.findProduct(product1.getName(),product1.getDescription(),product1.getPrice(),product1.getWeight()));
    }

    @Override
    public ProductDTO updateProduct(Integer id, ProductDTO product) {
        Optional<Product> aux = productRepository.findById(id).map(
                product1 -> {
                    product1=switchProduct.fromProductDTOtoProduct(product);
                    //product1.setId(id);
                    return productRepository.save(product1);

                });
        return product;
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
        log.info("Product with id " + id + " deleted successfully");
    }

    @Override
    public ProductDTO findById(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return switchProduct.fromProductToProductDTO(product);
    }

    @Override
    public List<ProductDTO> getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
            productDTOS.add(switchProduct.fromProductToProductDTO(product));
        }
        log.info("Product list is being returned");
        return productDTOS;
    }

    @Override
    public List<ProductCategoryDTO> getCategories() {
        List<ProductCategory> productCategories=productCategoryRepository.findAll();
        List<ProductCategoryDTO> productCategoryDTOS=new ArrayList<>();
        productCategories.forEach(p->productCategoryDTOS.add(switchProduct.fromProductCategoryToProductCategoryDTO(p)));
        return productCategoryDTOS;
    }

    @Override
    public List<SupplierDTO> getSuppliers() {
        List<Supplier> suppliers=supplierRepository.findAll();
        List<SupplierDTO> supplierDTOS=new ArrayList<>();
        suppliers.forEach(s->supplierDTOS.add(switchProduct.fromSupplierToSupplierDTO(s)));
        log.info("Supplier list is being returned");
        return supplierDTOS;
    }

}
