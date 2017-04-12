package com.example.codegen.controller;

import com.example.codegen.model.Properties;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;

import java.io.File;

public class Controller
{
    public static void main(String[] args)
    {
        //Get the variables from configuration
        Properties config = getTypeSafeConfig();

        //Populate the template with variables


        //Run the program
    }

    private static Properties getTypeSafeConfig()
    {
        File configFile = new File("/home/gsingh/Documents/Projects/config.conf");

        // usual Typesafe Config mechanism to load the file
        Config tsConfig = ConfigFactory.parseFile(configFile).resolve();

        // create instance of the type class. This will
        // perform all validations according to required properties and types:
        Properties config = new Properties(tsConfig);

        int age = config.Person.age;
        String name = config.Person.name;
        String email = config.Person.email;
        String location = config.Person.location;

        System.out.println("location = " + location);
        System.out.println("email = " + email);
        System.out.println("name = " + name);
        System.out.println("age = " + age);

        System.out.println("\n*** Typesafe rendering of input Config object: *** ");
        ConfigRenderOptions options = ConfigRenderOptions.defaults()
                .setFormatted(true)
                .setComments(true)
                .setOriginComments(false);

        System.out.println(tsConfig.root().render(options));

        return config;
    }

}
