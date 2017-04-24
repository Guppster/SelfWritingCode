package com.example.codegen.view.templates;

import com.example.codegen.model.Properties;
import madison.mpi.MatchMode;
import madison.mpi.MemHead;
import madison.mpi.MemMode;
import madison.mpi.PutType;

public class IxnMemCompareTemplate extends IxnMemTemplate
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
                .addStatement("$1T memHead2 = new $1T()", MemHead.class)

        //Execute
        main.addStatement("$T status = ixn.execute(inputRows, outputRows, $T.INSERT_UPDATE, $T.fromString($S), $T.IMMEDIATE)", boolean.class, PutType.class, MemMode.class, config.interaction.memMode, MatchMode.class);
    }

    @Override
    public void construct()
    {
        super.construct();
    }
}
