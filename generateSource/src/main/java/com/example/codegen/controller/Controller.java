package com.example.codegen.controller;

import com.example.codegen.model.Properties;
import com.example.codegen.view.TemplateComposer;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

public class Controller
{
    public static void main(String[] args)
    {
        //Get the variables from configuration
        Properties config = getTypeSafeConfig();

        //Populate the template with variables
        TemplateComposer composer = new TemplateComposer(config);

        //Test class constructing
        System.out.println(composer.construct());

        //Run the program
    }

    private static Properties getTypeSafeConfig()
    {
        File configFile = new File("/home/gsingh/Documents/Projects/SelfWritingCode/generateSource/config.conf");

        // usual Typesafe Config mechanism to load the file
        Config tsConfig = ConfigFactory.parseFile(configFile).resolve();

        // create instance of the type class. This will perform all validations according to required properties and types:
        return new Properties(tsConfig);
    }

}
