package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Product;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "select p from Product p where name=:name and description=:description and price=:price and weight=:weight")
    Product findProduct(@Param("name") String name, @Param("description") String description, @Param("price") BigDecimal price, @Param("weight") Double weight);

//    @Query(value = "select p from Product p")
//    Optional<Product> findProductInStockForLocation(@Param("location") Location location, @Param("product") Product product, @Param("quantity") Integer quantity);
}
