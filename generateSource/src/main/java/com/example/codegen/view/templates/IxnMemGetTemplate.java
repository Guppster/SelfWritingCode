package com.example.codegen.view.templates;

import com.example.codegen.model.Properties;
import madison.mpi.IxnMemGet;
import madison.mpi.KeyType;
import madison.mpi.MemHead;
import madison.mpi.MemRowList;

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
                .addStatement("$1T memGet = new $1T($2N)", IxnMemGet.class, "context")
                .addStatement("$1T inputRows = new $1T", MemRowList.class)
                .addStatement("$1T outputRows = new $1T", MemRowList.class)
                .addStatement("$1T memHead = new $1T", MemHead.class)
                .addStatement("$1T keyType = new $1T", KeyType.class)
                .addStatement("memGet.setEntType($S)", config.interaction.entType)
                .addStatement("memGet.setMemStatFilter($S)", config.interaction.memStatusFilter)
                .addStatement("memGet.setRecStatFilter($S)", config.interaction.recStatusFilter)
                .addStatement("memGet.setSegCodeFilter($S)", config.interaction.segCodeFilter);


        //Use provided information to generate interaction in different ways
        if (config.interaction.memRecNum > 0)
        {
            main
                    .addStatement("keyType = $N", KeyType.MEMRECNO)
                    .addStatement("memHead.setMemRecno($L)", config.interaction.memRecNum);
        } else if (config.interaction.memIDNum != null && config.interaction.memSrcCode != null)
        {
            main
                    .addStatement("keyType = $N", KeyType.MEMIDNUM)
                    .addStatement("memHead.setMemIdnum($S)", config.interaction.memIDNum)
                    .addStatement("memHead.setSrcCode($S)", config.interaction.memSrcCode);
        } else if (config.interaction.entRecNum > 0)
        {
            main
                    .addStatement("keyType = $N", KeyType.ENTRECNO)
                    .addStatement("memHead.setEntRecnos(new $N{$L}", Long.class, config.interaction.entRecNum);
        }

        if (config.interaction.compositeView != null)
        {
            main.addStatement("memGet.setCvwName($S)", config.interaction.compositeView);
        }

        main.addStatement("inputRows.addRow(memHead)");

        main.addStatement("$T status = memGet.execute(inputRows, outputRows, GetType.$S, keytype", boolean.class, config.interaction.getType.toUpperCase());
    }

    @Override
    public void construct()
    {
        super.construct();
    }
}
