package ru.ipccenter.webshell.server;


class A implements Runnable {

    Shell shell;
    
    @Override
    public void run() {

	shell = new Shell();
	while (true) {
	    
	}
    }
    
}

public class ShellTest {

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {

	A a1 = new A();
	A a2 = new A();
	A a3 = new A();
	
	new Thread(a1).start();
	new Thread(a2).start();
	new Thread(a3).start();
	
	Shell s1 = new Shell();
	Shell s2 = new Shell();
	
	Thread.sleep(1000);
	
	System.out.println("Comparison contexts in different threads");
	System.out.println(a1.shell.context.equals(a2.shell.context));
	System.out.println(a1.shell.context.equals(a3.shell.context)); 
	System.out.println(a2.shell.context.equals(a3.shell.context));
	
	System.out.println("Comparison contexts in the same thread");
	System.out.println(s1.context.equals(s2.context));
	
	System.out.println("ok");
    }

}
