package shop.mtcoding.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BankApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(BankApplication.class, args);
//		String[] beanDefinitionNames = context.getBeanDefinitionNames();
//		for (String name : beanDefinitionNames) {
//			System.out.println("name = " + name);
//		}
	}

}
