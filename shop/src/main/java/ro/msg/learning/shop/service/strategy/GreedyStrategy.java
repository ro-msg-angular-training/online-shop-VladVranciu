package ro.msg.learning.shop.service.strategy;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.client.RestTemplate;
import ro.msg.learning.shop.exception.OrderCannotBeCompletedException;
import ro.msg.learning.shop.exception.ProductNotFoundException;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.model.DTO.OrderInputObject;
import ro.msg.learning.shop.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class GreedyStrategy implements Strategy {

    private ProductRepository productRepository;
    private LocationRepository locationRepository;
    private CustomerRepository customerRepository;
    private StockRepository stockRepository;
    private OrderRepository orderRepository;

    public GreedyStrategy(ProductRepository productRepository, LocationRepository locationRepository, CustomerRepository customerRepository, StockRepository stockRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.locationRepository = locationRepository;
        this.customerRepository = customerRepository;
        this.stockRepository = stockRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Order compute(OrderInputObject orderInputObject, String[] customer) throws OrderCannotBeCompletedException {
        Order order = new Order();
        order.setShippedTo(orderInputObject.getAddress());
        order.setCreatedAt(orderInputObject.getOrderTimeStamp());
        List<GreedyLocation> orderToComputes = computeGreedy(orderInputObject);
        order.setShippedFrom(orderToComputes.get(0).getLocation());
        Customer customer1 = customerRepository.login(customer[0]);
        order.setCustomer(customer1);
        List<OrderDetail> orderDetails = new ArrayList<>();
        orderToComputes.stream().peek(o -> {

            o.getProductInputObjects().stream().peek(p -> {
                Stock stock = stockRepository.findStockByLocationAndProduct(o.getLocation().getId(), p.getProductId());
                stock.setQuantity(stock.getQuantity() - p.getQuantity ());
                stockRepository.save(stock);
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setQuantity(p.getQuantity());
                orderDetail.setProduct(productRepository.findById(p.getProductId()).get());
                orderDetail.setOrder(order);
                orderDetails.add(orderDetail);
            }).collect(Collectors.toList());

        }).collect(Collectors.toList());
        order.setOrderDetails(orderDetails);
        sendSimpleMessage(customer1.getEmail(),"Order confirmation","Your order has been successfully placed\n Order details: "+orderDetails.toString());
        return order;
    }


    public List<GreedyLocation> computeGreedy(OrderInputObject orderInputObject) throws OrderCannotBeCompletedException {
        final String uri = "http://www.mapquestapi.com/directions/v2/routematrix?key=b1UJ5kf3KmSCs855jMD5fY0t5A2ujEsn";

        MapquestInput mapquestInput = new MapquestInput();
        mapquestInput.setLocations(new ArrayList<>());
        mapquestInput.getLocations().add(orderInputObject.getAddress().getStreetAddress() + ", " + orderInputObject.getAddress().getCity() + ", " + orderInputObject.getAddress().getCountry());

        int count=orderInputObject.getProducts().size();
        locationRepository.findAll().stream().peek(p -> {
            mapquestInput.getLocations().add(p.getAddress().getStreetAddress() + ", " + p.getAddress().getCity() + ", " + p.getAddress().getCountry());
        }).collect(Collectors.toList());

        RestTemplate restTemplate = new RestTemplate();
        MapquestOutput mapquestOutput = restTemplate.postForObject(uri, mapquestInput, MapquestOutput.class);

        List<GreedyLocation> greedyLocations = new ArrayList<>();
        mapquestOutput.getDistance().remove(0);
        mapquestInput.getLocations().remove(0);
        while (!mapquestOutput.getDistance().isEmpty()) {
            double max = 9999999d;
            int i = 0;
            for (double d : mapquestOutput.getDistance()) {
                if (d < max) {
                    max = d;
                    i = mapquestOutput.getDistance().indexOf(d);
                }
            }
            String location = mapquestInput.getLocations().get(i);
            String[] aux = location.split(", ");
            Location location1 = locationRepository.findLocationByCityAndCountryAndStreet(aux[1], aux[2], aux[0]);

            GreedyLocation greedyLocation = new GreedyLocation();
            greedyLocation.setProductInputObjects(new ArrayList<>());
            greedyLocation.setLocation(location1);
            for( int j=0;j<orderInputObject.getProducts().size();j++) {
                ProductInputObject p=orderInputObject.getProducts().get(j);
                Product localProduct = productRepository.findById(p.getProductId()).get();
                Stock stock = stockRepository.findStockByLocationAndProduct(location1.getId(), p.getProductId());
                if (stock != null && stock.getQuantity() >= p.getQuantity()) {
                    greedyLocation.getProductInputObjects().add(new ProductInputObject(p.getProductId(), p.getQuantity()));
                    orderInputObject.getProducts().remove(p);
                    j--;
                }
            }
            greedyLocations.add(greedyLocation);
            mapquestInput.getLocations().remove(i);
            mapquestOutput.getDistance().remove(i);

        }
        List<ProductInputObject> verify=new ArrayList<>();
        for(GreedyLocation g:greedyLocations){
            verify.addAll(g.getProductInputObjects());
        }
        if(count!=verify.size()){
            throw new OrderCannotBeCompletedException();
        }
        return greedyLocations;


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
