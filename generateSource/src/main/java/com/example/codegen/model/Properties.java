package com.example.codegen.model;

import com.typesafe.config.Config;

public class Properties
{
    public final Properties.Car    Car;
    public final Properties.Person Person;

    public Properties(Config c)
    {
        this.Car = new Properties.Car(c.getConfig("Car"));
        this.Person = new Properties.Person(c.getConfig("Person"));
    }

    public static class Car
    {
        public final String name;
        public final int    year;

        public Car(Config config)
        {
            this.name = config.getString("name");
            this.year = config.getInt("year");
        }
    }

    public static class Person
    {
        public final int    age;
        public final String car;
        public final String email;
        public final String location;
        public final String name;

        public Person(Config config)
        {
            this.email = config.hasPathOrNull("email") ?
                         config.getString("email") :
                         null;

            this.location = config.hasPathOrNull("location") ?
                            config.getString("location") :
                            null;

            this.name = config.getString("name");
            this.age = config.getInt("age");
            this.car = config.getString("car");
        }
    }
}
