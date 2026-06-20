import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.example.order_saga.sagaorchestrator"
})
public class SagaOrchestratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                SagaOrchestratorApplication.class,
                args
        );
    }
}
