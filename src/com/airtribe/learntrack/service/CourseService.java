package com.airtribe.learntrack.service;

import com.airtribe.learntrack.AirtribePortal;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.util.InMemoryDb;
import java.util.Scanner;

public class CourseService {

    public void manageCourse() {
        manageCourseOptions();
    }

    private void manageCourseOptions() {
        Scanner sc = new Scanner(System.in);
        int selectedOption;

        System.out.println("╭──────────────────────────────────────────────────────╮");
        System.out.println("│               🏫 COURSE MANAGEMENT PORTAL            │");
        System.out.println("├──────────────────────────────────────────────────────┤");
        System.out.println("│                                                      │");
        System.out.println("│   1. Add New Course                                  │");
        System.out.println("│   2. Search Course By ID                             │");
        System.out.println("│   3. Update Course Details                           │");
        System.out.println("│   4. Remove Course                                   │");
        System.out.println("│   5. View All Courses                                │");
        System.out.println("│   6. Back to Main Menu                               │");
        System.out.println("│                                                      │");
        System.out.println("├──────────────────────────────────────────────────────┤");
        System.out.print("│   Select an option (1-6): ");

        selectedOption = sc.nextInt();
        sc.nextLine();

        switch (selectedOption) {
            case 1 -> addNewCourse();
            case 2 -> searchCourseById();
            case 3 -> updateCourse();
            case 4 -> removeCourse();
            case 5 -> getAllCourses();
            case 6 -> backToMainMenu();
            default -> {
                System.out.println("❌ Invalid option! Please enter a number between 1-6.");
                manageCourseOptions();
            }
        }
    }

    private void backToMainMenu() {
        new AirtribePortal().managementOptions();
    }

    private void addNewCourse() {
        Scanner sc = new Scanner(System.in);

        System.out.println("|******************************************************|");
        System.out.println("|                  📝 COURSE REGISTRATION FORM        |");
        System.out.println("|******************************************************|");

        int id;
        while (true) {
            System.out.print("👉 Course ID       : ");
            if (sc.hasNextInt()) {
                id = sc.nextInt();
                sc.nextLine();

                if (id <= 0) {
                    System.out.println("❌ ID must be a positive number. Please try again.");
                    continue;
                }

                int finalId = id;
                boolean exists = InMemoryDb.courseList.stream()
                        .anyMatch(c -> c.id == finalId);
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

        System.out.print("👉 Course Name     : ");
        String courseName = sc.nextLine();

        System.out.print("👉 Description     : ");
        String description = sc.nextLine();

        System.out.print("👉 Duration (weeks): ");
        String duration = sc.nextLine();

        Course course = new Course();
        course.id = id;
        course.courseName = courseName;
        course.description = description;
        course.durationInWeeks = duration;
        course.active = true;

        InMemoryDb.courseList.add(course);

        System.out.println("|──────────────────────────────────────────────────────|");
        System.out.println("✅ Course Registered Successfully!");
        System.out.println("|──────────────────────────────────────────────────────|");

        manageCourseOptions();
    }

    private void searchCourseById() {
        Scanner sc = new Scanner(System.in);

        System.out.println("|──────────────────────────────────────────────────────|");
        System.out.print("|   Enter Course ID to Search: ");
        int cId = sc.nextInt();
        sc.nextLine();
        System.out.println("|──────────────────────────────────────────────────────|");

        try {
            Course c = InMemoryDb.courseList.stream()
                    .filter(course -> course.id == cId)
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException(
                            "❌ Course with ID " + cId + " not found!"
                    ));

            printCourseTableHeader();
            printCourseRow(c);
            printCourseTableFooter();

        } catch (EntityNotFoundException ex) {
            printCenteredError(ex.getMessage());
        }

        manageCourseOptions();
    }


    private void updateCourse() {
        Scanner sc = new Scanner(System.in);
        System.out.print("| Enter Course ID to Update: ");
        int cId = sc.nextInt();
        sc.nextLine();

        try {
            Course course = InMemoryDb.courseList.stream()
                    .filter(c -> c.id == cId)
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException(
                            "❌ Course with ID " + cId + " not found!"
                    ));

            System.out.println("🎯 Course Found: " + course.courseName);

            System.out.println("|─────────────────────────────────────────────────────────|");
            System.out.println("| Which field(s) do you want to update? (comma separated) |");
            System.out.println("| 1. Course Name                                          |");
            System.out.println("| 2. Description                                          |");
            System.out.println("| 3. Duration                                             |");
            System.out.print("| Enter choices (e.g., 1,3): ");

            String input = sc.nextLine();
            String[] choices = input.split(",");

            for (String ch : choices) {
                switch (ch.trim()) {
                    case "1" -> {
                        System.out.print("| Enter new Course Name: ");
                        course.courseName = sc.nextLine();
                    }
                    case "2" -> {
                        System.out.print("| Enter new Description: ");
                        course.description = sc.nextLine();
                    }
                    case "3" -> {
                        System.out.print("| Enter new Duration: ");
                        course.durationInWeeks = sc.nextLine();
                    }
                    default -> System.out.println("| ❌ Invalid choice: " + ch + " |");
                }
            }

            System.out.println("|******************************************************|");
            System.out.println("✅ Course Updated Successfully!");
            System.out.println("|******************************************************|");

        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
            System.out.println("|******************************************************|");
        }

        manageCourseOptions();
    }

    private void removeCourse() {
        Scanner sc = new Scanner(System.in);
        System.out.print("| Enter Course ID to Remove: ");
        int cId = sc.nextInt();
        sc.nextLine();

        try {
            Course course = InMemoryDb.courseList.stream()
                    .filter(c -> c.id == cId)
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException(
                            "❌ Course with ID " + cId + " not found!"
                    ));

            System.out.print("| Are you sure to remove this course? (Y/N): ");
            String confirm = sc.nextLine();

            if (confirm.equalsIgnoreCase("Y")) {
                course.active = false;
                System.out.println("✅ Course with ID " + cId + " has been removed.");
            } else {
                System.out.println("⚠ Removal cancelled!");
            }

        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        manageCourseOptions();
    }

    private void getAllCourses() {

        printCourseTableHeader();

        if (InMemoryDb.courseList.isEmpty()) {
            System.out.println("║              ❌ No courses found in the system              ║");
        } else {
            for (Course c : InMemoryDb.courseList) {
                printCourseRow(c);
            }
        }

        printCourseTableFooter();
        manageCourseOptions();
    }

    private void printCourseTableHeader() {
        System.out.println("╔═════════╦════════════════════╦══════════════════════════════════════════╦═════════════════╦════════╗");
        System.out.println("║ ID      ║ Course Name        ║ Description                              ║ Duration(Weeks) ║ Active ║");
        System.out.println("╠═════════╬════════════════════╬══════════════════════════════════════════╬═════════════════╬════════╣");
    }


    private void printCourseRow(Course c) {
        System.out.printf("║ %-7d ║ %-20s ║ %-38s ║ %-15s ║ %-6s ║%n",
                c.id,
                c.courseName == null ? "" : c.courseName,
                c.description == null ? "" : c.description,
                c.durationInWeeks == null ? "" : c.durationInWeeks,
                c.active ? "Yes" : "No");
    }

    private void printCourseTableFooter() {
        System.out.println("╚═════════╩════════════════════╩══════════════════════════════════════════╩═════════════════╩════════╝");
    }

    private void printCenteredError(String msg) {
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.printf("║ %-52s ║%n", msg);
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }


}
