package com.example.javapoet;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.IOException;

/**
 */
public class HelloWorld
{
    public static void main(String[] args)
    {
        MethodSpec main = MethodSpec.methodBuilder("main")
                .returns(void.class)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorldGenerated")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(main)
                .build();

        JavaFile javaFile = JavaFile.builder("com.example.javapoet", helloWorld)
                .build();

        try
        {
            javaFile.writeTo(System.out);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
