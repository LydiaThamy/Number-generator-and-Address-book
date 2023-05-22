package vttp.ssf.workshop_revision;

import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
	private static final Logger cliLogger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		// SpringApplication.run(WorkshopRevisionApplication.class, args);

		SpringApplication app = new SpringApplication(App.class);

		String port;

		ApplicationArguments cliArgs = new DefaultApplicationArguments(args);

		// check if option --port is set and use it if available
		if (cliArgs.containsOption("port")) {
			port = cliArgs.getOptionValues("port").get(0);
			cliLogger.info("Port " + port + " set according to command line arguments");

		// check if the env variable PORT is set and use it if available
		} else if (System.getenv("PORT") != null) {
			port = System.getenv("PORT");
			cliLogger.info("Port " + port + " set according to environment variable");
		
		// default to port 3000
		} else {
			port = "3000";
			cliLogger.info("Port " + port + " set according to default");
		}

		// configure port
		app.setDefaultProperties(Collections.singletonMap("server.port", port));

		app.run(args);
	}


}
