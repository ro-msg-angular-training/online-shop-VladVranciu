package ro.msg.learning.shop.service.strategy;

import ro.msg.learning.shop.exception.OrderCannotBeCompletedException;
import ro.msg.learning.shop.exception.ProductNotFoundException;
import ro.msg.learning.shop.model.DTO.OrderInputObject;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderToCompute;
import ro.msg.learning.shop.model.StrategyEnum;
import ro.msg.learning.shop.repository.*;

import java.util.List;

public interface Strategy {
    //Strategy(ProductRepository productRepository,LocationRepository locationRepository,CustomerRepository customerRepository,StockRepository stockRepository,OrderRepository orderRepository);
    Order compute(OrderInputObject orderInputObject,String[] Customer) throws OrderCannotBeCompletedException;
}
