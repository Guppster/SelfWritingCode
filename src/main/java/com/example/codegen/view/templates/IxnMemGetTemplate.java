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
                .addStatement("ixn.setEntType($S)", config.interaction.entType)
                .addStatement("ixn.setMemStatFilter($S)", config.interaction.memStatusFilter)
                .addStatement("ixn.setRecStatFilter($S)", config.interaction.recStatusFilter)
                .addStatement("ixn.setSegCodeFilter($S)", config.interaction.segCodeFilter);

        //Use provided information to generate interaction in different ways
        if (config.interaction.memRecNum > 0)
        {
            main
                    .addStatement("keyType = $N", KeyType.MEMRECNO)
                    .addStatement("memHead.setMemRecno($L)", config.interaction.memRecNum);
        }
        else if (config.interaction.memIDNum != null && config.interaction.memSrcCode != null)
        {
            main
                    .addStatement("keyType = $N", KeyType.MEMIDNUM)
                    .addStatement("memHead.setMemIdnum($S)", config.interaction.memIDNum)
                    .addStatement("memHead.setSrcCode($S)", config.interaction.memSrcCode);
        }
        else if (config.interaction.entRecNum > 0)
        {
            main
                    .addStatement("keyType = $N", KeyType.ENTRECNO)
                    .addStatement("memHead.setEntRecnos(new $N{$L}", Long.class, config.interaction.entRecNum);
        }

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
