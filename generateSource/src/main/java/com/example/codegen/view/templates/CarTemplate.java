package com.example.codegen.view.templates;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

public abstract class CarTemplate implements Template
{
    //The main method to which child classes will add to
    protected MethodSpec.Builder main;

    //The class to which child classes will add to
    protected TypeSpec.Builder car;

    @Override
    public void initialize()
    {
        main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "I am a type of car");

        car = TypeSpec.classBuilder("Car")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
    }

    @Override
    public String build()
    {
        JavaFile javafile = JavaFile
                .builder("com.example.codegen", car.build())
                .build();

        return javafile.toString();
    }

    @Override
    public void construct()
    {
        car.addMethod(main.build());
    }
}
