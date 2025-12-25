package com.airtribe.learntrack;

import com.airtribe.learntrack.service.StudentService;
import java.util.Scanner;

public class AirtribePortal {
    public static void main(String[] args) {

        new AirtribePortal().managementOptions();

    }

    public void managementOptions() {

        Scanner sc = new Scanner(System.in);
        int selectedOption;

        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║          🎓 AIRTRIBE COURSE MANAGEMENT SYSTEM        ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.println("║                                                      ║");
        System.out.println("║   1. Student Management                              ║");
        System.out.println("║   2. Course Management                               ║");
        System.out.println("║   3. Enrollment Management                           ║");
        System.out.println("║                                                      ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.print  ("║   Enter your choice (1-3): ");

        selectedOption = sc.nextInt();

        if (selectedOption >= 1 && selectedOption <= 3) {
            System.out.println("╚══════════════════════════════════════════════════════╝");
            StudentService ss = new StudentService();
            ss.manageStudent ();
            return;
        }

        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.println("║   ❌ Invalid option!                                  ║");
        System.out.println("║   👉 Please enter a valid option between 1 and 3.    ║");

        System.out.println("╚══════════════════════════════════════════════════════╝");
        managementOptions();
    }
}