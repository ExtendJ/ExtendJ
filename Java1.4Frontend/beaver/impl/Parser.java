/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * This file is part of Beaver Parser Generator.                       *
 * Copyright (C) 2003,2004 Alexander Demenchuk <alder@softanvil.com>.  *
 * All rights reserved.                                                *
 * See the file "COPYRIGHT" for the terms and conditions for           *
 * copying, distribution and modification of Beaver.                   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package beaver.impl;

import java.io.IOException;

import beaver.core.Scanner;
import beaver.core.Token;

/**
 * Almost complete implementation of a LALR parser. Two components that it lacks to parse a concrete grammar --
 * rule actions and state machine tables -- are provided by a generated subclass.
 */
public abstract class Parser
{
	public static class Exception
	    extends java.lang.Exception
	{
		Exception(String msg)
		{
			super(msg);
		}
	}

	/**
	 * Simulator is a stripped (of action code) version of a parser that will try to parse ahead token
	 * stream after a syntax error. The simulation is considered successful if 3 tokens were shifted
	 * successfully. If during simulation this parser enconters an error it drops the first token it
	 * tried to use and restarts the simulated parsing.
	 * <p/>
	 * Note: Without a special "error" rule present in a grammar, which the real parser will try to
	 * shift at the beginning of an error recovery, simulation continues without removing anything from
	 * the original states stack. This often will lead to cases when no parsing ahead will recover the
	 * parser from a syntax error.
	 */
	private static class Simulator
	{
		private Parser parser;

		private short[] states;
		private int top;
		private Token[] tokens = new Token[3];
		private int token_index;

		Simulator(Parser parser)
		{
			this.parser = parser;
			states = new short[parser.states.length];
		}

		private void startSimulation(Token token, Scanner source)
		    throws IOException, Scanner.Exception
		{
			if (parser.states.length > states.length)
			{
				states = new short[parser.states.length];
			}
			System.arraycopy(parser.states, 0, states, 0, (top = parser.top) + 1);
			fillInTokenBuffer(token, source);
		}

		Token[] simulateParse(Token token, Scanner source)
		    throws IOException, Scanner.Exception
		{
			startSimulation(token, source);

			for (token = nextToken(); token != null; token = nextToken())
			{
				do
				{
					short act = parser.tables.findParserAction(states[top], token.getId());
					if (act > 0)
					{
						shift(act);
						token = null;
					}
					else if (act < 0)
					{
						if (act == parser.accept_action_id)
							return tokens;

						if ((act = reduce(~act)) > 0)
							shift(act);
						else if (act == parser.accept_action_id)
							return tokens;
						else
						{
							discardLeftmostToken(source);
							resyncStatesStack();
							token = null;
						}
					}
					else // still an error
					{
						if (token.getId() == 0)
							return null;

						discardLeftmostToken(source);
						resyncStatesStack();
						token = null;
					}
				}
				while (token != null);
			}
			return tokens;
		}

		private void fillInTokenBuffer(Token token, Scanner source)
		    throws IOException, Scanner.Exception
		{
			tokens[0] = token;
			for (int i = 1; i < tokens.length && token != null && token.getId() != 0; i++)
			{
				tokens[i] = token = source.nextToken();
			}
			token_index = 0;
		}

		private void discardLeftmostToken(Scanner source)
		    throws IOException, Scanner.Exception
		{
			System.arraycopy(tokens, 1, tokens, 0, tokens.length - 1);
			tokens[tokens.length - 1] = source.nextToken();
			token_index = 0;
		}

		void resyncStatesStack()
		{
			if (top < parser.top)
				System.arraycopy(parser.states, top, states, top, parser.top - top + 1);
			else if (top == parser.top)
				states[top] = parser.states[top];
			top = parser.top;
		}

		private Token nextToken()
		{
			return token_index < tokens.length ? tokens[token_index++] : null;
		}

		private void increaseStackCapacity()
		{
			short[] new_states = new short[states.length * 2];
			System.arraycopy(states, 0, new_states, 0, states.length);
			states = new_states;
		}

		private void shift(short state)
		{
			if (++top == states.length)
				increaseStackCapacity();
			states[top] = state;
		}

		private short reduce(int rule_id)
		{
			int rule_info = parser.tables.rule_infos[rule_id];
			int rhs_size = rule_info & 0xFFFF;
			short lhs_nt_id = (short) (rule_info >>> 16);

			top -= rhs_size;

			return parser.tables.findPostReduceAction(states[top], lhs_nt_id);
		}
	}

	/** automaton tables. */
	private final ParsingTables tables;

	/** "List" of reduce actions */
	private final Action[] actions;

	/** Cached ID of the ACCEPT action. */
	private final short accept_action_id;

	/** Parser's stack. */
	private short[] states;

	/**
	 * Semantic values associated with tokens on the stack. They are used (and produced) by
	 * action routines.
	 */
	private Object[] values;

	/** Index of the stack's top element, i.e. it's = -1 when the stack is empty; */
	private int top;

	/** Parsing simlulator. Used to parse ahead the input stream and find a point where the error recovery is possible; */
	private Simulator sim;
	
	protected Parser(ParsingTables tables)
	{
		this.tables = tables;
		this.actions = getReduceActions();

		accept_action_id = (short) ~tables.rule_infos.length;
		states = new short[256];
	}
	
	protected abstract Action[] getReduceActions(); 
	
	public Object parse(Scanner source)
	    throws IOException, Scanner.Exception, Parser.Exception
	{
		init();

		for (Token token = source.nextToken(); token != null; token = source.nextToken())
		{
			do
			{
				short act = tables.findParserAction(states[top], token.getId());
				if (act > 0)
				{
					shift(act, token.getValue());
					token = null;
				}
				else if (act < 0)
				{
					if (act == accept_action_id || reduce(~act) == accept_action_id)
					{
						return acceptedValue();
					}
				}
				else // act == 0, i.e. this is an error
				{
					syntaxError(token);
					if (recoverFromError(token, source) == accept_action_id)
					{
						return acceptedValue();
					}
					token = null;
				}
			}
			while (token != null);
		}
		throw new Parser.Exception("scanner ran out of tokens");
	}

	/**
	 * A callback that notifies the parser about a syntax error.
	 *
	 * @param token as returned by the scanner that was not recognized by the parser
	 */
	protected void syntaxError(Token token)
	{
		System.err.print("Syntax Error:");
		System.err.print(token.getLine());
		System.err.print(':');
		System.err.print(token.getColumn());
		System.err.print(':');
		Object value = token.getValue();
		if (value instanceof String)
		{
			System.err.print('\'');
			System.err.print(value);
			System.err.print('\'');
		}
		else
		{
			System.err.print('#');
			System.err.print(token.getId());
		}
		System.err.println(" - unexpected token.");
	}

	/**
	 * Performs stacks and, if not initialized yet, reduce actions array initialization.
	 */
	private void init()
	{
		values = new Object[states.length];
		top = 0; // i.e. it's not empty
		states[top] = 1; // initial/first state
	}

	/**
	 * Returns the result of parsing and resets values stack.
	 *
	 * @return value that was returned from an accept action
	 */
	private Object acceptedValue()
	{
		Object v = values[top];
		values = null; // drop this stack to prevent loitering
		return v;
	}

	/**
	 * Increases the stack capacity if it has no room for new entries.
	 */
	private void increaseStackCapacity()
	{
		short[] new_states = new short[states.length * 2];
		System.arraycopy(states, 0, new_states, 0, states.length);
		states = new_states;

		Object[] new_values = new Object[states.length];
		System.arraycopy(values, 0, new_values, 0, values.length);
		values = new_values;
	}

	/**
	 * Perform a shift action.
	 *
	 * @param state to switch to
	 * @param value of the token that will be shifted
	 */
	private void shift(short state, Object value)
	{
		if (++top == states.length)
			increaseStackCapacity();
		states[top] = state;
		values[top] = value;
	}

	/**
	 * Perform a reduce action and the shift that must immediately follow the reduce.
	 *
	 * @param rule_id Number of the production by which to reduce
	 * @return last action executed by reduce
	 */
	private short reduce(int rule_id)
	{
		int rule_info = tables.rule_infos[rule_id];
		int rhs_size = rule_info & 0xFFFF;
		top -= rhs_size;
		Object lhs_value = actions[rule_id].reduce(values, top);
		short lhs_nt_id = (short) (rule_info >>> 16);
		short act = tables.findPostReduceAction(states[top], lhs_nt_id);
		if (act > 0)
			shift(act, lhs_value);
		else if (act == accept_action_id)
			values[top] = lhs_value;
		else
			throw new IllegalStateException("Cannot shift the result of a reduced production: " + act);
		return act;
	}



	private short recoverFromError(Token token, Scanner source)
	    throws IOException, Scanner.Exception, Parser.Exception
	{
		if (tables.error_symbol_id > 0)
		{
			short state = 0;
			while (top >= 0 && (state = tables.findPostReduceAction(states[top], tables.error_symbol_id)) <= 0) top--;
			if (top < 0)
				throw new Parser.Exception("Cannot recover from the syntax error");
			shift(state, null);
		}
		else if (token.getId() == 0) // end of input
		{
			throw new Parser.Exception("Unexpected end of file while attempting error recovery");
		}
		else // discard current token
		{
			token = source.nextToken();
		}

		if (sim == null)
			sim = new Simulator(this);
		else
			sim.resyncStatesStack();

		Token[] fwd = sim.simulateParse(token, source);
		if (fwd == null)
			throw new Parser.Exception("Cannot recover from the syntax error");

		return parse(fwd);
	}

	private short parse(Token[] tokens)
	{
		short act = 0;
		Token token;
		for (int i = 0; i < tokens.length && (token = tokens[i]) != null; i++)
		{
			do
			{
				act = tables.findParserAction(states[top], token.getId());
				if (act > 0)
				{
					shift(act, token.getValue());
					token = null;
				}
				else if (act == accept_action_id || reduce(~act) == accept_action_id)
				{
					return accept_action_id;
				}
			}
			while (token != null);
		}
		return act;
	}
}
