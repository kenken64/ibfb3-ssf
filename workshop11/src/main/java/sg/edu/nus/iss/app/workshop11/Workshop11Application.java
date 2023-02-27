package sg.edu.nus.iss.app.workshop11;

import java.util.Collections;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Workshop11Application {
	private static String portNumber = null;
	private static String DEFAULT_PORTNUM = "3000";
	
	public static void main(String[] args) {
		
		
		for(String argVal: args){
			System.out.println("argVal > " +  argVal);
			if(argVal.contains("--Dport=") || argVal.contains("--port=")){
				System.out.println("contains > ");
				portNumber = argVal.substring(argVal.length() - 4, argVal.length());
				System.out.println("args portNumber > " +  portNumber);
			}
		}

		ApplicationArguments appArgs = new DefaultApplicationArguments(args);
		if (appArgs.containsOption("port")){
			System.out.println("contains");
			portNumber = appArgs.getOptionValues("port").get(0);
		}
		
		if(portNumber  == null){
			portNumber = System.getenv("PORT");
			System.out.println("env portNumber > " +  portNumber);
		}

		if(portNumber == null | portNumber.isBlank()){
			portNumber = DEFAULT_PORTNUM;
		}
		SpringApplication app = 
				new SpringApplication(Workshop11Application.class);
		app.setDefaultProperties(
				Collections.singletonMap("server.port", portNumber));
		app.run(args);
	}

}
