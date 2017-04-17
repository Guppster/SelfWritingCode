package com.example.codegen.view.templates;

import com.example.codegen.model.Properties;

public abstract class DomesticCarTemplate extends CarTemplate
{
    @Override
    public void generate(Properties config)
    {
        //Manipulate class to do things general to all Domestic Cars
        main.addStatement("$T.out.println($S)", System.class, "I am a Domestic Car");
    }
}
