// “I will practice academic and personal integrity and excellence of character and expect the same from others"
// Chella Drakeford, 1314292, CSC3280 002, Program 2: FSC Grade Book, 9/23/26

public class Student {
    // Data members
    // Private: Information that belongs to one student
    private String courseNumber; // identifies course student is enrolled in
    private int ID; // stores unique student ID number
    private String firstName;
    private String lastName;
    private int[] examGrades; // stores exam grades in order: exam1, exam2, final exam
    private double finalGrade; // stores weighted final grade after calculateFinalGrade() is called
    private char letterGrade; // stores letter grade associated calculated final grade
    // Static: Class-wide information that belongs to Student as a whole
    // is a counter for the number of students
    private static int numStudents;
    private Student next; // references the next Student in the course roster's linked list
    
    // CONSTRUCTORS
    // creates a Student using the course, ID, name, and 3 exam grades provided
    public Student(String courseNumber, int ID, String firstName, String lastName, int[] examGrades) {
        // finalGrade and letterGrade not needed in constructor
        // instance variables get default values, these values are changed in calculateFinalGrade()
        this.courseNumber = courseNumber;
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.examGrades = examGrades;
        numStudents++; // increases shared student counter
        next = null; // when a student is first created/added
                     // they are not connected to another Student yet   
    }

    // GETTERS and SETTERS
    public String getCourseNumber() {
        return courseNumber;
    }
    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int[] getExamGrades() {
        return examGrades;
    }
    public void setExamGrades(int[] examGrades) {
        this.examGrades = examGrades;
    }

    public double getFinalGrade() {
        return finalGrade;
    }
    public void setFinalGrade(double finalGrade) {
        this.finalGrade = finalGrade;
    }

    public char getLetterGrade() {
        return letterGrade;
    }
    public void setLetterGrade(char letterGrade) {
        this.letterGrade = letterGrade;
    }

    public static int getNumStudents() {
        return numStudents;
    }
    // numStudents does not need a setter
    // if we created one it would set it to a particular value
    // which we do not want because it is a counter that increases as students are added

    public Student getNext() {
        return next;
    }
    public void setNext(Student next) {
        this.next = next;
    }

    // final grade calculation
    public void calculateFinalGrade() {
        // method will use examGrades[0], examGrades[1], examGrades[2] 
        // to compute finalGrade and letterGrade
        finalGrade = (examGrades[0] * 0.30) + (examGrades[1] * 0.30) + (examGrades[2] * 0.40);
        // determining the letter grade based on the final grade score
        if (finalGrade >= 90.00) {
            letterGrade = 'A';
        }
        else if (finalGrade >= 80.00) {
            letterGrade = 'B';
        }
        else if (finalGrade >= 70.00) {
            letterGrade = 'C';
        }
        else if (finalGrade >= 60.00) {
            letterGrade = 'D';
        }
        else {
            letterGrade = 'F';
        }
    }
}