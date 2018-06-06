package com.igortullio.cursomc;

import com.igortullio.cursomc.domain.*;
import com.igortullio.cursomc.domain.Enums.EstadoPagamento;
import com.igortullio.cursomc.domain.Enums.TipoCliente;
import com.igortullio.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
