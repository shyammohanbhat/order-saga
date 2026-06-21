import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.example.order_saga.shipment"})
@EnableJpaRepositories(basePackages = "com.example.order_saga.shipment")
@EntityScan(basePackages = "com.example.order_saga.shipment")
public class ShipmentServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShipmentServiceApplication.class, args);
  }
}
