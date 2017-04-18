package com.example.codegen.view.templates;

import com.example.codegen.model.Properties;

/**
 * This class defines what each template must be able to do
 */
public interface Template
{
    //Must be able to construct the class and the required methods
    void initialize();

    //Must be able to generate the main execution of the method
    void generate(Properties config);

    //Constructs the class by adding methods to the class
    void construct();

    //Builds the final class
    String build();
}
