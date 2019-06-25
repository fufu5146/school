package com.furkan.School.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

@RestController
@SpringBootApplication
public class SchoolController {

    private final static String DB_URL = "jdbc:mysql://localhost:3306/wild_db_quest?serverTimezone=GMT";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "segatower51";

    @GetMapping("/api/schools")
    public List<Wizard> getWizards(@RequestParam(defaultValue = "%") String country) {
        try(
            Connection connection = DriverManager.getConnection(
                DB_URL, DB_USER, DB_PASSWORD
            );
            PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM school WHERE country like ?"
            );
        ){
            statement.setString(1,country);
            
            try(
            ResultSet resultSet = statement.executeQuery();
        ) {
            List<Wizard> wizards = new ArrayList<Wizard>();

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String countryy = resultSet.getString("country");
                int capacity = resultSet.getInt("capacity");
                wizards.add(new Wizard(id, name,capacity,countryy));
            }

            return wizards;
        }
    }catch (SQLException e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "", e
            );
        }
    }
    }
    class Wizard {

        private int id;
        private String name;
        private String country;
        private int capacity;

        public Wizard(int id, String name,int capacity, String country) {
            this.id = id;
            this.name = name;
            this.capacity= capacity;
            this.country=country;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
        public int getCapacity() {
            return capacity;
        }
        public String getCountry() {
            return country;
        }
        
    }

