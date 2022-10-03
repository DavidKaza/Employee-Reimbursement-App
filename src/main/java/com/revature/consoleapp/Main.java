package com.revature.consoleapp;

import com.revature.consoleapp.menu.MainMenu;

import java.util.Scanner;

public class Main {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        MainMenu mainMenuObject = new MainMenu();
        mainMenuObject.display();
    }
}