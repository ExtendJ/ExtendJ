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

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * This file is part of Beaver Parser Generator.                       *
 * Copyright (C) 2003,2004 Alexander Demenchuk <alder@softanvil.com>.  *
 * All rights reserved.                                                *
 * See the file "LICENSE" for the terms and conditions for copying,    *
 * distribution and modification of Beaver.                            *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package beaver;

import java.io.IOException;



/**
 * Defines an interface expected by a generated parser.
 */
public abstract class Scanner
{
	public static class Exception extends java.lang.Exception
	{
		public final int line;
		public final int column;
		
		public Exception(String msg)
		{
			this(0, 0, msg);
		}
		
		public Exception(int line, int column, String msg)
		{
			super(msg);
			this.line = line;
			this.column = column;
		}
	}

	public abstract Symbol nextToken() throws IOException, Scanner.Exception;
}
