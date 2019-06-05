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

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private Strategy strategy;

    private StrategyConfiguration strategyConfiguration=new StrategyConfiguration(stockRepository,productRepository,locationRepository,customerRepository,orderRepository);

    private static final int idCustomer = 1;


    @Override
    public Order createOrder(OrderInputObject orderInputObject,String[] customer) throws OrderCannotBeCompletedException, ProductNotFoundException {
        return strategy.compute(orderInputObject,customer);
    }


    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
