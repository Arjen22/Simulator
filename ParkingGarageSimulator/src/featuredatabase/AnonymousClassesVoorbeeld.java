package featuredatabase;

public class AnonymousClassesVoorbeeld {
	    
	    interface HelloWorld {
	        public void greet();
	        public void greetSomeone(String someone);
	    }
	  
	    public void sayHello() {
	        
	        class EnglishGreeting implements HelloWorld {
	            String name = "world";
	            public void greet() {
	                greetSomeone("world");
	            }
	            public void greetSomeone(String someone) {
	                name = someone;
	                System.out.println("Hello " + name);
	            }
	        }
	      
	        HelloWorld englishGreeting = new EnglishGreeting();
	        
	        HelloWorld frenchGreeting = new HelloWorld() {
	            String name = "tout le monde";
	            public void greet() {
	                greetSomeone("tout le monde");
	            }
	            public void greetSomeone(String someone) {
	                name = someone;
	                System.out.println("Salut " + name);
	            }
	        };
	        
	        HelloWorld spanishGreeting = new HelloWorld() {
	            String name = "mundo";
	            public void greet() {
	                greetSomeone("mundo");
	            }
	            public void greetSomeone(String someone) {
	                name = someone;
	                System.out.println("Hola, " + name);
	            }
	        };
	            
	        HelloWorld dutchGreeting = new HelloWorld() {
	            String name = "naam";
	            public void greet() {
	        	greetSomeone("naam");
	            }
	            public void greetSomeone(String someone) {
	        	name = someone;
	        	System.out.println("Hallo, " + name);
	            }
	        };
	        
	        englishGreeting.greet();
	        englishGreeting.greetSomeone("Jos");
	        frenchGreeting.greetSomeone("Fred");
	        spanishGreeting.greet();
	        dutchGreeting.greetSomeone("Jorick");
	    }

	    public static void main(String... args) {
		AnonymousClassesVoorbeeld  myApp =
	            new AnonymousClassesVoorbeeld();
	        myApp.sayHello();
	    }            
    }
