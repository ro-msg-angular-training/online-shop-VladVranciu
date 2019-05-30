package ro.msg.learning.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Data
@Entity
@AllArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    public Location(Integer id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "idaddress",referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "location",orphanRemoval=true,cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Stock> stocks=new ArrayList<>();

    @OneToMany(mappedBy = "location",orphanRemoval=true,cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Revenue> revenues=new ArrayList<>();

    @OneToMany(mappedBy = "shippedFrom",cascade = CascadeType.ALL,orphanRemoval=true)
    @JsonIgnore
    private List<Order> orders=new ArrayList<>();

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                '}';
    }


}
