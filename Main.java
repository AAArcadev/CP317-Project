import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // Base class Student
    public static class Student {
        private String studentID;

        public Student(String studentID) {
            this.studentID = studentID;
        }

        public String getStudentID() {
            return studentID;
        }

        public void setStudentID(String studentID) {
            this.studentID = studentID;
        }
    }

    // Derived class StudentName
    public static class StudentName extends Student {
        private String studentName;

        public StudentName(String studentID, String studentName) {
            super(studentID);
            this.studentName = studentName;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }
    }

    // Derived class StudentCourse
    public static class StudentCourse extends Student {
        private String courseID;
        private float test1;
        private float test2;
        private float test3;
        private float finalExam;

        public StudentCourse(String studentID, String courseID, float test1, float test2, float test3, float finalExam) {
            super(studentID);
            this.courseID = courseID;
            this.test1 = test1;
            this.test2 = test2;
            this.test3 = test3;
            this.finalExam = finalExam;
        }

        public String getCourseID() {
            return courseID;
        }

        public void setCourseID(String courseID) {
            this.courseID = courseID;
        }

        public float getTest1() {
            return test1;
        }

        public void setTest1(float test1) {
            this.test1 = test1;
        }

        public float getTest2() {
            return test2;
        }

        public void setTest2(float test2) {
            this.test2 = test2;
        }

        public float getTest3() {
            return test3;
        }

        public void setTest3(float test3) {
            this.test3 = test3;
        }

        public float getFinalExam() {
            return finalExam;
        }

        public void setFinalExam(float finalExam) {
            this.finalExam = finalExam;
        }
    }

    // StudentOutput class
    public static class StudentOutput {
        private String studentID;
        private String studentName;
        private String courseID;
        private float finalGrade;

        public StudentOutput(String studentID, String studentName, String courseID, float finalGrade) {
            this.studentID = studentID;
            this.studentName = studentName;
            this.courseID = courseID;
            this.finalGrade = finalGrade;
        }

        public String getStudentID() {
            return studentID;
        }

        public void setStudentID(String studentID) {
            this.studentID = studentID;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getCourseID() {
            return courseID;
        }

        public void setCourseID(String courseID) {
            this.courseID = courseID;
        }

        public float getFinalGrade() {
            return finalGrade;
        }

        public void setFinalGrade(float finalGrade) {
            this.finalGrade = finalGrade;
        }
    }

    // Function to read a file and store its lines in an array list
    public static void readFileToArray(String filename, ArrayList<String> outputList) {
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String dataLine = myReader.nextLine();
                outputList.add(dataLine); // Adds the line of data to the array
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file: " + filename);
            e.printStackTrace();
        }
    }

    // Function that takes an array with strings with delimeter ',' and turns into
    // an array of StudentName objects
    public static StudentName[] elementToStudentName(ArrayList<String> outputList) {
        final StudentName[] studentNameObjectArray = new StudentName[outputList.size()];
        for (int i = 0; i < outputList.size(); i++) {
            String[] studentNameArray = outputList.get(i).split(", ");
            studentNameObjectArray[i] = new StudentName(studentNameArray[0], studentNameArray[1]);
        }
        return studentNameObjectArray;
    }

    // Function that takes an array with strings with delimeter ',' and turns into
    // an array of StudentCourse objects
    public static StudentCourse[] elementToStudentCourse(ArrayList<String> outputList) {
        final StudentCourse[] studentCourseObjectArray = new StudentCourse[outputList.size()];
        for (int i = 0; i < outputList.size(); i++) {
            String[] studentCourseArray = outputList.get(i).split(", ");
            studentCourseObjectArray[i] = new StudentCourse(studentCourseArray[0], studentCourseArray[1],
                    Float.parseFloat(studentCourseArray[2]), Float.parseFloat(studentCourseArray[3]),
                    Float.parseFloat(studentCourseArray[4]), Float.parseFloat(studentCourseArray[5]));
        }
        return studentCourseObjectArray;
    }

    // Function that returns an array list of student output objects
    public static ArrayList<StudentOutput> createStudentOutputArray(StudentName[] studentNameArray,
            StudentCourse[] studentCourseArray) {
        final ArrayList<StudentOutput> studentOutputObjectArray = new ArrayList<StudentOutput>();
        for (StudentName studentName : studentNameArray) {
            for (StudentCourse studentCourse : studentCourseArray) {
                if (studentName.getStudentID().equals(studentCourse.getStudentID())) {
                    float finalGrade = ((studentCourse.getTest1() * 0.2f) + (studentCourse.getTest2() * 0.2f)
                            + (studentCourse.getTest3() * 0.2f) + (studentCourse.getFinalExam() * 0.6f)); // Calculate the final grade
                    studentOutputObjectArray.add(new StudentOutput(studentName.getStudentID(), studentName.getStudentName(),
                            studentCourse.getCourseID(), finalGrade)); // Add new student output object containing the students information
                }
            }
        }
        return studentOutputObjectArray;
    }

    public static void createOutputFile() {
        try {
            File myObj = new File("StudentOutputFile.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }
    }

    public static void writeOutputFile(ArrayList<StudentOutput> studentOutputArrayList) {
        try {
            FileWriter myWriter = new FileWriter("StudentOutputFile.txt");
            for (StudentOutput output : studentOutputArrayList) {
                String writeString = String.format("%s, %s, %s, %.1f\n", 
                        output.getStudentID(), output.getStudentName(), output.getCourseID(), output.getFinalGrade());
                myWriter.write(writeString);
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        final ArrayList<String> courseFileLines = new ArrayList<String>();
        final ArrayList<String> nameFileLines = new ArrayList<String>();

        readFileToArray("CourseFile.txt", courseFileLines);
        readFileToArray("NameFile.txt", nameFileLines);

        StudentName[] studentNameObjectArray = elementToStudentName(nameFileLines);
        StudentCourse[] studentCourseObjectArray = elementToStudentCourse(courseFileLines);

        ArrayList<StudentOutput> studentOutputObjectArray = createStudentOutputArray(studentNameObjectArray,
                studentCourseObjectArray);
        createOutputFile();
        writeOutputFile(studentOutputObjectArray);
    }
}
