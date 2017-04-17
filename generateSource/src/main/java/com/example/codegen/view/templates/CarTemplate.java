package com.example.codegen.view.templates;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.util.List;

public abstract class CarTemplate implements Template
{
    //The main method to which child classes will add to
    protected MethodSpec.Builder main;

    //A method to list the specs of the car
    protected MethodSpec.Builder specLister;

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

        specLister = MethodSpec.methodBuilder("specsLister")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(List.class, "specs")
                .addStatement("$T.out.println($S)", System.class, "My Details Are:")
                .beginControlFlow("for (String spec : specs)")
                .addStatement("$T.out.println($N)", System.class, "spec")
                .endControlFlow();

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
        car.addMethod(specLister.build());
    }
}
