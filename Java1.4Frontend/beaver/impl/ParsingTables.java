package beaver.impl;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

/**
 * Parsing Tables
 */
public final class ParsingTables
{
	/** A table with all actions */
	final short[] actions;

	/**
	 * A table containing the lookahead for each entry in "actions" table.
	 * Used to detect "collisions".
	 */
	final short[] lookaheads;

	/**
	 * For each state, the offset into "actions" table that is used to find action for a terminal
	 * that has been fetched from the scanner.
	 */
	final short[] terminal_offsets;

	/**
	 * For each state, the offset into "actions" table that is used to find action for a nonterminal
	 * that has been created by a reduced production.
	 */
	final short[] nonterminal_offsets;

	/** Default action for each state */
	final short[] default_actions;

	/**
	 * A table with encoded production information.
	 * <p/>
	 * Each slot in this table is a "structure":
	 * <pre>
	 *   short lhs_symbol_id ; // Symbol on the left-hand side of the production
	 *   short rhs_length    ; // Number of right-hand side symbols in the production
	 * </pre>
	 * where lhs_symbol_id uses high 16 bit of this structure, and rhs_length - lower 16 bits
	 */
	final int[] rule_infos;

	/** ID of the "error" nonterminal if used in the grammar, -1 otherwise; */
	final short error_symbol_id;

	/**
	 * Ensures that parser tables are loaded.
	 *
	 * @param impl_class class of the instance of the Parser
	 */
	public ParsingTables(Class impl_class)
	{
		String name = impl_class.getName();
		name = name.substring(name.lastIndexOf('.') + 1) + ".spec";
		InputStream spec_in = impl_class.getResourceAsStream(name);
		if (spec_in == null)
			throw new IllegalStateException("parser specification not found");
		try
		{
			DataInputStream data = new DataInputStream(new InflaterInputStream(spec_in));
			try
			{
				short len = data.readShort();
				actions = new short[len];
				for (int i = 0; i < len; i++)
					actions[i] = data.readShort();
				lookaheads = new short[len];
				for (int i = 0; i < len; i++)
					lookaheads[i] = data.readShort();

				len = data.readShort();
				terminal_offsets = new short[len];
				for (int i = 0; i < len; i++)
					terminal_offsets[i] = data.readShort();
				nonterminal_offsets = new short[len];
				for (int i = 0; i < len; i++)
					nonterminal_offsets[i] = data.readShort();
				default_actions = new short[len];
				for (int i = 0; i < len; i++)
					default_actions[i] = data.readShort();

				len = data.readShort();
				rule_infos = new int[len];
				for (int i = 0; i < len; i++)
					rule_infos[i] = data.readInt();

				error_symbol_id = data.readShort();
			}
			finally
			{
				data.close();
			}
		}
		catch (IOException e)
		{
			throw new IllegalStateException("cannot load parser tables: " + e.getMessage());
		}
	}

	/**
	 * Find the appropriate action for a parser in a given state with a specified terminal look-ahead.
	 *
	 * @param state     of a parser
	 * @param lookahead
	 * @return parser action
	 */
	final short findParserAction(int state, short lookahead)
	{
		int index = terminal_offsets[state];
		if (index != Short.MAX_VALUE)
		{
			index += lookahead;
			if (0 <= index && index < actions.length && lookaheads[index] == lookahead)
				return actions[index];
		}
		return default_actions[state];
	}

	/**
	 * Find the appropriate action for a parser in a given state with a specified nonterminal look-ahead.
	 * In this case the only possible outcomes are either a state to shift to or an accept action.
	 *
	 * @param state     of a parser
	 * @param lookahead
	 * @return parser action
	 */
	final short findPostReduceAction(int state, short lookahead)
	{
		int index = nonterminal_offsets[state];
		if (index != Short.MAX_VALUE)
		{
			index += lookahead;
			if (0 <= index && index < actions.length && lookaheads[index] == lookahead)
				return actions[index];
		}
		return default_actions[state];
	}
}

