import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.example.order_saga.orchestrator"})
@EnableJpaRepositories(basePackages = "com.example.order_saga.orchestrator")
@EntityScan(basePackages = "com.example.order_saga.orchestrator")
public class SagaOrchestratorApplication {

  public static void main(String[] args) {
    SpringApplication.run(SagaOrchestratorApplication.class, args);
  }
}
