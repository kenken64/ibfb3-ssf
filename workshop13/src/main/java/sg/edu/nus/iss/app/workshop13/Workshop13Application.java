package sg.edu.nus.iss.app.workshop13;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static sg.edu.nus.iss.app.workshop13.util.IOUtil.*;

@SpringBootApplication
public class Workshop13Application {
	private static final Logger logger = LoggerFactory.getLogger(Workshop13Application.class);

	public static void main(String[] args) {
		SpringApplication app = 
				new SpringApplication(Workshop13Application.class);
		DefaultApplicationArguments appArgs = new DefaultApplicationArguments(args);
		List<String> opsVal = appArgs.getOptionValues("dataDir");
		System.out.println("before createDir");
		if(null != opsVal){
			logger.info("" + (String)opsVal.get(0));
			System.out.println("inside create Dir");
			createDir((String)opsVal.get(0));
		}else{
			System.out.println("exit");
			System.exit(1);
		}

		app.run(args);
	}

}
