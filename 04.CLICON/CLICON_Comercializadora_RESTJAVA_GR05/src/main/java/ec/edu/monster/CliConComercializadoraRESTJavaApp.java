/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster;

import ec.edu.monster.controllers.CliConComercializadoraController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author joela
 */
@SpringBootApplication
public class CliConComercializadoraRESTJavaApp implements CommandLineRunner {

    public static void main(String[] args) {
        // Set encoding to UTF-8 for proper display of accents
        System.setProperty("file.encoding", "UTF-8");
        SpringApplication.run(CliConComercializadoraRESTJavaApp.class, args);
    }

    @Autowired
    private CliConComercializadoraController controller;

    @Override
    public void run(String... args) throws Exception {
        controller.iniciarAplicacion();
    }
}
