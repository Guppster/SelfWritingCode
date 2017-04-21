package com.example.codegen.view.templates;

import com.example.codegen.model.Properties;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;
import madison.mpi.*;
import madison.util.SetterException;

import java.io.IOException;
import java.util.Map;

public abstract class IxnMemTemplate extends IxnTemplate
{
    @Override
    public void generate(Properties config)
    {
        //Call the general interaction template generator
        super.generate(config);

        //Manipulate class to do things general to all Member Interactions
        main
                .addStatement("$1T inputRows = new $1T()", MemRowList.class)
                .addStatement("$1T outputRows = new $1T()", MemRowList.class)
                .addStatement("$1T outputAUDRows = new $1T()", MemRowList.class)
                .addStatement("$1T memHead = new $1T()", MemHead.class)
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

    void addDictionaryAttributes(Properties config)
    {
        //dicstore creation throws IOException
        main.addException(IOException.class);

        //setString throws SetterException
        main.addException(SetterException.class);

        //Create the dictionary to use
        main.addStatement("$1T dictionary = new $1T(context)", DicStore.class);

        //Create variable to use in class
        main.addStatement("$T tempAttribute", MemAttrRow.class);

        //For each attributeRows, assign its attributes and add the row to the input list
        for (Properties.AttributeRow row : config.rows)
        {
            main.addStatement("tempAttribute = dictionary.createMemAttrRowByCode($S, memHead)", row.code);

            for (Map.Entry<String, ConfigValue> entry : row.attributes.entrySet())
            {
                main.addStatement("tempAttribute.setString($S,$S)", entry.getKey(), entry.getValue().unwrapped());
            }

            main.addStatement("inputRows.addRow(tempAttribute)");
        }
    }
}
