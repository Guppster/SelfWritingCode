package com.example.codegen.view;

import com.example.codegen.model.Properties;
import com.example.codegen.view.templates.Template;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TemplateComposer
{
    private Template   template;
    private Properties config;

    public TemplateComposer(Properties config)
    {
        this.config = config;

        //Using Reflection to fetch a Set of all classnames inside com.example.codegen.view.templates package
        List<ClassLoader> classLoadersList = new LinkedList<>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                        .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                        .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("com.example.codegen.view.templates"))));


        Set<Class<?>> allClasses = reflections.getSubTypesOf(Object.class);

        //Extracts the name of the specific template that needs to be generated
        Class<?> theClass = allClasses.stream().filter(aClass -> aClass.getSimpleName().equals(config.interactionName)).findAny().orElse(null);

        //Create a new instance of that class
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

    public String construct()
    {
        template.initialize();
        template.generate(config);
        template.construct();
        return template.build();
    }
}
