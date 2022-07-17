package com.at.cas;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @create 2022-07-17
 */
public class AtomicReferenceDemo {

    public static void main(String[] args) {

        User z3 = new User("z3", 14);
        User l4 = new User("l4", 10);
        User w5 = new User("w5", 100);

        AtomicReference<User> atomicReference = new AtomicReference<>();

        atomicReference.set(z3);

        System.out.println(atomicReference.compareAndSet(z3,l4) + " -> " + atomicReference.get());
        System.out.println(atomicReference.compareAndSet(z3,w5) + " -> " + atomicReference.get());


    }

}

class User{

    private String name;
    private int age;

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
