package ro.msg.learning.shop.model.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierDTO implements Serializable {
    private Integer id;
    private String name;
}
