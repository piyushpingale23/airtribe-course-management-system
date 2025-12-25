package com.airtribe.learntrack;

import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.StudentService;
import java.util.Scanner;

public class AirtribePortal {
    public static void main(String[] args) {

        new AirtribePortal().managementOptions();

    }

    public void managementOptions() {

        Scanner sc = new Scanner(System.in);
        int selectedOption;

        while (true) {

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

            String input = sc.nextLine().trim();

            if (!input.matches("[1-3]")) {
                System.out.println("╔══════════════════════════════════════════════════════╗");
                System.out.println("║ ❌ Invalid input! Please enter only 1, 2 or 3.        ║");
                System.out.println("╚══════════════════════════════════════════════════════╝");
                continue;
            }

            selectedOption = Integer.parseInt(input);
            System.out.println("╚══════════════════════════════════════════════════════╝");

            switch (selectedOption) {
                case 1 -> new StudentService().manageStudent();
                case 2 -> new CourseService().manageCourse();
                default -> {
                    System.out.println("❌ Invalid option! Please enter a number between 1-6.");
                    managementOptions();
                }
            }
        }
    }

}