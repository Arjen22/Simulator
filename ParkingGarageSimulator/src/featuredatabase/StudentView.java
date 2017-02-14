package featuredatabase;

public class StudentView {
    private Student model;
    
    public StudentView (Student model) {
	this.model = model;
    }
    
    public void printStudentDetails(String studentName, String studentRollNo, int age){
       System.out.println("Student: ");
       System.out.println("Name: " + studentName);
       System.out.println("Roll No: " + studentRollNo);
       System.out.println("Student's age: " + age);   
       // Sets the age for every student to 21 in the model;
       //model.setAge(21);
    }
 }
