package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Stock;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock,Integer> {
    //List<Stock> findStockByLocation(Integer id);
    @Query(value = "select * from stock where stock.idLocation=:location and stock.idproduct=:product",nativeQuery = true)
    Stock findStockByLocationAndProduct(@Param("location") Integer location, @Param("product") Integer product);



}
