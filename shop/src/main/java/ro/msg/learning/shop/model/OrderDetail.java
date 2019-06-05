package ro.msg.learning.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "ORDERDETAIL")
@Data
@NoArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="idorder",referencedColumnName= "id")
    @JsonIgnore
    private Order order;
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name="idproduct",referencedColumnName = "id")
    private Product product;
    private Integer quantity;

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                "product="+product+
                ", quantity=" + quantity +
                '}';
    }
}
