package com.example.codegen.view;

/**
 * This class defines what each template must be able to do
 */
public interface Template
{
    //Must be able to construct the class and main method to start off
    void initialize();

    //Must be able to generate the main execution of the method
    String generate();

    String build();
}
