package com.example.codegen;

import com.typesafe.config.Config;

public class Properties
{
  public final Properties.Person Person;

  public static class Person
  {
    public final int age;
    public final java.lang.String email;
    public final java.lang.String location;
    public final java.lang.String name;

    public Person(Config c)
    {
      this.age = c.getInt("age");
      this.email = c.hasPathOrNull("email") ? c.getString("email") : null;
      this.location = c.getString("location");
      this.name = c.hasPathOrNull("name") ? c.getString("name") : null;
    }
  }

  public Properties(Config c)
  {
    this.Person = new Properties.Person(c.getConfig("Person"));
  }
}
