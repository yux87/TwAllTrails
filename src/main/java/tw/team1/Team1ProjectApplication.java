package tw.team1;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Team1ProjectApplication {

	public static void main(String[] args) {
//		String encodedPassword = new BCryptPasswordEncoder().encode("123456");
//		System.out.println(encodedPassword);

		// Load .env file
		Dotenv dotenv = Dotenv.configure().load();
		// Set environment variables programmatically
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(Team1ProjectApplication.class, args);
	}

}
