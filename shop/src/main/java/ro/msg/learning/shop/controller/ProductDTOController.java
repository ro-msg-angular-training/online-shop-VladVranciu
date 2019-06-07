package ro.msg.learning.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.model.CustomUser;
import ro.msg.learning.shop.model.DTO.ProductDTO;
import ro.msg.learning.shop.repository.*;
import ro.msg.learning.shop.service.ProductService;
import ro.msg.learning.shop.service.ProductServiceImpl;
import ro.msg.learning.shop.service.strategy.Strategy;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class ProductDTOController {

    private ProductService productService;

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    private final SupplierRepository supplierRepository;


    @Autowired
    private ProductDTOResourceAssembler assembler;



    public ProductDTOController(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.supplierRepository = supplierRepository;
        this.productService=new ProductServiceImpl(this.productRepository, this.productCategoryRepository, this.supplierRepository);
    }


    @GetMapping("/products/{id}")
    Resource<ProductDTO> findOne(@PathVariable Integer id) {
        ProductDTO productDTO = productService.findById(id);
        return assembler.toResource(productDTO);
    }


    @GetMapping
    ResponseEntity<?>findAll(){
        return new ResponseEntity<>(productService.getProducts(),null,HttpStatus.OK);
    }


    @PostMapping("/products")
    ResponseEntity<?> save(@RequestBody ProductDTO productDTO) throws URISyntaxException {

        return new ResponseEntity<>(productService.createProduct(productDTO),null,HttpStatus.OK);


    }

    @PutMapping("/products/{id}")
    ResponseEntity<?> update(@RequestBody ProductDTO productDTO, @PathVariable Integer id) throws URISyntaxException {

        return new ResponseEntity<>(productService.updateProduct(id,productDTO),null,HttpStatus.OK);
    }


    @DeleteMapping("/products/{id}")
    void delete(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/categories")
    ResponseEntity<?> getCategories(){
        return new ResponseEntity<>(productService.getCategories(),null,HttpStatus.OK);
    }

    @GetMapping("/suppliers")
    ResponseEntity<?> getSuppliers(){
        return new ResponseEntity<>(productService.getSuppliers(),null,HttpStatus.OK);
    }

    @GetMapping("/user")
    ResponseEntity<?> login(CustomUser customUser){
        return new ResponseEntity<>(customUser,null,HttpStatus.OK);
    }


}
