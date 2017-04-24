package com.example.codegen.view.templates;

import com.example.codegen.model.Properties;
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

        initializeInputRows(config);

        main.addStatement("inputRows.addRow(memHead)");
    }

    private void initializeInputRows(Properties config)
    {
        setKey(config);
    }

    private void setKey(String headName, Properties.MemHead memHead)
    {
        //Use provided information to generate interaction in different ways
        if (memHead.memRecNum > 0)
        {
            main
                    .addStatement("keyType = KeyType.MEMRECNO")
                    .addStatement("$S.setMemRecno($L)", headName, memHead.memRecNum);
        } else if (memHead.memIDNum != null && memHead.memSrcCode != null)
        {
            main
                    .addStatement("keyType = KeyType.MEMIDNUM")
                    .addStatement("$S.setMemIdnum($S)", headName, memHead.memIDNum)
                    .addStatement("$S.setSrcCode($S)", headName, memHead.memSrcCode);
        } else if (memHead.entRecNum > 0)
        {
            main
                    .addStatement("keyType = KeyType.ENTRECNO")
                    .addStatement("$S.setEntRecnos(new $N{$L}", headName, Long.class, memHead.entRecNum);
        }

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
        for (Properties.AttributeRow row : config.attributeRows)
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
