package ro.msg.learning.shop;

import org.aspectj.weaver.ast.Or;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ro.msg.learning.shop.exception.OrderCannotBeCompletedException;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.model.DTO.OrderInputObject;
import ro.msg.learning.shop.repository.*;
import ro.msg.learning.shop.service.OrderService;
import ro.msg.learning.shop.service.OrderServiceImpl;
import ro.msg.learning.shop.service.strategy.MostAbundantStrategy;
import ro.msg.learning.shop.service.strategy.SingleLocationStrategy;
import ro.msg.learning.shop.service.strategy.Strategy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


public class StrategyTest {


    @Mock
    private ProductRepository productRepository;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private StockRepository stockRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private OrderRepository orderRepository;

    private Strategy singleLocationStrategy;
    private Strategy mostAbundantStrategy;

    @Rule
    public ExpectedException expectedException= ExpectedException.none();



    @Mock
    List<Location> locations;
    @Mock
    List<Product> products;
    @Mock
    List<Stock> stocks;

    @Before
    public void initTest() {
        Location loc1 = new Location(1, "location1", new Address("la", "la", "la", "la"));
        Location loc2 = new Location(2, "location2", new Address("la2", "la2", "la2", "la2"));

        ProductCategory productCategory=new ProductCategory(1,"papuci","smc");
        Supplier supplier=new Supplier(1,"Supplier1");
        Product product1=new Product(1,"Vans","for feet",new BigDecimal(150),50d,productCategory,supplier);
        Product product2=new Product(2,"Vans2","for feet2",new BigDecimal(250),20d,productCategory,supplier);

        locations = Arrays.asList(loc1, loc2);
        when(locationRepository.findAll()).thenReturn(locations);

        when(locationRepository.findSingleLocation(20,product1)).thenReturn(Arrays.asList(loc1));
        when(locationRepository.findSingleLocation(100000,product1)).thenReturn(null);

        when(locationRepository.findMostAbundantLocation(999999,product1)).thenReturn(null);
        when(locationRepository.findMostAbundantLocation(1,product2)).thenReturn(Arrays.asList(loc1,loc2));
        when(locationRepository.findMostAbundantLocation(20,product1)).thenReturn(Arrays.asList(loc1,loc2));

        when(locationRepository.findMostAbundantLocation(100,product2)).thenReturn(Arrays.asList(loc1,loc2));
        when(locationRepository.findMostAbundantLocation(100,product1)).thenReturn(Arrays.asList(loc2,loc1));




        products=Arrays.asList(product1,product2);
        when(productRepository.findAll()).thenReturn(products);
        when(productRepository.findById(1)).thenReturn(java.util.Optional.of(product1));
        when(productRepository.findById(2)).thenReturn(java.util.Optional.of(product2));

        Customer customer=new Customer(1,"lasname","firstname","username","password","mail@mail.com");
        when(customerRepository.findById(anyInt())).thenReturn(java.util.Optional.of(customer));

        Stock stock1=new Stock(1,product1,loc1,99999);
        Stock stock2=new Stock(2,product2,loc1,22);
        Stock stock3=new Stock(3,product1,loc2,9999);
        Stock stock4=new Stock(4,product2,loc2,10000);
        stocks=Arrays.asList(stock1,stock2,stock3,stock4);
        when(stockRepository.findAll()).thenReturn(stocks);
        when(stockRepository.save(any(Stock.class))).thenReturn(null);

        when(orderRepository.save(any(Order.class))).thenReturn(null);

        singleLocationStrategy=new SingleLocationStrategy(productRepository,locationRepository,customerRepository,stockRepository,orderRepository);
        mostAbundantStrategy=new MostAbundantStrategy(productRepository,locationRepository,customerRepository,stockRepository,orderRepository);
    }

    @Test
    public void singleLocationWorksFine() throws OrderCannotBeCompletedException {
        List<ProductInputObject> productList = new ArrayList<>();
        productList.add(new ProductInputObject(1, 20));
        OrderInputObject orderInputObject = new OrderInputObject(LocalDateTime.now(), new Address("Romania", "Iasi", "Iasi", "address1"), productList);

        Assert.assertNotNull(singleLocationStrategy.compute(orderInputObject));


    }
    @Test
    public void singleLocationThrowsException()throws OrderCannotBeCompletedException{
        List<ProductInputObject> productList = new ArrayList<>();
        productList.add(new ProductInputObject(1, 100000));
        OrderInputObject orderInputObject = new OrderInputObject(LocalDateTime.now(), new Address("Romania", "Iasi", "Iasi", "address1"), productList);

        expectedException.expect(OrderCannotBeCompletedException.class);
        singleLocationStrategy.compute(orderInputObject);
    }

    @Test
    public void mostAbundantStrategyWorksFine() throws OrderCannotBeCompletedException {
        List<ProductInputObject> productList = new ArrayList<>();
        productList.add(new ProductInputObject(1, 20));
        productList.add(new ProductInputObject(2,1));
        OrderInputObject orderInputObject = new OrderInputObject(LocalDateTime.now(), new Address("Romania", "Iasi", "Iasi", "address1"), productList);

        Assert.assertNotNull(mostAbundantStrategy.compute(orderInputObject));

    }

    @Test
    public void mostAbundantStrategyWorksFine2()throws OrderCannotBeCompletedException{
        List<ProductInputObject> productList = new ArrayList<>();
        productList.add(new ProductInputObject(1, 100));
        productList.add(new ProductInputObject(2,100));
        OrderInputObject orderInputObject = new OrderInputObject(LocalDateTime.now(), new Address("Romania", "Iasi", "Iasi", "address1"), productList);

        Assert.assertNotNull(mostAbundantStrategy.compute(orderInputObject));
    }
    @Test
    public void mostAbundantStrategyThrowsException()throws OrderCannotBeCompletedException{
        List<ProductInputObject> productList = new ArrayList<>();
        productList.add(new ProductInputObject(1, 999999));
        productList.add(new ProductInputObject(2,1));
        OrderInputObject orderInputObject = new OrderInputObject(LocalDateTime.now(), new Address("Romania", "Iasi", "Iasi", "address1"), productList);

        expectedException.expect(OrderCannotBeCompletedException.class);
        mostAbundantStrategy.compute(orderInputObject);
    }
}

