package com.example.codegen.view.templates;

import com.example.codegen.model.Properties;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import madison.mpi.Context;

import javax.lang.model.element.Modifier;

public abstract class IxnTemplate implements Template
{
    //The class to which child classes will add to
    protected TypeSpec.Builder ixnClass;

    //The constructor
    protected MethodSpec.Builder constructor;

    //The main method to which child classes will add to
    protected MethodSpec.Builder main;

    @Override
    public void initialize()
    {
        //Initializes the Context
        constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC);

        main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args");

        ixnClass = TypeSpec.classBuilder("Interaction")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
    }

    public void generate(Properties config)
    {
        //Create context fields (not really needed but maybe useful)
        ixnClass.addField(String.class, "host");
        ixnClass.addField(String.class, "user");
        ixnClass.addField(String.class, "pass");
        ixnClass.addField(Integer.class, "port");

        //Add context initialization to the constructor
        constructor
                .addStatement("this.$N = $S", "host", config.context.host)
                .addStatement("this.$N = $S", "user", config.context.user)
                .addStatement("this.$N = $S", "pass", config.context.pass)
                .addStatement("this.$N = $S", "port", config.context.port)
                .addStatement("$1T context = new $1T($2N,$3N,$4N,$5N)", Context.class, "host", "user", "pass", "port")
                .beginControlFlow("if(!context.isConnected())")
                .addStatement("$T.err.println($S)", System.class, "Unable to get a Context")
                .endControlFlow();

    }

    @Override
    public String build()
    {
        JavaFile javafile = JavaFile
                .builder("com.example.codegen", ixnClass.build())
                .build();

        return javafile.toString();
    }

    @Override
    public void construct()
    {
        ixnClass.addMethod(main.build());
    }
}
