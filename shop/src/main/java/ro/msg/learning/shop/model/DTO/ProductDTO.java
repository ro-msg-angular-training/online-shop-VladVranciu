package ro.msg.learning.shop.model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.model.Stock;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  ProductDTO implements Serializable {

    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Double weight;
    private Integer productCategory;
    private String productCategoryName;
    private String supplierName;
    private Integer supplierId;
    private String imgUrl;

    @JsonIgnore
    private List<Stock> stocks;
    @JsonIgnore
    private List<OrderDetail> orderDetails;

}
