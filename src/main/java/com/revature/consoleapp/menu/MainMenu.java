package com.revature.consoleapp.menu;

import com.revature.consoleapp.Main;

public class MainMenu {

    public void display() {
        while (true) {
            System.out.println("Welcome to the Revature employee reimbursement system.");
            System.out.println("Select from the following options:");
            System.out.println("1.) Authenticate");
            System.out.println("2.) About");
            System.out.println("3.) Exit");

            String choice = Main.sc.nextLine();

            if (choice.equals("1")) {
                AuthenticationMenu authMenuObject = new AuthenticationMenu();
                authMenuObject.display();
            } else if (choice.equals("2")) {
                AboutMenu aboutMenuObject = new AboutMenu();
                aboutMenuObject.display();
            } else if (choice.equals("3")) {
                return;
            } else {
                System.out.println("Invalid choice");
            }
        }
    }
}