package com.example.codegen.view.templates;

import com.example.codegen.model.Properties;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import madison.mpi.Context;

import javax.lang.model.element.Modifier;
import java.util.HashMap;
import java.util.Map;

public abstract class IxnTemplate implements Template
{
    //The class to which child classes will add to
    TypeSpec.Builder ixnClass;

    //The constructor
    MethodSpec.Builder constructor;

    //The main method to which child classes will add to
    MethodSpec.Builder main;

    @Override
    public void initialize()
    {
        //Initializes the Context
        constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC);

        main = MethodSpec.methodBuilder("execute")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Map.class, "input")
                .returns(void.class);

        ixnClass = TypeSpec.classBuilder("Interaction")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
    }

    public void generate(Properties config)
    {
        ClassName ixnType = ClassName.get("madison.mpi", config.interactionName.replace("Template", ""));

        //Create context fields (not really needed but maybe useful)
        ixnClass.addField(String.class, "host", Modifier.PRIVATE);
        ixnClass.addField(String.class, "user", Modifier.PRIVATE);
        ixnClass.addField(String.class, "pass", Modifier.PRIVATE);
        ixnClass.addField(Integer.class, "port", Modifier.PRIVATE);
        ixnClass.addField(Context.class, "context", Modifier.PRIVATE, Modifier.STATIC);
        ixnClass.addField(ixnType, "ixn", Modifier.PRIVATE, Modifier.STATIC);

        //Add context initialization to the constructor
        constructor
                .addStatement("this.$N = $S", "host", config.context.host)
                .addStatement("this.$N = $S", "user", config.context.user)
                .addStatement("this.$N = $S", "pass", config.context.pass)
                .addStatement("this.$N = $L", "port", config.context.port)
                .addStatement("$1T context = new $1T($2N,$3N,$4N,$5N)", Context.class, "host", "port", "user", "pass")
                .beginControlFlow("if(!context.isConnected())")
                .addStatement("$T.err.println($S)", System.class, "Unable to get a Context")
                .endControlFlow()
                .addStatement("ixn = new $T($N)", ixnType, "context");

        main
                .addStatement("Map<String,String> vars = new $T(input)", HashMap.class);
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
        ixnClass.addMethod(constructor.build());
        ixnClass.addMethod(main.build());
    }
}
