package com.example.codegen.view.templates;

import com.example.codegen.model.Properties;
import com.typesafe.config.ConfigValue;
import madison.mpi.DicStore;
import madison.mpi.MemAttrRow;

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

        main.addStatement("ixn.setSegCodeFilter($S)", config.interaction.segCodeFilter);

        //Execute
        main.addStatement("$T status = ixn.execute(inputRows, outputRows, GetType.$N, SearchType.$N, outputAUDRows)", boolean.class, config.interaction.getType, config.interaction.searchType);
    }

    @Override
    public void construct()
    {
        super.construct();
    }
}
