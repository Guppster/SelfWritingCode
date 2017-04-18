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
                .addStatement("$1T keyType = new $1T", KeyType.class);
    }
}
