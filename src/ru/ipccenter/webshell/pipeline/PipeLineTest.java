package ru.ipccenter.webshell.pipeline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



public class PipeLineTest {

    /**
     * @param args
     */
    public static void main(String[] args)
	    throws IOException {

	Runtime rt = java.lang.Runtime.getRuntime();
	
	Process p1 = rt.exec("ps -aux");
	Process p2 = rt.exec("grep ivan");
	Process p3 = rt.exec("grep eclipse");
        
	PipeLine line = null;
	try {
	    line = new PipeLine(p1, p2, p3);
	    line.exec();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    
	    if (line != null) {
		BufferedReader r = new BufferedReader(new InputStreamReader(line.getStderr()));
	    	String s = null;
	    	while ((s = r.readLine()) != null) {
	    	    System.out.println(s);
	    	}
	    }
	}
	
	if (line != null) {
	    BufferedReader r = new BufferedReader(new InputStreamReader(line.getStdout()));
	    String s = null;
	    while ((s = r.readLine()) != null) {
		System.out.println(s);
	    }
	}
    }
}
