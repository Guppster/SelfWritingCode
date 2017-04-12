package com.example.codegen.view;

import com.example.codegen.model.Properties;
import com.example.codegen.view.templates.Template;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class TemplateComposer
{
    static Template template;

    public TemplateComposer(Properties config)
    {
        //Extracts the name of the specific template that needs to be generated
        //and populates that templates fields

        Reflections reflections = new Reflections("com.example.codegen.view.templates");

        Set<Class<?>> allClasses = reflections.getSubTypesOf(Object.class);

        Class<?> theClass = allClasses.stream().filter(aClass -> aClass.getSimpleName().equals(config.ClassName)).findAny().orElse(null);

        try
        {
            template = (Template) theClass.getConstructor().newInstance();
        } catch (InstantiationException |
                IllegalAccessException |
                InvocationTargetException |
                NoSuchMethodException e)
        {
            e.printStackTrace();
        }

    }

    private static void generateBase()
    {
        template.initialize();
        template.generate();
        System.out.println(template.build());
    }
}
