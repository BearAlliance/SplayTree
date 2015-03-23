
//========================================================================//
// Nick Cacace															  //
// Splay Tree Interface 												  //
// Advanced Data Structures 											  //
// Prof. Li 															  //
// 3 March 2015 														  //
//========================================================================//

import java.util.*;
import java.util.regex.*;
import java.io.*;

public class SplayTreeInterface
{
	public static void main(String[] args)
	{
		Scanner userInput = new Scanner(System.in); // Input Scanner
		Command currentCommand;
		// Initialize the tree
		SplayTree tree = new SplayTree();
		
		System.out.println("\n" + "Splay Tree Interface by N. Cacace" + "\n");
		// Populates the tree with integers from "in.dat"
		System.out.println("Reading from file \'in.dat\"...");
		System.out.println(populate(tree) + " nodes added to the tree \n");
		tree.printTree();
		System.out.println("\nEntering interactive mode...");
		System.out.println("Each command must be followed by a space and the key you wish to interact with");
		System.out.println(" S - Splay\n I - Insert\n D - Delete\n F - Search\n H - Help\n Q - Quit\n");
		System.out.println("Enter command:");
		
		while (true) // Loops forever until the user enters 'q' to stop
		{
			// Promps user for the next line
			currentCommand = parseInput(new Scanner(userInput.nextLine())); // Parse out command type and value to currentCommand
			if (currentCommand != null) { // If the command was parsed correctly
				if (currentCommand.type == 'q' || currentCommand.type == 'Q')
					break;
				System.out.println();
				doCommand(currentCommand, tree);
			}
			System.out.println("\nEnter command:");
		}
	}

//========================================================================//
//							User Input Parsing							  //
//							   & Excecution								  //
//========================================================================//

	// Accepts a line of user input and returns a Command object containing the parsed data
	private static Command parseInput(Scanner input)
	{
		Command output = new Command('0', 0); // Initialize return object

		if (input.hasNext(COMMAND))
			output.type = input.findInLine(COMMAND).charAt(0);
		else {
			System.out.println("The entered command was not valid");
			return null;
		}
		if (output.type == 'p' || output.type == 'P'
			|| output.type == 'q' || output.type == 'Q'
			|| output.type == 'h' || output.type == 'H') // Print / quitdoes not require a value
			return output;
		if (input.hasNextInt())
			output.value = input.nextInt();
		else {
			System.out.println("The entered command was not valid");
			return null;
		}
		return output;
	}

	// Excecutes the command given from the user
	// Returns true if the loop should continue
	// Returns false if the user has entered 'q' to stop
	private static boolean doCommand(Command input, SplayTree tree)
	{
		//System.out.println("Command is " + input.type + " " + input.value);
		switch (input.type) {
			case 's':
			case 'S': // Splay
				tree.splay(input.value);
				System.out.println("Splay done");
				break;
			case 'i':
			case 'I': // Insert
				if (tree.insert(input.value))
					System.out.println("Insertion is successful");
				else
					System.out.println("Duplicated keys");
				break;
			case 'd':
			case 'D': // Delete
				if (tree.remove(input.value))
					System.out.println("Deletion is successful");
				else
					System.out.println("Key is not in the tree");
				break;
			case 'f':
			case 'F': // Search
				if (tree.search(input.value))
					System.out.println("Search is successful");
				else
					System.out.println("Search is unsuccessful");
				break;
			case 'h':
			case 'H':
				System.out.println
				(" S - Splay\n I - Insert\n D - Delete\n F - Search\n H - Help\n Q - Quit");
				return true;
			case 'p':
			case 'P':
				tree.printTree();
				break;
		}
		System.out.println("");
		tree.printTree(); // Print the tree
		return true;
	}



//========================================================================//
//						Tree Initialization								  //
// Populates the tree with integers from "in.dat"						  //
// Returns the number of nodes added to the tree 						  //
// Can be used to initialize, or add values to existing tree 			  //
//========================================================================//
	private static int populate(SplayTree tree)
	{
		int nodesAdded = 0;
		Scanner currentLine;
		try {
            BufferedReader inputStream = new BufferedReader(new FileReader("in.dat"));
            String line = inputStream.readLine(); 
            // End of file when next line is null, 
            // File is empty when first line is null
            while (line != null) { // Until end of file
            	currentLine = new Scanner(line);
            	if (currentLine.hasNextInt()) { // If the line contains an int
            		tree.insert(currentLine.nextInt()); // Insert it into the tree
            		nodesAdded++;
            	}
            	else { // Error condition, found something other than an integer
            		System.out.println("The file \"in.dat\" contains something other than an integer");
            		System.out.println("Ignoring line");
            	}
            	line = inputStream.readLine(); // Loads next line
            }
        inputStream.close();
        }

        // Handnle possible errors
        catch (FileNotFoundException e)
        {
            System.out.println("File does not exist");
        }
        catch (IOException e)
        {
            System.out.println("Error Reading From in.dat");
        }
        return nodesAdded;
	}
	// Pattern assignment
	public static final Pattern COMMAND = 
 	Pattern.compile("[SsIiDdFfQqPpHh]");
}
//========================================================================//
//						Command Container Class     					  //
// Simple two paramater object to contain the 							  //
// parsed user input for a single command 								  //
//========================================================================//
class Command
{
	public char type;
	public int value;

	public Command(char givenType, int givenValue)
	{
		type = givenType;
		value = givenValue;
	}
}