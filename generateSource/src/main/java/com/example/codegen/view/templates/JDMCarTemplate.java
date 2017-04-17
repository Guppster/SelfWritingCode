package com.example.codegen.view.templates;

import com.example.codegen.model.Properties;

public abstract class JDMCarTemplate extends CarTemplate
{
    @Override
    public void generate(Properties config)
    {
        //Manipulate class to do things general to all JDM Cars
        main.addStatement("$T.out.println($S)", System.class, "I am a JDM Car");
    }
}
