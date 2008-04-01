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

/**
 * An "interface" to Java code executed when a production is reduced.
 */
public abstract class Action
{
	static public final Action NONE = new Action()
	{
		public Symbol reduce(Symbol[] args, int offset)
		{
			return new Symbol(null);
		}
	};
	
	static public final Action RETURN = new Action()
	{
		public Symbol reduce(Symbol[] args, int offset)
		{
			return args[offset + 1];
		}
	};
	
	/**
	 * Am action code that is executed when the production is reduced.
	 *
	 * @param args   an array part of which is filled with this action arguments
	 * @param offset to the last element <b>BEFORE</b> the first argument of this action
	 * @return a symbol or a value of a LHS nonterminal
	 */
	public abstract Symbol reduce(Symbol[] args, int offset);
}
