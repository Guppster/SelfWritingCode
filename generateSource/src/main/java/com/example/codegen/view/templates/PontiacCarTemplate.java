package com.example.codegen.view.templates;

import com.example.codegen.model.Properties;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;
import java.util.Arrays;
import java.util.List;

public class PontiacCarTemplate extends DomesticCarTemplate
{
    private MethodSpec pontiacHelperMethod;

    @Override
    public void initialize()
    {
        super.initialize();

        pontiacHelperMethod = MethodSpec.methodBuilder("pontiacHelperMethod")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(List.class, "specs")
                .addStatement("$T.out.println($S)", System.class, "My Details Are:")
                .beginControlFlow("for (String spec : specs)")
                .addStatement("$T.out.println($N)", System.class, "spec")
                .endControlFlow()
                .build();
    }

    @Override
    public void generate(Properties config)
    {
        //Call the domestic generate
        super.generate(config);

        //Apply Pontiac specific code changes
        main.addStatement("$T.out.println($S)", System.class, "More Specifically a Pontiac Car");
        main.addStatement("$N(Arrays.asList($S, $S))", pontiacHelperMethod, config.Car.name, config.Car.year);
    }

    @Override
    public void construct()
    {
        super.construct();
        car.addMethod(pontiacHelperMethod);
    }
}
