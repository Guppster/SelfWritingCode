package com.example.codegen.view.templates;

import com.example.codegen.model.Properties;
import madison.mpi.KeyType;
import madison.mpi.MemHead;
import madison.mpi.MemRowList;

public abstract class IxnMemTemplate extends IxnTemplate
{
    @Override
    public void generate(Properties config)
    {
        //Call the general interaction template generator
        super.generate(config);

        //Manipulate class to do things general to all Member Interactions
        main
                .addStatement("$1T inputRows = new $1T", MemRowList.class)
                .addStatement("$1T outputRows = new $1T", MemRowList.class)
                .addStatement("$1T memHead = new $1T", MemHead.class)
                .addStatement("$T keyType = KeyType.UNKNOWN", KeyType.class)
                .addStatement("ixn.setEntType($S)", config.interaction.entType)
                .addStatement("ixn.setMemType($S)", config.interaction.memType);

         //Use provided information to generate interaction in different ways
        if (config.interaction.memRecNum > 0)
        {
            main
                    .addStatement("keyType = KeyType.MEMRECNO")
                    .addStatement("memHead.setMemRecno($L)", config.interaction.memRecNum);
        } else if (config.interaction.memIDNum != null && config.interaction.memSrcCode != null)
        {
            main
                    .addStatement("keyType = KeyType.MEMIDNUM")
                    .addStatement("memHead.setMemIdnum($S)", config.interaction.memIDNum)
                    .addStatement("memHead.setSrcCode($S)", config.interaction.memSrcCode);
        } else if (config.interaction.entRecNum > 0)
        {
            main
                    .addStatement("keyType = KeyType.ENTRECNO")
                    .addStatement("memHead.setEntRecnos(new $N{$L}", Long.class, config.interaction.entRecNum);
        }

        main.addStatement("inputRows.addRow(memHead)");
    }
}
