package featuredatabase;

public class Rundemo {
    private static StudentView view;
    private Student Student;
    
    public Rundemo() {
	//fetch student record based on his roll no from the database
	       //Create a view : to write student details on console
	  	Student model  = new Student(view);
	  	StudentView view = new StudentView(Student);

	  	StudentController controller = new StudentController(model, view);
	       //update model data
	  	model = retrieveStudentFromDatabase();
	       controller.updateView();
	       controller.setStudentName("John");
	       controller.updateView();
	      
	       if (controller.getStudentAge() == 21) {
		controller.setStudentName("Berry");
		controller.updateView();
	       }
	    }

	    private static Student retrieveStudentFromDatabase(){
	       Student student = new Student(view);
	       student.setName("Robert");
	       student.setRollNo("10");
	       return student;
	    }
    
    public static void main(String[] args) {
	new Rundemo();
 }	
    
}
