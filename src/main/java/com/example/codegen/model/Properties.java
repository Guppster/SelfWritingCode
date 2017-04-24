package com.example.codegen.model;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Properties
{
    //Declare fields
    public final Properties.Context            context;
    public final Properties.Interaction        interaction;
    public final List<Properties.AttributeRow> attributeRows;
    public final List<Properties.InputRow>     inputRows;
    public final String                        interactionName;

    public Properties(Config c)
    {
        //Initialize fields
        this.context = new Properties.Context(c.getConfig("context"));
        this.interactionName = c.getString("interaction name");
        this.interaction = new Properties.Interaction(c.getConfig("interaction"));
        this.attributeRows = new ArrayList<>();
        this.inputRows = new ArrayList<>();

        //Initialize the input row list
        if (this.interaction.inputRows != null)
        {
            for (Config row : this.interaction.inputRows)
            {
                inputRows.add(new InputRow(row));
            }
        }

        //Initialize the attribute row list
        if (this.interaction.attrbuteRows != null)
        {
            for (Config row : this.interaction.attrbuteRows)
            {
                attributeRows.add(new AttributeRow(row));
            }
        }
    }

    public static class Context
    {
        public final String host;
        public final String user;
        public final String pass;
        public final int    port;

        public Context(Config config)
        {
            this.host = config.getString("host");
            this.user = config.getString("user");
            this.pass = config.getString("pass");
            this.port = config.getInt("port");
        }
    }

    public static class Interaction
    {
        public final List<? extends Config> inputRows;          //Defines the inputRows used by the interaction
        public final List<? extends Config> attrbuteRows;       //Defines the attributeRows used by the interaction

        public final String entType;            //Entity type
        public final String memType;            //Member Type
        public final String memMode;            //Member update mode
        public final String segCodeFilter;      //Filter specfic segments
        public final String recStatusFilter;    //Filter record status indicators
        public final String memStatusFilter;    //Filter member status indicators
        public final String compositeView;      //Set a composite view
        public final String getType;            //ASMEMBER or ASENTITY (single member or single entity)
        public final String searchType;            //ASMEMBER or ASENTITY (single member or single entity)
        public final long   taskRecNum;         //Task record number
        public final long   entPriority;          //Entity management priority

        public Interaction(Config config)
        {
            this.entType = config.getString("entityType");

            // Member types are listed in mpi_memtype table.
            this.memType = config.hasPathOrNull("member type") ?
                           config.getString("member type") :
                           "PERSON";

            // Member update mode
            this.memMode = config.hasPathOrNull("memberMode") ?
                           config.getString("memberMode") :
                           "Partial";

            // ASMEMBER specifies that only individual members will be retrieve.
            // ASENTITY specifies entities will be retrieve.
            this.getType = config.hasPathOrNull("getType") ?
                           config.getString("getType") :
                           "ASENTITY";

            // ASMEMBER specifies that only individual members will be retrieve.
            // ASENTITY specifies entities will be retrieve.
            this.searchType = config.hasPathOrNull("searchType") ?
                              config.getString("searchType") :
                              "ASMEMBER";

            // Set a segment code filter to limit output to specific segments.
            // MEMHEAD, MEMATTR, MEMNAME, MEMADDR, MEMPHONE, MEMIDENT, MEMDATE
            this.segCodeFilter = config.hasPathOrNull("segcode filter") ?
                                 config.getString("segcode filter") :
                                 "MEMHEAD,MEMATTRALL,AUDALL";

            // Set the record status indicators desired.
            // The values include (A)ctive, (I)nactive, (D)eleted and (S)hadow.
            this.recStatusFilter = config.hasPathOrNull("record status filter") ?
                                   config.getString("record status filter") :
                                   "A,I";

            // Set the record status indicators desired.
            // The values include (A)ctive, (I)nactive, (D)eleted and (S)hadow.
            this.memStatusFilter = config.hasPathOrNull("member status filter") ?
                                   config.getString("member status filter") :
                                   "A,I";

            this.compositeView = config.hasPathOrNull("composite view") ?
                                 config.getString("composite view") :
                                 null;

            this.taskRecNum = config.hasPathOrNull("task record number") ?
                              config.getInt("task record number") :
                              -1;

            this.entPriority = config.hasPathOrNull("entityPriority") ?
                               config.getInt("entityPriority") :
                               0;

            this.attrbuteRows = config.hasPathOrNull("attributeRows") ?
                                config.getConfigList("attributeRows") :
                                null;

            this.inputRows = config.hasPathOrNull("inputRows") ?
                             config.getConfigList("inputRows") :
                             null;
        }
    }

    public static class InputRow
    {
        public final long   memRecNum;          //Member record number
        public final long   entRecNum;          //Entity record number
        public final String memSrcCode;         //Member source code
        public final String memIDNum;           //Member ID Number
        public final String name;               //Any name to identify this row in code

        public InputRow(Config config)
        {

            this.name = config.hasPathOrNull("name") ?
                        config.getString("name") :
                        null;

            this.memSrcCode = config.hasPathOrNull("memberSourceCode") ?
                              config.getString("memberSourceCode") :
                              null;

            this.memIDNum = config.hasPathOrNull("memberIDNumber") ?
                            config.getString("memberIDNumber") :
                            null;

            this.memRecNum = config.hasPathOrNull("member record number") ?
                             config.getInt("member record number") :
                             -1;

            this.entRecNum = config.hasPathOrNull("entity record number") ?
                             config.getInt("entity record number") :
                             -1;
        }
    }

    public static class AttributeRow
    {
        public final Map<String, ConfigValue> attributes;
        public final String                   code;

        public AttributeRow(Config config)
        {
            this.code = config.getString("code");
            this.attributes = config
                    .getConfig("attributes")
                    .root()
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        }
    }
}
