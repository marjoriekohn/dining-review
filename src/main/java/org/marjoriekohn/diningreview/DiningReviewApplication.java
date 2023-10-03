package org.marjoriekohn.diningreview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Dining Review Application. This class is responsible for initializing and running the Spring Boot application.
 *
 */
@SpringBootApplication
public class DiningReviewApplication {
    
    /**
     * Main method which serves as the entry point for the application.
     *
     * @param args command-line arguments passed to the application are not utilized in this application.
     */
    public static void main(String[] args) {
        SpringApplication.run(DiningReviewApplication.class, args);
    }

}
