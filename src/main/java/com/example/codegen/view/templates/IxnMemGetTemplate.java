package com.example.codegen.view.templates;

import com.example.codegen.model.Properties;
import madison.mpi.KeyType;

public class IxnMemGetTemplate extends IxnMemTemplate
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

        main
                .addStatement("ixn.setMemStatFilter($S)", config.interaction.memStatusFilter)
                .addStatement("ixn.setRecStatFilter($S)", config.interaction.recStatusFilter)
                .addStatement("ixn.setSegCodeFilter($S)", config.interaction.segCodeFilter);

        if (config.interaction.compositeView != null)
        {
            main.addStatement("ixn.setCvwName($S)", config.interaction.compositeView);
        }

        main.addStatement("inputRows.addRow(memHead)");

        main.addStatement("$T status = ixn.execute(inputRows, outputRows, GetType.$N, keyType)", boolean.class, config.interaction.getType.toUpperCase());
    }

    @Override
    public void construct()
    {
        super.construct();
    }
}
