import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;


public class Preprocessor {

	public static final String REFERENCE_SEPARATOR = ":REF:";
	private static int current_pos;
	
	public static void main(String[] args) {
		StringBuilder text = new StringBuilder();
		int character = 0;
		int i;
		int ref_index = 0;
		ArrayList<Integer> insert_indexes = new ArrayList<Integer>();
		Reader f = null;
		BufferedWriter writer = null;
		
		if(args.length != 2) {
			System.out.println("Error, incorrect input arguments");
			System.exit(1);
		}
		try {
			f = new BufferedReader(new FileReader(args[0]));
		} catch(Exception e) {
			System.out.println("Error, wrong input file path");
			System.exit(1);
		}



		try {
			character = f.read();
			while(character != -1) {
				text.append((char)character);
				character = f.read();
			}
			f.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if(text.length() < 2) 
			return;


		try {
		    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "utf-8"));
		    
		} catch (IOException ex) {
			System.out.println("Error, wrong output file path");
			System.exit(1);
		} 

		locate_generic_method_references(text, insert_indexes);

		for(i = 0; i < text.length(); i++) {
			if(ref_index != insert_indexes.size() && i == insert_indexes.get(ref_index)) {
				try {
					writer.write(REFERENCE_SEPARATOR);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ref_index++;
			}
			try {
				writer.write(text.charAt(i));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
	}

	private static int locate_last_nonspace(StringBuilder text, int start_pos)
	{
		int pos;
		if(start_pos == 0) {
			return start_pos;
		}
		pos = start_pos - 1;
		while(Character.isWhitespace(text.charAt(pos)) && pos != 0) {
			pos--;
		}
		
		return pos;
	}

	static void locate_array_start(StringBuilder text) {
		if(current_pos == 0) {
			return;
		}

		current_pos = current_pos - 1;
		while(text.charAt(current_pos) != '[' && current_pos != 0) {
			current_pos = current_pos - 1;
		}

		return;	
	}

	static void locate_generic_start(StringBuilder text) 
	{
		if(current_pos == 0)
			return;
		while(true) {
			current_pos = current_pos - 1;
			if(current_pos == 0) 
				return;
			else if(text.charAt(current_pos) == '<')
				return;
			else if(text.charAt(current_pos) == '>') 
				locate_generic_start(text);
			
		}
	}

	static void locate_generic_method_references(StringBuilder text, ArrayList<Integer> insert_indexes)
	{	
		for(int i = 0; i < text.length() - 2; i++) {
			if(text.charAt(i) == ':' && text.charAt(i+1) == ':' && text.charAt(i+2) != ':') {
				current_pos = locate_last_nonspace(text, i);
				while(text.charAt(current_pos) == ']') {
					locate_array_start(text);
					if(current_pos == 0)
						return;
					current_pos = locate_last_nonspace(text, current_pos);
				}

				if(text.charAt(current_pos) == '>') {
					locate_generic_start(text);
					if(current_pos == 0)
						return;
					else if(text.charAt(current_pos) == '<') {
						insert_indexes.add(current_pos);
					}
					
				}

			}

		}
	}

}