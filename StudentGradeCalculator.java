package StartProject;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Student {
    String name;
    int rollNo;
    int[] marks;
    int total;
    double percentage;
    String grade;

    public Student(String name, int rollNo, int[] marks) {
        this.name = name;
        this.rollNo = rollNo;
        this.marks = marks;
    }

    public void calculateTotal() {
        total = 0;
        for (int mark : marks) {
            total += mark;
        }
    }

    public void calculatePercentage() {
        percentage = total / (double) marks.length;
    }

    public void assignGrade() {
        if (percentage >= 90) grade = "A+";
        else if (percentage >= 80) grade = "A";
        else if (percentage >= 70) grade = "B";
        else if (percentage >= 60) grade = "C";
        else if (percentage >= 50) grade = "D";
        else grade = "F";
    }

    public String getReport() {
        return "Roll No: " + rollNo + ", Name: " + name +
               ", Total: " + total + ", Percentage: " + String.format("%.2f", percentage) +
               "%, Grade: " + grade;
    }
}

public class StudentGradeCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of students: ");
        int numStudents = sc.nextInt();

        System.out.print("Enter number of subjects: ");
        int numSubjects = sc.nextInt();

        Student[] students = new Student[numStudents];

        for (int i = 0; i < numStudents; i++) {
            sc.nextLine(); // clear buffer
            System.out.println("\nEnter details for Student " + (i + 1));
            System.out.print("Name: ");
            String name = sc.nextLine();
            System.out.print("Roll No: ");
            int rollNo = sc.nextInt();

            int[] marks = new int[numSubjects];
            for (int j = 0; j < numSubjects; j++) {
                System.out.print("Enter marks for Subject " + (j + 1) + ": ");
                marks[j] = sc.nextInt();
            }

            Student student = new Student(name, rollNo, marks);
            student.calculateTotal();
            student.calculatePercentage();
            student.assignGrade();
            students[i] = student;
        }

        // Display report
        System.out.println("\n--- Student Grade Report ---");
        for (Student student : students) {
            System.out.println(student.getReport());
        }

        // Save report to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("StudentReport.txt"))) {
            for (Student student : students) {
                writer.write(student.getReport());
                writer.newLine();
            }
            System.out.println("\nReport saved to StudentReport.txt");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

        sc.close();
    }
}
