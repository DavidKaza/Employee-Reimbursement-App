package com.revature.consoleapp.menu;

import com.revature.consoleapp.Main;

public class UserMenu {
    public void display(){
        while(true){
            System.out.println("===== User Menu =====");
            System.out.println("1.) Submit Reimbursement Ticket");
            System.out.println("2.) Logout");

            String choice = Main.sc.nextLine();

            if(choice.equals("1")){
                System.out.println("How much u need foo?");
                int amount = Main.sc.nextInt();
                System.out.println("Amount requested: " + amount);
                Main.sc.nextLine();
            }else if(choice.equals("2")){
                return;
            }else{
                System.out.println("Invalid choice.");
            }
        }
    }
}
