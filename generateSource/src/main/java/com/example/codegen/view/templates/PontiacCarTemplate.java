package com.example.codegen.view.templates;

import com.example.codegen.model.Properties;

public class PontiacCarTemplate extends DomesticCarTemplate
{
    @Override
    public void initialize()
    {
        super.initialize();
    }

    @Override
    public void generate(Properties config)
    {
        //Call the domestic generate
        super.generate(config);

        //Apply Pontiac specific code changes
        main.addStatement("$T.out.println($S)", System.class, "More Specifically a Pontiac Car");
        main.addStatement("$N(Arrays.asList($S, $S))", specLister.build(), config.Car.name, config.Car.year);
    }

    @Override
    public void construct()
    {
        super.construct();
    }
}
