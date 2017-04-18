package com.example.codegen.view.templates;

import com.example.codegen.model.Properties;

public abstract class IxnMemTemplate extends IxnTemplate
{
    @Override
    public void generate(Properties config)
    {
        super.generate(config);
        //Manipulate class to do things general to all Member Interactions
    }
}
