import java.io.*;
import java.util.*;

// Student class
class Student {
    private String name;
    private int rollNumber;
    private String grade;
    private String course;

    public Student(String name, int rollNumber, String grade, String course) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    public String getCourse() {
        return course;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String toFileString() {
        return name + "," + rollNumber + "," + grade + "," + course;
    }

    public void displayStudent() {
        System.out.println("Name       : " + name);
        System.out.println("Roll No.   : " + rollNumber);
        System.out.println("Grade      : " + grade);
        System.out.println("Course     : " + course);
        System.out.println("-----------------------------");
    }
}

// Student Management System class
public class StudentManagementSystem {
    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    private static final String FILE_NAME = "students.txt";

    public static void addStudent() {
        System.out.print("Enter student name: ");
        String name = sc.nextLine();

        if (name.trim().isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Enter roll number: ");
        int rollNumber;

        try {
            rollNumber = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid roll number.");
            return;
        }

        if (isRollNumberExists(rollNumber)) {
            System.out.println("Student with this roll number already exists.");
            return;
        }

        System.out.print("Enter grade: ");
        String grade = sc.nextLine();

        if (grade.trim().isEmpty()) {
            System.out.println("Grade cannot be empty.");
            return;
        }

        System.out.print("Enter course: ");
        String course = sc.nextLine();

        if (course.trim().isEmpty()) {
            System.out.println("Course cannot be empty.");
            return;
        }

        students.add(new Student(name, rollNumber, grade, course));
        saveToFile();

        System.out.println("Student added successfully.");
    }

    public static void removeStudent() {
        System.out.print("Enter roll number to remove: ");
        int rollNumber;

        try {
            rollNumber = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid roll number.");
            return;
        }

        Student student = searchStudentByRoll(rollNumber);

        if (student != null) {
            students.remove(student);
            saveToFile();
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    public static void searchStudent() {
        System.out.print("Enter roll number to search: ");
        int rollNumber;

        try {
            rollNumber = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid roll number.");
            return;
        }

        Student student = searchStudentByRoll(rollNumber);

        if (student != null) {
            System.out.println("\nStudent Found:");
            student.displayStudent();
        } else {
            System.out.println("Student not found.");
        }
    }

    public static void editStudent() {
        System.out.print("Enter roll number to edit: ");
        int rollNumber;

        try {
            rollNumber = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid roll number.");
            return;
        }

        Student student = searchStudentByRoll(rollNumber);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter new name: ");
        String name = sc.nextLine();

        System.out.print("Enter new grade: ");
        String grade = sc.nextLine();

        System.out.print("Enter new course: ");
        String course = sc.nextLine();

        if (name.trim().isEmpty() || grade.trim().isEmpty() || course.trim().isEmpty()) {
            System.out.println("Fields cannot be empty.");
            return;
        }

        student.setName(name);
        student.setGrade(grade);
        student.setCourse(course);

        saveToFile();

        System.out.println("Student information updated successfully.");
    }

    public static void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        System.out.println("\n===== Student List =====");

        for (Student student : students) {
            student.displayStudent();
        }
    }

    public static Student searchStudentByRoll(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    public static boolean isRollNumberExists(int rollNumber) {
        return searchStudentByRoll(rollNumber) != null;
    }

    public static void saveToFile() {
        try {
            FileWriter writer = new FileWriter(FILE_NAME);

            for (Student student : students) {
                writer.write(student.toFileString() + "\n");
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    public static void loadFromFile() {
        try {
            File file = new File(FILE_NAME);

            if (!file.exists()) {
                return;
            }

            Scanner fileReader = new Scanner(file);

            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] data = line.split(",");

                if (data.length == 4) {
                    String name = data[0];
                    int rollNumber = Integer.parseInt(data[1]);
                    String grade = data[2];
                    String course = data[3];

                    students.add(new Student(name, rollNumber, grade, course));
                }
            }

            fileReader.close();

        } catch (Exception e) {
            System.out.println("Error loading data.");
        }
    }

    public static void main(String[] args) {
        loadFromFile();

        int choice;

        do {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Edit Student");
            System.out.println("5. Display All Students");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice.");
                choice = 0;
            }

            switch (choice) {
                case 1:
                    addStudent();
                    break;

                case 2:
                    removeStudent();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    editStudent();
                    break;

                case 5:
                    displayAllStudents();
                    break;

                case 6:
                    saveToFile();
                    System.out.println("Thank you for using Student Management System.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 6);
    }
}