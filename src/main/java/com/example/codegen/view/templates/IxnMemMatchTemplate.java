package com.example.codegen.view.templates;

import com.example.codegen.model.Properties;
import madison.mpi.GetType;
import madison.mpi.SearchType;

public class IxnMemMatchTemplate extends IxnMemTemplate
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

        //Execute
        main.addStatement("$T status = ixn.execute(inputRows, outputRows, $T.$N, $T.$N, keyType, outputAUDRows)", boolean.class, GetType.class, config.interaction.getType.toUpperCase(), SearchType.class, config.interaction.searchType);
    }

    @Override
    public void construct()
    {
        super.construct();
    }
}
