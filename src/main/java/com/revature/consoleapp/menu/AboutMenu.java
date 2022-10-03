package com.revature.consoleapp.menu;

import com.revature.consoleapp.Main;

public class AboutMenu {
    public void display(){
        while(true){
            System.out.println("This is the reimbursement system for Revature LLC. Create an account and log in to request reimbursements.");
            System.out.println("1. Back");
            String choice = Main.sc.nextLine();
            if(choice.equals("1")){
                return;
            }else{
                System.out.println("Invalid choice.");
            }
        }
    }
}
