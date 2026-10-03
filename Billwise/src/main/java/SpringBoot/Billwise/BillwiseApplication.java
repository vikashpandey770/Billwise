package SpringBoot.Billwise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BillwiseApplication {
	public static void main(String[] args) {
		SpringApplication.run(BillwiseApplication.class, args);
		System.out.println("bill project");
	}
}