package beaver.impl;

/**
 * An API that a code portion of a production, with that production action, must implement.
 */
public abstract class Action
{
	static public final Action NONE = new Action()
	{
		protected Object reduce(Object[] args, int offset)
		{
			return null;
		}
	};
	
	/**
	 * Am action code that is executed when the production is reduced.
	 *
	 * @param args   an array part of which is filled with this action arguments
	 * @param offset to the last element BEFORE the first argument of this action
	 * @return a value of a LHS nonterminal
	 */
	protected abstract Object reduce(Object[] args, int offset);
}
