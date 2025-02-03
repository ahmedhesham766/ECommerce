package com.ecommerce.configServer.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    public AppConfig() {
        // Load environment variables from .env
        Dotenv dotenv = Dotenv.configure().load();

        // Retrieve and set system properties (if needed)
        System.setProperty("GITHUB_USERNAME", dotenv.get("GITHUB_USERNAME"));
        System.setProperty("GITHUB_TOKEN", dotenv.get("GITHUB_TOKEN"));

        // Print the values for debugging (optional)
        System.out.println("GitHub Username: " + dotenv.get("GITHUB_USERNAME"));
        System.out.println("GitHub Token: " + dotenv.get("GITHUB_TOKEN"));
    }
}