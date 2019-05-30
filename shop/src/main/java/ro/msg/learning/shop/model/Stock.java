package ro.msg.learning.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Stock {
    @Id
    private Integer id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="idproduct",referencedColumnName = "id")
    private Product product;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idlocation",referencedColumnName = "id")
    private Location location;
    private Integer quantity;

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", quantity=" + quantity +
                '}';
    }

    public Stock(Integer id, Product product, Location location, Integer quantity) {
        this.id = id;
        this.product = product;
        this.location = location;
        this.quantity = quantity;
    }
}
