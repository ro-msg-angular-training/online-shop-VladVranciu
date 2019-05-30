package ro.msg.learning.shop.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductCategoryDTO implements Serializable {
    private Integer id;
    private String name;
    private String description;
}
