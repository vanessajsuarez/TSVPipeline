package assignment3;

/*
 * This system records specifications made by the user into a data
 * structure by using the TSVFilter class.
 * 
 * TSVPipeline then serves as an interface to establish formatting
 * rules captured from the file and handles creating an output file
 * that meets the user specifications.
 * 
 * This is done by delegating line-by-line validation, filtering, and
 * computation to the Processor class.
 * 
 * The system assumes only two field types are specified by the file
 * and that the user specifies exactly two parameters of either type
 * String or long. Filtering and terminal operations are optional.
 * 
 * The user must provide the path for input and output files.
 */

public class HW3Runner {

	public static void main(String[] args) {

		TSVFilter myTSVFilter = new TSVFilter
				.WhichFile("/Users/Vanessa/Desktop/myTestFile.tsv")
				.select("Gender", "male")
				.terminate("Age", TerminalComputation.MEANVAL)
				.done();
		
		new TSVPipeline(myTSVFilter).processIntoFile("newfile.tsv");
	}

}