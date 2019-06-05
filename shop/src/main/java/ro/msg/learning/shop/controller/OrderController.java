package ro.msg.learning.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.exception.OrderCannotBeCompletedException;
import ro.msg.learning.shop.exception.ProductNotFoundException;
import ro.msg.learning.shop.model.CustomUser;
import ro.msg.learning.shop.model.DTO.CustomerDTO;
import ro.msg.learning.shop.model.DTO.OrderInputObject;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.StockRepository;
//import ro.msg.learning.shop.service.CustomUserService;
import ro.msg.learning.shop.service.CustomerService;
import ro.msg.learning.shop.service.OrderService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@RestController
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/orders")
    ResponseEntity<?> save(@RequestBody OrderInputObject orderInputObject, @RequestHeader("authorization") String headers){

        String user=headers.split(" ")[1];
        String aux=new String(Base64.getUrlDecoder().decode(user));
        String[] customer=aux.split(":");
        Order order;
         try {
            order = orderService.createOrder(orderInputObject,customer);
        }catch(ProductNotFoundException | OrderCannotBeCompletedException e){
            return new ResponseEntity<>("{\"message\":\""+e.getMessage()+"\"}",null,HttpStatus.OK);
        }
            return new ResponseEntity<>(order,null, HttpStatus.OK);

    }
    @PostMapping("/login")
    public boolean login(@RequestBody CustomUser user) {
        return true;
    }

    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody CustomerDTO customerDTO){
        return new ResponseEntity<>(customerService.register(customerDTO),null,HttpStatus.OK);
    }
//    @GetMapping("/customer")
//    ResponseEntity<?> getCustomer(){
//        return new ResponseEntity<>(customerRepository.login("root"),null,HttpStatus.OK);
//    }

//    @GetMapping("/user")
//    ResponseEntity<?> getUser(){
//        return new ResponseEntity<>(userDetailsService.login("root","123"),null,HttpStatus.OK);
//    }
    @GetMapping("/stocks")
    ResponseEntity<?> getStocks(){
        return new ResponseEntity<>(stockRepository.findAll(),null,HttpStatus.OK);
    }
}
