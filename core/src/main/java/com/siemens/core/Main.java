package com.siemens.core;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**

 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=
                new AnnotationConfigApplicationContext("com.siemens.core.config");

//        Console console=context.getBean(Console.class);
//        console.runConsole();

        System.out.println("bye");
    }
}
