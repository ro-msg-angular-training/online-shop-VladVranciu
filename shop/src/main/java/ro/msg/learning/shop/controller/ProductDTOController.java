package ro.msg.learning.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.model.DTO.ProductDTO;
import ro.msg.learning.shop.repository.*;
import ro.msg.learning.shop.service.ProductService;
import ro.msg.learning.shop.service.ProductServiceImpl;

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
    private StockRepository stockRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private AddressRepository addressRepository;

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


//    @GetMapping("/products")
//    Resources<Resource<ProductDTO>> findAll() {
//        List<Resource<ProductDTO>> list = productService.getProducts().stream()
//                .map(assembler::toResource)
//                .collect(Collectors.toList());
//
//        return new Resources<>(list,
//                linkTo(methodOn(ProductDTOController.class).findAll()).withSelfRel());
//    }

    @GetMapping
    ResponseEntity<?>findAll(){
        return new ResponseEntity<>(productService.getProducts(),null,HttpStatus.OK);
    }


    @PostMapping("/products")
    ResponseEntity<?> save(@RequestBody ProductDTO productDTO) throws URISyntaxException {
//        Resource<ProductDTO> resource = assembler.toResource(productService.createProduct(productDTO));
//        //return ResponseEntity.created(new URI(resource.));
//        return new ResponseEntity<>(resource,null, HttpStatus.OK);
        return new ResponseEntity<>(productService.createProduct(productDTO),null,HttpStatus.OK);


    }

    @PutMapping("/products/{id}")
    ResponseEntity<?> update(@RequestBody ProductDTO productDTO, @PathVariable Integer id) throws URISyntaxException {


        Resource<ProductDTO> resource = assembler.toResource(productService.updateProduct(id,productDTO));

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }


    @DeleteMapping("/products/{id}")
    void delete(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/pc")
    ResponseEntity<?> getpc(){
        return new ResponseEntity<>(productCategoryRepository.findById(1).get(),null,HttpStatus.OK);
    }

}
