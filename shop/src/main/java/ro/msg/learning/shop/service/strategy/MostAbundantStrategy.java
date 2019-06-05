package ro.msg.learning.shop.service.strategy;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import ro.msg.learning.shop.exception.OrderCannotBeCompletedException;
import ro.msg.learning.shop.exception.ProductNotFoundException;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.model.DTO.OrderInputObject;
import ro.msg.learning.shop.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ro.msg.learning.shop.model.StrategyEnum.MOSTABUNDANT;


@NoArgsConstructor
@AllArgsConstructor
public class MostAbundantStrategy implements Strategy {

    private ProductRepository productRepository;
    private LocationRepository locationRepository;
    private CustomerRepository customerRepository;
    private StockRepository stockRepository;
    private OrderRepository orderRepository;

    public MostAbundantStrategy(ProductRepository productRepository, LocationRepository locationRepository, CustomerRepository customerRepository, StockRepository stockRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.locationRepository = locationRepository;
        this.customerRepository = customerRepository;
        this.stockRepository = stockRepository;
        this.orderRepository = orderRepository;
    }

    public Order compute(OrderInputObject orderInputObject, String[] customer)throws OrderCannotBeCompletedException{
        Order order = new Order();
        order.setShippedTo(orderInputObject.getAddress());
        order.setCreatedAt(orderInputObject.getOrderTimeStamp());
        List<OrderToCompute> orderToComputes=computeMostAbundant(orderInputObject);
        order.setShippedFrom(orderToComputes.get(0).getLocation());
        Customer customer1=customerRepository.login(customer[0]);
        order.setCustomer(customer1);
        List<OrderDetail> orderDetails=new ArrayList<>();
        List<Location> locations = new ArrayList<>();
        orderToComputes.stream().peek(o ->
                locations.add(o.getLocation())
        ).collect(Collectors.toList());
        orderToComputes.stream().peek(o->{
            if(!locations.isEmpty()){
                Location location=o.getLocation();
                List<Product> products=new ArrayList<>();
                List<Integer> quantities=new ArrayList<>();
                locations.stream().peek(l->{
                    if(l.equals(location)){
                        if(!products.contains(o.getProduct())){
                            products.add(o.getProduct());
                            quantities.add(o.getQuantity());
                        }
                    }
                }).collect(Collectors.toList());
                locations.stream().filter(l->!l.equals(location)).collect(Collectors.toList());
                products.stream().peek(product -> {
                    OrderDetail orderDetail=new OrderDetail();
                    orderDetail.setProduct(product);
                    orderDetail.setQuantity(quantities.get(products.indexOf(product)));
                    Stock stock=stockRepository.findStockByLocationAndProduct(location.getId(), product.getId());
                    stock.setQuantity(stock.getQuantity()-orderDetail.getQuantity());
                    stockRepository.save(stock);
                    orderDetail.setOrder(order);
                    orderDetails.add(orderDetail);
                }).collect(Collectors.toList());

            }

        }).collect(Collectors.toList());
        order.setOrderDetails(orderDetails);
        orderRepository.save(order);
        sendSimpleMessage(customer1.getEmail(),"Order confirmation","Your order has been successfully placed\n Order details: "+orderDetails.toString());
        return order;

    }

    public List<OrderToCompute> computeMostAbundant(OrderInputObject orderInputObject) throws OrderCannotBeCompletedException {
        List<OrderToCompute> orderToComputes = new ArrayList<>();
        List<Location> allLocations = new ArrayList<>();
        orderInputObject.getProducts().stream().peek(p -> {
            Product product1=null;
            if(productRepository.findById(p.getProductId()).isPresent()) {
                product1 = productRepository.findById(p.getProductId()).get();
            } else
                throw new ProductNotFoundException(p.getProductId());
            List<Location> locations= locationRepository.findMostAbundantLocation(p.getQuantity(),product1);
            if(!locations.isEmpty())
                allLocations.add(locations.get(0));
        }).collect(Collectors.toList());

        if (allLocations.size() != orderInputObject.getProducts().size() || allLocations.contains(null))
            throw new OrderCannotBeCompletedException();

        allLocations.stream().peek(p -> {
            orderToComputes.add(new OrderToCompute(p, productRepository.findById(orderInputObject.getProducts().get(allLocations.indexOf(p)).getProductId()).get(), orderInputObject.getProducts().get(allLocations.indexOf(p)).getQuantity()));
        }).collect(Collectors.toList());

        return orderToComputes;
    }
    @Autowired
    public JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
