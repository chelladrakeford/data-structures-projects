//  “I will practice academic and personal integrity and excellence of character and expect the same from others"
// Chella Drakeford, 1314292, CSC3280 002, Program 2: FSC Grade Book, 9/23/26

// manages linked list of students for one course
public class FSCcourseRoster {
    // Data members
    // head: reference variable to the first node of the list
    private Student head;
    private String courseNumber;
    
    // initializes a course roster with the given course number & an empty linked list
    // CONSTRUCTORS
    public FSCcourseRoster() {
        head = null;
    }

    // Getter and setter for courseNumber
    public Student getHeadNode() {
        return head;
    }
    public String getCourseNumber() {
        return courseNumber;
    }
	public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

	// methods that are used on Linked Lists
    // **************************************
    // checks whether the course roster contains any students
	public boolean isEmpty() {
		return head == null;
	}
	
    // searches roster for student with specified ID
	public boolean searchID(int ID) {
		return searchID(head, ID);
	}
    // traverses linked list and returns true when the specified student ID is found
	private boolean searchID(Student head, int ID) {
		Student helpPtr = head;
		while (helpPtr != null) {
			if (helpPtr.getID() == ID) {
				return true;
            }
            helpPtr = helpPtr.getNext();			
	    }
        return false;
    }

    // searches roster for student with the specified first & last name
    public boolean searchName(String firstName, String lastName) {
        return searchName(head, firstName, lastName);
    }
    // traverses linked list and compares each student's first and last name
    private boolean searchName(Student head, String firstName, String lastName) {
        Student helpPtr = head;
        while (helpPtr != null) {
            if (helpPtr.getFirstName().equals(firstName) && helpPtr.getLastName().equals(lastName)) {
                return true;
            }
            helpPtr = helpPtr.getNext();
        }
        return false;
    }

    // finds and returns the student node with specified ID
	public Student findNode(int ID) {
		return findNode(head, ID);
	}
    // traverses linked list until the student with specified ID is found
	private Student findNode(Student head, int ID) {
		Student helpPtr = head;
		while (helpPtr != null) {
			if (helpPtr.getID() == ID) {
				return helpPtr;
            }
            helpPtr = helpPtr.getNext();			
        }
		return null;
	}
	
    // inserts new student into roster while maintaining ascending ID order
	public void insert(String courseNumber, int ID, String firstName, String lastName, int[] examGrades) {
        head = insert(head, courseNumber, ID, firstName, lastName, examGrades);
    }
    private Student insert(Student head, String courseNumber, int ID, String firstName, String lastName, int[] examGrades) {
        // handles insertion when list is empty or new student belongs at beginning
        if (head == null || head.getID() > ID) {
            Student newNode = new Student(courseNumber, ID, firstName, lastName, examGrades);
            newNode.setNext(head);
            head = newNode;
            return head;
		}
        // ELSE, insert the new node at the correct location
        else {
            // traverse to the correct insertion location with a helpPtr
            Student helpPtr = head;
            while (helpPtr.getNext() != null) {
                if (helpPtr.getNext().getID() > ID) {
                    break; // found spot and break out of the while loop
                }
                helpPtr = helpPtr.getNext();
            }
            // make the new node and set its next to point to the successor node
			// make the predecessor node point to the new node
            Student newNode = new Student(courseNumber, ID, firstName, lastName, examGrades);
            newNode.setNext(helpPtr.getNext());
            helpPtr.setNext(newNode);
        }
        return head;
    }

    // removes the student with the specified ID from the course roster
    public void delete(int ID) {
		head = delete(head, ID);
    }
    private Student delete(Student head, int ID) {
        // handles deletion of first student separately from students elsewhere in list
        // can only delete if the list has nodes and is not empty
		if (head != null) {
			// IF the head (first node) has the data value we are wanting to delete
			// wanted value is found & delete by skipping the node and making head point to the next node
			if (head.getID() == ID) {
				head = head.getNext();
			}
			// ELSE, the data could be somewhere else in the list so one must traverse and look for it
			else {
				// traverse to find the data that needs to be deleted with helpPtr
				Student helpPtr = head;
				// traverse to correct deletion point
				while (helpPtr.getNext() != null) {
					if (helpPtr.getNext().getID() == ID) {
						helpPtr.setNext(helpPtr.getNext().getNext());
						break; // deleted the value and should break out of the while loop
					}
					helpPtr = helpPtr.getNext();
				}
			}
			// return the possibly updated head of the list
			return head;
		}
		return head;
    }

    // prints each student's info, exam grades, final grade, & letter grade
    public void printRoster() {
        printRoster(head);
    }
    private void printRoster(Student head) {
        Student helpPtr = head;
        while (helpPtr != null) {
            System.out.printf("\t%s %s (ID# %d):\n\t\tExam 1:       %d\n\t\tExam 2:       %d\n\t\tFinal Exam:   %d\n\t\tFinal Grade:  %.2f\n\t\tLetter Grade: %c\n", helpPtr.getFirstName(), helpPtr.getLastName(), helpPtr.getID(), helpPtr.getExamGrades()[0], helpPtr.getExamGrades()[1], helpPtr.getExamGrades()[2], helpPtr.getFinalGrade(), helpPtr.getLetterGrade());
            helpPtr = helpPtr.getNext();
        }
    }

    // calculates and prints statistical info for all students in the course
    public void printStats() {
        printStats(head);
    }
    private void printStats(Student head) {
        // tracks number of students, grade totals, and overall score stats
        Student helpPtr = head;
        int total_students = 0;
        double highest = 0.0;
        double lowest = Integer.MAX_VALUE;
        double total_final = 0.0;
        // stores number of students receiving each letter grade
        // matches each letter grade to its corresponding position in the counts array
        int[] counts = {0, 0, 0, 0, 0};
        char[] letters = {'A', 'B', 'C', 'D', 'F'};
        // stores percentage of students receiving each letter grade
        double[] percent = {0.0, 0.0, 0.0, 0.0, 0.0};

        // traverses roster while tracking grades, totals, highest score, and lowest score
        while (helpPtr != null) {
            // updates the highest and lowest scores as each stuent is examined
            if (highest < helpPtr.getFinalGrade()) {
                highest = helpPtr.getFinalGrade();
            }
            if (lowest > helpPtr.getFinalGrade()) {
                lowest = helpPtr.getFinalGrade();
            }
            // determines which letter grade category current student belongs to
            for (int i = 0; i < letters.length; i++) {
                if (letters[i] == helpPtr.getLetterGrade()) {
                    counts[i]++;
                }
            }
            // adds student's final grade to the total and counts the student
            total_final += helpPtr.getFinalGrade();
            total_students += 1;
            helpPtr = helpPtr.getNext();
        }
        double average = 0.0;
        // calculates average and grade percentages only when the roster contains students
        if (total_students > 0) {
            average = total_final / total_students;
            for (int i = 0; i < percent.length; i++) {
                percent[i] = (double) counts[i] / total_students * 100;
            }
        }
        // sets lowest score to 0 when the roster is empty
        else {
            lowest = 0.0;
        }
        // displays calculated course statistics using the required output format
        System.out.printf("\tTotal number of student records: %d\n\tAverage Score: %.2f\n\tHighest Score: %.2f\n\tLowest Score:  %.2f\n\tTotal 'A' Grades: %d (%.2f%% of class)\n\tTotal 'B' Grades: %d (%.2f%% of class)\n\tTotal 'C' Grades: %d (%.2f%% of class)\n\tTotal 'D' Grades: %d (%.2f%% of class)\n\tTotal 'F' Grades: %d (%.2f%% of class)\n", total_students, average, highest, lowest, counts[0], percent[0], counts[1], percent[1], counts[2], percent[2], counts[3], percent[3], counts[4], percent[4]);
    }
}