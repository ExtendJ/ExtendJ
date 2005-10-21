package server;

import AST.*;

import java.io.*;
import java.net.*;
import java.util.*;

class TestServer {

  public static void main(String args[]) {
    Program program = new Program();
    ServerSocket server = null;
    try {
      server = new ServerSocket(12345);
    } catch(IOException e) {
      System.err.println("Could not listen to port 12345");
      System.exit(1);
    }

    System.out.println("Server active");
    while(true) {
      Socket socket = null;
      try {
        socket = server.accept();
      } catch (IOException e) {
        System.err.println("Accept failed");
        System.exit(1);
      }

      try {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String msg = in.readLine();
        if(msg.equals("shutdown")) {
          System.out.println("Exiting");
          System.exit(0);
        }
        else if(msg.equals("reset")) {
          program = new Program();
          System.out.println("Reset");
        }
        else {
          String path = msg;
          ClassFile.pushClassPath(msg);
          msg = in.readLine();
          boolean error = false;
          String errorMessage = "";
          while (!msg.equals("end")) {
            if(msg.length() > 5) {
              msg = msg.substring(0, msg.length()-5);
              System.out.println("Processing: " + msg);
              try {
                CompilationUnit cu = new ClassFile(msg).getCompilationUnit();
                //CompilationUnit cu = JavaCompiler.parse(path + msg);
                for(int k = 0; k < cu.getNumTypeDecl(); k++) {
                  String name = ((TypeDecl)cu.getTypeDeclListNoTransform().getChildNoTransform(k)).fullName();
                  for(int i = 0; i < program.getNumCompilationUnit(); i++) {
                    CompilationUnit unit = program.getCompilationUnit(i);
                    for(int j = 0; j < unit.getNumTypeDecl(); j++) {
                      if(unit.getTypeDecl(j).fullName().equals(name)) {
                        program = new Program();
                      }
                    }
                  }
                }
                program.addCompilationUnit(cu);
                String prettyPrint = cu.toString();
                Collection collection = new LinkedList();
                cu.errorCheck(collection);

                System.out.println("Errors:");
                for(Iterator iter = collection.iterator(); iter.hasNext(); ) {
                  String s = (String)iter.next();
                  System.out.println(s);
                  if(!s.equals(""))
                    error = true;
                }
              }
              catch (FileNotFoundException e) {
                System.err.println(e);
                error = false;
              }
              catch (IOException e) {
                e.printStackTrace();
                error = true;
              }
              catch (Error e) {
                errorMessage = msg + ".java:" + e.toString().substring(e.toString().indexOf(':')+2);
                System.err.println(msg + ":" + e.toString().substring(e.toString().indexOf(':')+2));
                e.printStackTrace();
                //System.err.println(e);
                //e.printStackTrace();
                error = true;
              }
              catch (Exception e) {
                errorMessage = msg + ":" + e.toString().substring(e.toString().indexOf(':')+2);
                System.err.println(e.toString().substring(e.toString().indexOf(':')+2));
                e.printStackTrace();
                error = true;
              }
            }
            msg = in.readLine();
          }
          out.println(error ? "error:" + errorMessage : "ok");
          out.flush();
          System.out.println("Done");
          ClassFile.popClassPath();
        }
        out.close();
        in.close();
      } catch(IOException e) {
        System.err.println("IOException");
        System.exit(1);
      }
   }
    
      
    
    
    //long code = System.currentTimeMillis() - start - program.parseTime;
    //program.codeGen();
    //
    //program.printTypes();
    //System.err.println("Parse: " + program.parseTime);
    //System.err.println("Analysis + print: " + code);
  }

  
}
