package com.jp.run.project.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class ProjectMain {

	public static void main(String[] args) throws IOException {
		new ClassPathXmlApplicationContext("spring-root.xml").start();
		System.in.read();
	}
}
