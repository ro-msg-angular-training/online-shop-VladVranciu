package ro.msg.learning.shop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.config.StrategyConfiguration;
import ro.msg.learning.shop.exception.OrderCannotBeCompletedException;
import ro.msg.learning.shop.exception.ProductNotFoundException;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.model.DTO.OrderInputObject;
import ro.msg.learning.shop.repository.*;

import ro.msg.learning.shop.service.strategy.Strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {


    private ProductRepository productRepository;
    private StockRepository stockRepository;
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private LocationRepository locationRepository;
    private Strategy strategy;
    public OrderServiceImpl(ProductRepository productRepository, StockRepository stockRepository, OrderRepository orderRepository, CustomerRepository customerRepository, LocationRepository locationRepository,Strategy strategy) {
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.locationRepository = locationRepository;
        this.strategy=strategy;
    }


//
//    private StrategyConfiguration strategyConfiguration=new StrategyConfiguration(stockRepository,productRepository,locationRepository,customerRepository,orderRepository);

    @Override
    public Order createOrder(OrderInputObject orderInputObject,String[] customer) throws OrderCannotBeCompletedException, ProductNotFoundException {
        return strategy.compute(orderInputObject,customer);
    }


    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
