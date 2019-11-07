public class Runner {

	public static void main(String[] args) {

		TSVFilter myTSVFilter = new TSVFilter
				.WhichFile("/Users/Vanessa/Desktop/myTestFile.tsv")
				.select("Gender", "male")
				.terminate("Age", TerminalComputation.MEANVAL)
				.done();
		
		new TSVPipeline(myTSVFilter).processIntoFile("newfile.tsv");
	}

}
