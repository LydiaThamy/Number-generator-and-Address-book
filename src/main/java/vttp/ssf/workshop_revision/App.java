package vttp.ssf.workshop_revision;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;

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

		ApplicationArguments cliArgs = new DefaultApplicationArguments(args);

		// 1. Setting directory

		// check if option --dataDir is set and exit if it is not
		if (!cliArgs.containsOption("dataDir")) {
			cliLogger.error("Input the directory path 'dataDir' into the command");
			System.exit(0);
		}

		// check if the directory exists and use it
		String dataDir = cliArgs.getOptionValues("dataDir").get(0);
		
		File dir = new File(dataDir);

		if (!dir.exists()) {
			dir.mkdir();
			cliLogger.info("Directory " + dataDir + " created");
		} else {
			cliLogger.info("Directory " + dataDir + " exists");
		}

		// 2. Setting port
		String port;

		// // Load application.properties manually using Properties.load()
        // Properties properties = new Properties();
        // try (InputStream input = App.class.getClassLoader().getResourceAsStream("application.properties")) {
        //     properties.load(input);
        // } catch (IOException ex) {
        //     ex.printStackTrace();
        // }

        // // Get port property
        //  port = properties.getProperty("port");

		// check if option --port is set and use it if available
		if (cliArgs.containsOption("port")) {
			port = cliArgs.getOptionValues("port").get(0);
			cliLogger.info("Port " + port + " set according to command line arguments");

			// check if the env variable PORT is set and use it if available
		} else  if (!cliArgs.containsOption("port") && System.getenv("PORT") != null) {
			port = System.getenv("PORT");
			cliLogger.info("Port " + port + " set according to environment variable");

			// default to port 3000
		} else {
			port = "3000";
			cliLogger.info("Port " + port + " set according to default");
		}

		SpringApplication app = new SpringApplication(App.class);

		// configure port
		app.setDefaultProperties(Collections.singletonMap("server.port", port));

		app.run(args);
	}


}
