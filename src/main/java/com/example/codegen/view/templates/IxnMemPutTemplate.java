package com.example.codegen.view.templates;

import com.example.codegen.model.Properties;
import com.typesafe.config.ConfigValue;
import madison.mpi.*;

import java.util.Map;

public class IxnMemPutTemplate extends IxnMemTemplate
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

        if(config.interaction.entPriority != 0)
        {
            main.addStatement("ixn.setEntPrior(vars.get(\"ENTPRIORITY\"))");
        }

        //Execute
        main.addStatement("$T status = ixn.execute(inputRows, outputRows, $T.INSERT_UPDATE, $T.fromString(vars.get(\"MEMMODE\")), $T.IMMEDIATE)", boolean.class, PutType.class, MemMode.class, MatchMode.class);
    }

    @Override
    public void construct()
    {
        super.construct();
    }
}
