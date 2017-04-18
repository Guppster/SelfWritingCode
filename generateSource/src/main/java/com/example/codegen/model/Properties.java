package com.example.codegen.model;

import com.typesafe.config.Config;

public class Properties
{
    public final Properties.Context     context;
    public final Properties.Interaction interaction;
    public final String                 interactionName;

    public Properties(Config c)
    {
        this.context = new Properties.Context(c.getConfig("context"));
        this.interactionName = c.getString("interaction name");
        this.interaction = new Properties.Interaction(c.getConfig("interaction"));
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
        public final String entType;            //Entity type
        public final String memSrcCode;         //Member source code
        public final String memIDNum;           //Member ID Number
        public final String segCodeFilter;      //Filter specfic segments
        public final String recStatusFilter;    //Filter record status indicators
        public final String memStatusFilter;    //Filter member status indicators
        public final String compositeView;      //Set a composite view
        public final String getType;            //ASMEMBER or ASENTITY (single member or single entity)
        public final long   memRecNum;          //Member record number
        public final long   entRecNum;          //Entity record number
        public final long   taskRecNum;         //Task record number

        public Interaction(Config config)
        {
            this.entType = config.getString("entity type");

            this.memSrcCode = config.hasPathOrNull("member source code") ?
                              config.getString("member source code") :
                              null;

            // ASMEMBER specifies that only individual members will be retrieve.
            // ASENTITY specifies entities will be retrieve.
            this.getType = config.hasPathOrNull("getType") ?
                              config.getString("getType") :
                              "ASENTITY";

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

            this.memIDNum = config.hasPathOrNull("member ID number") ?
                            config.getString("member ID number") :
                            null;

            this.memRecNum = config.hasPathOrNull("member record number") ?
                             config.getInt("member record number") :
                             -1;
            this.entRecNum = config.hasPathOrNull("entity record number") ?
                             config.getInt("entity record number") :
                             -1;

            this.taskRecNum = config.hasPathOrNull("task record number") ?
                              config.getInt("task record number") :
                              -1;
        }
    }
}
