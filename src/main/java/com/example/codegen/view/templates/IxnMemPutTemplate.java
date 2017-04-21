package com.example.codegen.view.templates;

import com.example.codegen.model.Properties;
import com.typesafe.config.ConfigValue;
import madison.mpi.DicStore;
import madison.mpi.MemAttrRow;

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

        //Create the dictionary to use
        main.addStatement("$1T dictionary = new $1T(context)", DicStore.class);

        //For each attributeRows, assign its attributes and add the row to the input list
        for (Properties.AttributeRow row : config.rows)
        {
            main.addStatement("$T tempAttribute = dictionary.createMemAttrRowByCode($S, memHead)", MemAttrRow.class, row.code);

            for (Map.Entry<String, ConfigValue> entry : row.attributes.entrySet())
            {
                main.addStatement("tempAttribute.setString($S,$S)", entry.getKey(), entry.getValue().unwrapped());
            }

            main.addStatement("inputRows.addRow(tempAttribute)");
        }

        if(config.interaction.entPriority != 0)
        {
            main.addStatement("ixn.setEntPrior($L)", config.interaction.entPriority);
        }

        //Execute
        main.addStatement("$T status = ixn.execute(inputRows, outputRows, PutType.Insert_Update, MemMode.fromString($S), MatchMode.IMMEDIATE)", boolean.class, config.interaction.memMode);
    }

    @Override
    public void construct()
    {
        super.construct();
    }
}
