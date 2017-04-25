package com.example.codegen.view.templates;

import com.example.codegen.model.Properties;

public class IxnMemDeleteTemplate extends IxnMemTemplate
{
    @Override
    public void initialize()
    {
        super.initialize();
    }

    @Override
    public void generate(Properties config)
    {
        //Call the IxnMem generate
        super.generate(config);

        //Execute
        main.addStatement("$T status = ixn.execute(inputRows, outputRows, keyType)", boolean.class);
    }

    @Override
    public void construct()
    {
        super.construct();
    }
}
