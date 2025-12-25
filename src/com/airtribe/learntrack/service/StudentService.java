package com.airtribe.learntrack.service;

import com.airtribe.learntrack.AirtribePortal;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.util.InMemoryDb;
import java.util.Scanner;

public class StudentService {

    public void manageStudent() {

        manageStudentOptions();

    }

    private void manageStudentOptions() {

        Scanner sc = new Scanner(System.in);
        int selectedOption;

        System.out.println("╭──────────────────────────────────────────────────────╮");
        System.out.println("│            👨‍🎓 STUDENT MANAGEMENT PORTAL              │");
        System.out.println("├──────────────────────────────────────────────────────┤");
        System.out.println("│                                                      │");
        System.out.println("│   1. Register New Student                            │");
        System.out.println("│   2. Search Student By ID                            │");
        System.out.println("│   3. Update Student Details                          │");
        System.out.println("│   4. Remove Student                                  │");
        System.out.println("│   5. View All Students                               │");
        System.out.println("│   6. Back to Main Menu                               │");
        System.out.println("│                                                      │");
        System.out.println("├──────────────────────────────────────────────────────┤");
        System.out.print  ("│   Select an option (1-6): ");

        selectedOption = sc.nextInt();

        switch (selectedOption) {
            case 1 -> registerNewStudent();
            case 2 -> searchStudentById();
            case 3 -> updateStudent();
            case 4 -> removeStudent();
            case 5 -> getAllStudents();
            case 6 -> backToMainMenu();
            default -> {
                System.out.println("❌ Invalid option! Please enter a number between 1-6.");
                manageStudentOptions();
            }

        }

        System.out.println("|──────────────────────────────────────────────────────|");
        System.out.println("|   ❌ Invalid option!                                  |");
        System.out.println("|   👉 Please enter a valid option between 1 and 6.    |");

        System.out.println("|──────────────────────────────────────────────────────|");

    }

    private void backToMainMenu() {
        new AirtribePortal().managementOptions();
    }

    private void getAllStudents() {
        System.out.println("╔═════════╦═════════════════╦═════════════════╦═══════════════════════════╦════════════╦════════╗");
        System.out.println("║ ID      ║ First Name      ║ Last Name       ║ Email                     ║ Batch      ║ Active ║");
        System.out.println("╠═════════╬═════════════════╬═════════════════╬═══════════════════════════╬════════════╬════════╣");

        if (InMemoryDb.studentList.isEmpty()) {
            System.out.println("║                    ❌ No students found in the system                     ║");
        } else {
            for (Student s : InMemoryDb.studentList) {
                System.out.printf("║ %-7d ║ %-15s ║ %-15s ║ %-25s ║ %-10s ║ %-6s ║%n",
                        s.getId(),
                        s.getFirstName() == null ? "" : s.getFirstName(),
                        s.getLastName() == null ? "" : s.getLastName(),
                        s.getEmail() == null ? "" : s.getEmail(),
                        s.getBatch() == null ? "" : s.getBatch(),
                        s.isActive() ? "Yes" : "No");
            }
        }

        System.out.println("╚═════════╩═════════════════╩═════════════════╩═══════════════════════════╩════════════╩════════╝");

        manageStudentOptions();
    }

    private void removeStudent() {
        Scanner sc = new Scanner(System.in);

        System.out.println("|******************************************************|");
        System.out.print("|   Enter Student ID to Remove: ");
        int sId = sc.nextInt();
        sc.nextLine();
        System.out.println("|******************************************************|");

        try {
            Student student = InMemoryDb.studentList.stream()
                    .filter(s -> s.getId() == sId)
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException(
                            "❌ Student with ID " + sId + " not found!"
                    ));

            System.out.println("🎯 Student Found: " + student.getFirstName() + " " + student.getLastName());

            System.out.print("| Are you sure to remove this student? (Y/N): ");
            String confirm = sc.nextLine();

            if (confirm.equalsIgnoreCase("Y")) {
                student.setActive(false);
                System.out.println("✅ Student with ID " + sId + " has been removed.");
            } else {
                System.out.println("⚠ Removal cancelled!");
            }

        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("|******************************************************|");
        manageStudentOptions();
    }

    private void updateStudent() {
        Scanner sc = new Scanner(System.in);

        System.out.println("|******************************************************|");
        System.out.print("|   Enter Student ID to Update: ");
        int sId = sc.nextInt();
        sc.nextLine();
        System.out.println("|******************************************************|");

        try {
            Student student = InMemoryDb.studentList.stream()
                    .filter(s -> s.getId() == sId)
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException(
                            "❌ Student with ID " + sId + " not found!"
                    ));

            System.out.println("🎯 Student Found: " + student.getFirstName() + " " + student.getLastName());
            System.out.println("|******************************************************|");

            System.out.println("|───────────────────────────────────────────────────────|");
            System.out.println("| Which field(s) do you want to update? (comma separated) |");
            System.out.println("| 1. First Name                                         |");
            System.out.println("| 2. Last Name                                          |");
            System.out.println("| 3. Email                                              |");
            System.out.println("| 4. Batch                                              |");
            System.out.print("| Enter choices (e.g., 1,3,4): ");

            String input = sc.nextLine();
            String[] choices = input.split(",");

            for (String ch : choices) {
                switch (ch.trim()) {
                    case "1" -> {
                        System.out.print("| Enter new First Name: ");
                        student.setFirstName(sc.nextLine());
                    }
                    case "2" -> {
                        System.out.print("| Enter new Last Name: ");
                        student.setLastName(sc.nextLine());
                    }
                    case "3" -> {
                        System.out.print("| Enter new Email: ");
                        student.setEmail(sc.nextLine());
                    }
                    case "4" -> {
                        System.out.print("| Enter new Batch: ");
                        student.setBatch(sc.nextLine());
                    }
                    default -> System.out.println("| ❌ Invalid choice: " + ch + " |");
                }
            }

            System.out.println("|******************************************************|");
            System.out.println("✅ Student Updated Successfully!");
            System.out.println("|******************************************************|");

        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
            System.out.println("|******************************************************|");
        }

        manageStudentOptions();
    }

    private void searchStudentById() {
        Scanner sc = new Scanner(System.in);

        System.out.println("|──────────────────────────────────────────────────────|");
        System.out.print("|   Enter Student ID to Search: ");
        int sId = sc.nextInt();
        System.out.println("|──────────────────────────────────────────────────────|");

        try {
            Student s = InMemoryDb.studentList.stream()
                    .filter(st -> st.getId() == sId)
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException(
                            "❌ Student with ID " + sId + " not found!"
                    ));

            System.out.println("╔═════════╦═════════════════╦═════════════════╦═══════════════════════════╦════════════╦════════╗");
            System.out.println("║ ID      ║ First Name      ║ Last Name       ║ Email                     ║ Batch      ║ Active ║");
            System.out.println("╠═════════╬═════════════════╬═════════════════╬═══════════════════════════╬════════════╬════════╣");

            System.out.printf("║ %-7d ║ %-15s ║ %-15s ║ %-25s ║ %-10s ║ %-6s ║%n",
                    s.getId(),
                    s.getFirstName() == null ? "" : s.getFirstName(),
                    s.getLastName() == null ? "" : s.getLastName(),
                    s.getEmail() == null ? "" : s.getEmail(),
                    s.getBatch() == null ? "" : s.getBatch(),
                    s.isActive() ? "Yes" : "No");

            System.out.println("╚═════════╩═════════════════╩═════════════════╩═══════════════════════════╩════════════╩════════╝");

        } catch (EntityNotFoundException ex) {
            System.out.println("╔══════════════════════════════════════════════════════╗");
            System.out.printf("║ %-52s ║%n", ex.getMessage());
            System.out.println("╚══════════════════════════════════════════════════════╝");
        }

        manageStudentOptions();
    }


    private void registerNewStudent() {
        Scanner sc = new Scanner(System.in);

        System.out.println("|******************************************************|");
        System.out.println("|                📝 STUDENT REGISTRATION FORM          |");
        System.out.println("|******************************************************|");

        int id;
        while (true) {
            System.out.print("👉 Student ID        : ");
            if (sc.hasNextInt()) {
                id = sc.nextInt();
                sc.nextLine();

                if (id <= 0) {
                    System.out.println("❌ ID must be a positive number. Please try again.");
                    continue;
                }

                int finalId = id;
                boolean exists = InMemoryDb.studentList.stream()
                        .anyMatch(s -> s.getId() == finalId);
                if (exists) {
                    System.out.println("❌ ID already exists! Please enter a unique ID.");
                } else {
                    break;
                }

            } else {
                System.out.println("❌ Invalid input! Please enter a numeric ID.");
                sc.nextLine();
            }
        }

        System.out.print("👉 First Name        : ");
        String firstName = sc.nextLine();

        System.out.print("👉 Last Name         : ");
        String lastName = sc.nextLine();

        System.out.print("👉 Email             : ");
        String email = sc.nextLine();

        System.out.print("👉 Batch             : ");
        String batch = sc.nextLine();

        Student student = new Student(id, firstName, lastName, email, batch, true);
        InMemoryDb.studentList.add(student);

        System.out.println("|──────────────────────────────────────────────────────|");
        System.out.println("✅ Student Registered Successfully!");
        System.out.println("|──────────────────────────────────────────────────────|");

        manageStudentOptions();
    }

}
