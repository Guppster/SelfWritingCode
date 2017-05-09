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
                .addStatement("$1T outputAUDRows = new $1T()", AudRowList.class)
                .addStatement("$T keyType = KeyType.UNKNOWN", KeyType.class)
                .addStatement("ixn.setEntType($S)", config.interaction.entType)
                .addStatement("ixn.setMemType($S)", config.interaction.memType);

        initializeInputRows(config);
    }

    private void initializeInputRows(Properties config)
    {
        for (Properties.InputRow row : config.inputRows)
        {
            main.addStatement("$1T $2N = new $1T()", MemHead.class, row.name);

            setKey(row.name, row);

            addDictionaryAttributes(row.name, row);

            main.addStatement("inputRows.addRow($N)", row.name);
        }
    }

    private void setKey(String headName, Properties.InputRow memHead)
    {
        //Use provided information to generate interaction in different ways
        if (memHead.memRecNum > 0)
        {
            main
                    .addStatement("keyType = KeyType.MEMRECNO")
                    .addStatement("$N.setMemRecno($L)", headName, memHead.memRecNum);
        } else if (memHead.memIDNum != null && memHead.memSrcCode != null)
        {
            main
                    .addStatement("keyType = KeyType.MEMIDNUM")
                    .addStatement("$N.setMemIdnum($S)", headName, memHead.memIDNum)
                    .addStatement("$N.setSrcCode($S)", headName, memHead.memSrcCode);
        } else if (memHead.entRecNum > 0)
        {
            main
                    .addStatement("keyType = KeyType.ENTRECNO")
                    .addStatement("$N.setEntRecnos(new $N{$L}", headName, Long.class, memHead.entRecNum);
        }
    }

    void addDictionaryAttributes(String headName, Properties.InputRow memHead)
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
        for (Properties.AttributeRow row : memHead.attributeRows)
        {
            main.addStatement("tempAttribute = dictionary.createMemAttrRowByCode($S, $N)", row.code, headName);

            for (Map.Entry<String, ConfigValue> entry : row.attributes.entrySet())
            {
                main.addStatement("tempAttribute.setString($S,$S)", entry.getKey(), entry.getValue().unwrapped());
            }

            main.addStatement("inputRows.addRow(tempAttribute)");
        }
    }
}
