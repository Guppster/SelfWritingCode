package com.example.codegen.view.templates;

import com.example.codegen.model.Properties;
import madison.mpi.DicStore;

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

        main.addStatement("$1T dictionary = new $1T(context)", DicStore.class);

        //Get list of attributes from properties

        //Traverse each row and run the following commands on them

        //For each config.interaction.attributeRows
            //main.addStatement("$T tempAttribute = dictionary.createMemAttrRowByCode($S, memHead)", MemAttrRow.class, config.{rowName}.code);
            //main.addStatement("tempAttribute.setString($S,$S)", config.{rowName}.attributes.key, config.{rowName}.attributes.value);
            //main.addStatement("inputRows.addRow(tempAttribute)");

        //Execute
    }

    @Override
    public void construct()
    {
        super.construct();
    }
}
