package com.example.codegen.controller;

import com.example.codegen.model.Properties;
import com.example.codegen.view.TemplateComposer;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import javax.tools.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.Arrays;

public class Controller
{
    public static void main(String[] args)
    {
        //Get the compiler to run the generated class
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

        //Get the variables from configuration
        Properties config = getTypeSafeConfig();

        //Populate the template with variables
        TemplateComposer composer = new TemplateComposer(config);

        //Create the class in a file
        String fileString = composer.construct();

        System.out.println(fileString);

        //Run the program
        JavaFileObject file = new JavaSourceFromString("Interaction", fileString);

        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);
        JavaCompiler.CompilationTask       task             = compiler.getTask(null, null, diagnostics, null, null, compilationUnits);

        boolean success = task.call();
        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            System.out.println(diagnostic.getCode());
            System.out.println(diagnostic.getKind());
            System.out.println(diagnostic.getPosition());
            System.out.println(diagnostic.getStartPosition());
            System.out.println(diagnostic.getEndPosition());
            System.out.println(diagnostic.getSource());
            System.out.println(diagnostic.getMessage(null));

        }
        System.out.println("Success: " + success);

        if (success) {
            try {
                Class.forName("Interaction").getDeclaredMethod("main", new Class[] { String[].class })
                        .invoke(null, new Object[] { null });
            } catch (ClassNotFoundException e) {
                System.err.println("Class not found: " + e);
            } catch (NoSuchMethodException e) {
                System.err.println("No such method: " + e);
            } catch (IllegalAccessException e) {
                System.err.println("Illegal access: " + e);
            } catch (InvocationTargetException e) {
                System.err.println("Invocation target: " + e);
            }
        }
    }

    private static Properties getTypeSafeConfig()
    {
        File configFile = new File("/home/gsingh/Documents/Projects/SelfWritingCode/config.conf");

        // usual Typesafe Config mechanism to load the file
        Config tsConfig = ConfigFactory.parseFile(configFile).resolve();

        // create instance of the type class. This will perform all validations according to required properties and types:
        return new Properties(tsConfig);
    }

}

class JavaSourceFromString extends SimpleJavaFileObject
{
    private final String code;

    JavaSourceFromString(String name, String code)
    {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors)
    {
        return code;
    }
}
