package ro.msg.learning.shop;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.exception.OrderCannotBeCompletedException;
import ro.msg.learning.shop.exception.ProductNotFoundException;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.model.DTO.OrderInputObject;
import ro.msg.learning.shop.repository.*;
import ro.msg.learning.shop.service.OrderService;
import ro.msg.learning.shop.service.strategy.Strategy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest(
        classes = ShopApplication.class)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")

@AutoConfigureMockMvc
@Profile("integrationtest")
public class CreateNewOrderIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Rule
    public ExpectedException expectedException= ExpectedException.none();

    @Autowired
    Strategy strategy;

    @Test
    public void createNewOrderSuccessfully() throws OrderCannotBeCompletedException {
        List<ProductInputObject> productList = new ArrayList<>();
        productList.add(new ProductInputObject(1, 1));
        OrderInputObject orderInputObject = new OrderInputObject(LocalDateTime.now(), new Address("Romania", "Iasi", "Iasi", "address1"), productList);

        Assert.assertNotNull(strategy.compute(orderInputObject));

        Assert.assertNotNull(orderService.createOrder(orderInputObject));
    }

    @Test
    public void failToCreateNewOrderDueToMissingStock()throws OrderCannotBeCompletedException{
        expectedException.expect(OrderCannotBeCompletedException.class);
        List<ProductInputObject> productList = new ArrayList<>();
        productList.add(new ProductInputObject(1, 4000));
        OrderInputObject orderInputObject = new OrderInputObject(LocalDateTime.now(), new Address("Romania", "Iasi", "Iasi", "address1"), productList);

        strategy.compute(orderInputObject);

        orderService.createOrder(orderInputObject);
    }

    @Test
    public void failToCreateNewOrderDueToLackOfProduct() throws ProductNotFoundException, OrderCannotBeCompletedException {
        expectedException.expect(ProductNotFoundException.class);
        List<ProductInputObject> productList = new ArrayList<>();
        productList.add(new ProductInputObject(44, 2));
        OrderInputObject orderInputObject = new OrderInputObject(LocalDateTime.now(), new Address("Romania", "Iasi", "Iasi", "address1"), productList);

        strategy.compute(orderInputObject);

        orderService.createOrder(orderInputObject);
    }
}
