package com.example.codegen.view.templates;

import com.example.codegen.model.Properties;
import com.typesafe.config.ConfigValue;
import madison.mpi.DicStore;
import madison.mpi.GetType;
import madison.mpi.MemAttrRow;
import madison.mpi.SearchType;

import java.util.Map;

public class IxnMemSearchTemplate extends IxnMemTemplate
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

        addDictionaryAttributes(config);

        main
                .addStatement("ixn.setMemStatFilter($S)", config.interaction.memStatusFilter)
                .addStatement("ixn.setRecStatFilter($S)", config.interaction.recStatusFilter)
                .addStatement("ixn.setSegCodeFilter($S)", config.interaction.segCodeFilter);

        if (config.interaction.compositeView != null)
        {
            main.addStatement("ixn.setCvwName($S)", config.interaction.compositeView);
        }

        //Execute
        main.addStatement("$T status = ixn.execute(inputRows, outputRows, $T.$N, $T.$N, outputAUDRows)", boolean.class, GetType.class, config.interaction.getType, SearchType.class, config.interaction.searchType);
    }

    @Override
    public void construct()
    {
        super.construct();
    }
}
