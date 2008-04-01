/*
 * The JastAdd Extensible Java Compiler (http://jastadd.org) is covered
 * by the modified BSD License. You should have received a copy of the
 * modified BSD license with this compiler.
 * 
 * Copyright (c) 2005-2008, Torbjorn Ekman
 * All rights reserved.
 */

/*
 * The JastAdd Extensible Java Compiler (http://jastadd.org) is covered
 * by the modified BSD License. You should have received a copy of the
 * modified BSD license with this compiler.
 * 
 * Copyright (c) 2005-2008, Torbjorn Ekman
 * All rights reserved.
 */

package server;

import java.util.*;
import java.net.*;
import java.io.*;

class TestClient {
    public static void main(String[] args) {
      if(args.length == 0) {
        System.err.println("No input file given");
        System.exit(1);
      }
      Socket socket = null;
      PrintWriter out = null;
      BufferedReader in = null;

      boolean error = false;
      try {
        socket = new Socket("localhost", 12345);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        File file = new File(".");
        String path = file.getCanonicalPath();
        out.println(path + "/" + args[0]);

        String msg = in.readLine();
        error = msg.equals("error");
  	    out.close();
	      in.close();
	      socket.close();
        
      } catch (UnknownHostException e) {
          System.err.println("Don't know about host: localhost");
          System.exit(1);
      } catch (IOException e) {
          System.err.println("Couldn't get I/O for "
                             + "the connection to: localhost");
          System.exit(1);
      }

      if(error) {
        System.out.println("error");
        System.exit(1);
      }
      else {
        System.out.println("ok");
      }
    }
}
