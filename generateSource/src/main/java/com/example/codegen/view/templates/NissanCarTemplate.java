package com.example.codegen.view.templates;

import com.example.codegen.model.Properties;

public class NissanCarTemplate extends JDMCarTemplate
{
    @Override
    public void generate(Properties config)
    {
        //Call The JDM generate
        super.generate(config);

        //Apply Nissan specific code changes
        main.addStatement("$T.out.println($S)", System.class, "More Specifically a Nissan Car");
    }
}
