package ro.msg.learning.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Entity
@Data
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Double weight;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "idcategory", referencedColumnName = "id")
    private ProductCategory productCategory;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "idsupplier", referencedColumnName = "id")
    private Supplier supplier;

    @Column(name = "IMAGEURL")
    private String imgUrl;

    @OneToMany(mappedBy = "product", orphanRemoval = true,cascade = CascadeType.MERGE)
            @JsonIgnore
    List<Stock> stocks = new ArrayList<>();

    @OneToMany(mappedBy = "product", orphanRemoval = true,cascade = CascadeType.ALL)
            @JsonIgnore
    List<OrderDetail> orderDetails = new ArrayList<>();

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", productCategory=" + productCategory +
                ", supplier=" + supplier +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }

    public Product(String name, String description, BigDecimal price, Double weight, ProductCategory productCategory, Supplier supplier) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.productCategory = productCategory;
        this.supplier = supplier;
    }

    public Product(Integer id, String name, String description, BigDecimal price, Double weight, ProductCategory productCategory, Supplier supplier) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.productCategory = productCategory;
        this.supplier = supplier;
    }
}
