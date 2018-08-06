package com.igortullio.cursomc.config;

import com.igortullio.cursomc.services.EmailService;
import com.igortullio.cursomc.services.SmtpEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("prod")
public class ProdConfig {

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        return true;
    }

    @Bean
    public EmailService emailService(){
        return new SmtpEmailService();
    }

}
