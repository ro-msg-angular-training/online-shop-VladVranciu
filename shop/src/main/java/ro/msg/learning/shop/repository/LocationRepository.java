package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.Address;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Product;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    @Query(value = "select l from Location l inner join Stock s on l.id = s.location and s.quantity>= :quantity and s.product=:product")
    List<Location> findSingleLocation(@Param("quantity") Integer quantity, @Param("product") Product product);

    @Query(value = "select l from Location l inner join Stock s on l.id = s.location and s.quantity>= :quantity and s.product=:product order by s.quantity desc")
    List<Location> findMostAbundantLocation(@Param("quantity") Integer quantity, @Param("product") Product product);

//    @Query(value="select l from Location l where l.address=:address")
//    List<Location> findLocationByAddress(@Param("address")Address address);

    //@Query(value = "select l from Location l inner join Address a on l.id=a.location and a.city=:city and a.country=:country and a.streetAddress=:street")
//    @Query(value = "select l from Location l where l.address.city:=city and l.address.country=:country and l.address.streetAddress=:street")

//    @Query(value = "select l from Location l inner join Address a on a.city=:city and a.country=:country and a.streetAddress=:street")
    @Query(value = "select l from Location l inner join l.address a on a.city=:city and a.country=:country and a.streetAddress=:street")
    Location findLocationByCityAndCountryAndStreet(@Param("city") String city, @Param("country") String country, @Param("street") String street);


//    @Query(value = "select l from Location l where l.address=:address")
//    List<Location> findLocationByAddress(@Param("address") Address address);
}
