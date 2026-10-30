// “I will practice academic and personal integrity and excellence of character and expect the same from others"
// Chella Drakeford, 1314292, CSC3280 002, Program 2: FSC Grade Book, 9/23/26

import java.util.*;

public class Main {
	public static void main(String[] args) {
		// create Scanner used to read all input
		Scanner in = new Scanner(System.in);

		// other variables
		// read number of courses and create the roster array
		int numCourses = Integer.parseInt(in.nextLine());
		FSCcourseRoster[] courses = new FSCcourseRoster[numCourses];

		// create one roster for each course
		for (int i = 0; i < numCourses; i++) {
			courses[i] = new FSCcourseRoster();
			courses[i].setCourseNumber(in.nextLine());
		}
		System.out.println("Welcome to the FSC Grade Book.");
		System.out.println();
		System.out.println("The following course(s) have been added to the database:");

		for (int i = 0; i < courses.length; i++) {
			System.out.println("\t" + courses[i].getCourseNumber());
		}

		// read first command before entering the loop
		String command = in.next();
		// continue commands until QUIT is entered
		while (!command.equals("QUIT")) {
			// ADDRECORD adds new student to a course roster
			if (command.equals("ADDRECORD")) {
				System.out.println("Command: ADDRECORD");
				String courseNumber = in.next();
				int ID = in.nextInt();
				String firstName = in.next();
				String lastName = in.next();

				int[] examGrades = new int[3];
				examGrades[0] = in.nextInt();
				examGrades[1] = in.nextInt();
				examGrades[2] = in.nextInt();

				int courseIndex = -1;

				// Find the course in the course array.
				for (int i = 0; i < courses.length; i++) {
					if (courses[i].getCourseNumber().equals(courseNumber)) {
						courseIndex = i;
						break;
					}
				}

				if (courseIndex == -1) {
					System.out.printf("\tERROR: cannot add student. Course \"%s\" does not exist.\n", courseNumber);
				} else {

					// Insert creates the Student and places it in the roster.
					courses[courseIndex].insert(courseNumber, ID, firstName, lastName, examGrades);

					// Find the actual Student that was inserted.
					Student student = courses[courseIndex].findNode(ID);

					if (student != null) {
						// Calculate the grade on the Student actually in the roster.
						student.calculateFinalGrade();

						System.out.printf("\t%s %s (ID# %d) has been added to %s.\n", firstName, lastName, ID, courseNumber);

						System.out.printf("\tFinal Grade: %.2f (%c).\n", student.getFinalGrade(), student.getLetterGrade());
					}
				}
			}

			// DELETERECORD removes a student from course
			else if (command.equals("DELETERECORD")) {
				System.out.println("Command: DELETERECORD");
				int ID = in.nextInt();
				boolean deleteFound = false;
				// find course before trying to delete student
				for (int i = 0; i < courses.length; i++) {
					Student student = courses[i].findNode(ID);
					if (student != null) {
						String firstName = student.getFirstName();
						String lastName = student.getLastName();
						String courseNumber = courses[i].getCourseNumber();
						courses[i].delete(ID);
						System.out.printf("\t%s %s (ID# %d) has been deleted from %s.\n", firstName, lastName, ID, courseNumber);
						deleteFound = true;
					}
				}
				// display error when student is not found
				if (!deleteFound) {
				System.out.printf("\tERROR: cannot delete student. ID# %d does not exist.\n", ID);
				}
			}

			// SEARCHBYID searches every course for one student ID
			else if (command.equals("SEARCHBYID")) {
				int ID = in.nextInt();
				boolean idFound = false;

				for (int i = 0; i < courses.length; i++) {
					if (courses[i].searchID(ID)) {
						Student student = courses[i].findNode(ID);

						if (student != null) {
							System.out.printf("%s %s (ID# %d) is enrolled in %s.\n", student.getFirstName(),
									student.getLastName(), ID, courses[i].getCourseNumber());

							idFound = true;
						}
					}
				}

				if (!idFound) {
					System.out.printf("ERROR: Student ID# %d was not found.\n", ID);
				}
			}
			// SEARCHBYNAME searches every course for student's name
			else if (command.equals("SEARCHBYNAME")) {
				String firstName = in.next();
				String lastName = in.next();
				boolean namefound = false;

				for (int i = 0; i < courses.length; i++) {
					if (courses[i].searchName(firstName, lastName)) {

						Student current = courses[i].getHeadNode();

						while (current != null) {
							if (current.getFirstName().equals(firstName) && current.getLastName().equals(lastName)) {

								System.out.printf("%s %s (ID# %d) is enrolled in %s.\n", current.getFirstName(),
										current.getLastName(), current.getID(), courses[i].getCourseNumber());

								namefound = true;
								break;
							}
							current = current.getNext();
						}
					}
				}
				if (!namefound) {
					System.out.printf("ERROR: Student %s %s was not found.\n", firstName, lastName);
				}
			}

			// DISPLAYSTUDENTS displays one course's entire roster
			else if (command.equals("DISPLAYSTUDENTS")) {
				String courseNumber = in.next();
				System.out.printf("Command: DISPLAYSTUDENTS (%s)\n", courseNumber);

				if (courseNumber.equals("ALL")) {

					for (int i = 0; i < courses.length; i++) {
						courses[i].printRoster();
					}

				} else {
					int courseIndex = -1;

					// Find the requested course.
					for (int i = 0; i < courses.length; i++) {
						if (courses[i].getCourseNumber().equals(courseNumber)) {
							courseIndex = i;
							break;
						}
					}

					if (courseIndex == -1) {
						System.out.printf("\tERROR: Course \"%s\" does not exist.\n", courseNumber);
					} else if (courses[courseIndex].isEmpty()) {
						System.out.printf("\tERROR: there are no student records for %s.\n", courseNumber);
					} else {
						courses[courseIndex].printRoster();
					}
				}
			}

			// DISPLAYSTATS displays statistics for one course or all courses.
			else if (command.equals("DISPLAYSTATS")) {
				String courseNumber = in.next();
				System.out.printf("Command: DISPLAYSTATS (%s)\n", courseNumber);
				if (courseNumber.equals("ALL")) {
					// calculate statistics across every course
					int totalStudents = 0;
					double totalGrades = 0.0;
					double highest = 0.0;
					double lowest = Double.MAX_VALUE;

					int[] gradeCounts = new int[5];

					// walk through every course and every student
					for (int i = 0; i < courses.length; i++) {
						Student current = courses[i].getHeadNode();

						while (current != null) {
							double grade = current.getFinalGrade();
							totalStudents++;
							totalGrades += grade;

							if (grade > highest) {
								highest = grade;
							}
							if (grade < lowest) {
								lowest = grade;
							}

							if (current.getLetterGrade() == 'A') {
								gradeCounts[0]++;
							} else if (current.getLetterGrade() == 'B') {
								gradeCounts[1]++;
							} else if (current.getLetterGrade() == 'C') {
								gradeCounts[2]++;
							} else if (current.getLetterGrade() == 'D') {
								gradeCounts[3]++;
							} else {
								gradeCounts[4]++;
							}

							current = current.getNext();
						}
					}
					if (totalStudents == 0) {
						lowest = 0.0;
					}

					double average = 0.0;

					if (totalStudents > 0) {
						average = totalGrades / totalStudents;
					}

					System.out.println("Statistical Results for All Courses:");
					System.out.printf(
							"\tTotal number of student records: %d\n",
							totalStudents);
					System.out.printf(
							"\tAverage Score: %.2f\n", average);
					System.out.printf(
							"\tHighest Score: %.2f\n", highest);
					System.out.printf(
							"\tLowest Score:  %.2f\n", lowest);

					char[] letters = { 'A', 'B', 'C', 'D', 'F' };

					for (int i = 0; i < letters.length; i++) {
						double percent = 0.0;

						if (totalStudents > 0) {
							percent = (gradeCounts[i] * 100.0) / totalStudents;
						}

						System.out.printf(
								"\tTotal '%c' Grades: %d (%.2f%% of class)\n",
								letters[i], gradeCounts[i], percent);
					}

				} else {
					// find and display statistics for one course
					boolean foundStudent = false;

					for (int i = 0; i < courses.length; i++) {
						if (courses[i].getCourseNumber().equals(courseNumber)) {

							courses[i].printStats();
							foundStudent = true;
							break;
						}
					}

					if (!foundStudent) {
						System.out.printf(
								"\tERROR: Course \"%s\" does not exist.\n",
								courseNumber);
					}
				}
			}
			// read the next command after finishing current command
			command = in.next();
		}
		// close the input when the program is finished
		System.out.println("Thank you for using the the FSC Grade Book.");
		System.out.println();
		System.out.println("Goodbye.");
		in.close();
	}
}
