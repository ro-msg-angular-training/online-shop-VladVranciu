package ro.msg.learning.shop;

import org.flywaydb.core.Flyway;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.ShopApplication;
import ro.msg.learning.shop.exception.OrderCannotBeCompletedException;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.StockRepository;

@RestController
public class RestControllerTest {


    @Autowired
    private StockRepository stockRepository;

    private Flyway flyway=Flyway.configure().dataSource("jdbc:h2:mem:test","sa","").load();


    @PostMapping("/populate")
    public ResponseEntity<?> populate(){
        flyway.migrate();
        return new ResponseEntity<>(null,null,HttpStatus.OK);
    }

    @DeleteMapping("/clean")
    public ResponseEntity<?> clean(){
        flyway.clean();
        return new ResponseEntity<>(null,null,HttpStatus.OK);
    }



}
