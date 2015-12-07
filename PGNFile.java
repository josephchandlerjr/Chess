import java.util.Scanner;
import java.io.FileReader;
import java.util.ArrayList;

public class PGNFile	
{
	Scanner in;
	ArrayList<String> games = new ArrayList<String>();

	public PGNFile(String fileName) throws java.io.FileNotFoundException
	{
		in = new Scanner(new FileReader(fileName));
	}

	public static void main(String[] args) throws java.io.FileNotFoundException
	{
		//poor mans parser
		boolean inTagPair = false;
		boolean inComment = false;
		PGNFile file = new PGNFile(args[0]);
		while (file.in.hasNext())
		{
			String token = file.in.next();
			if (token.startsWith("[")) 
			{
				inTagPair = true;
			}
			if (token.startsWith("{")) 
			{
				inComment = true;
			}
			if (!inTagPair && !inComment)
			{
				System.out.print(token);
				if ("1-00-11/2-1/2".contains(token)){ System.out.print(" END");}
				System.out.println();
			}
			if (token.endsWith("]"))
			{
				inTagPair = false;
			}
			if (token.endsWith("}"))
			{
				inComment = false;
			}
		}

	}
}
