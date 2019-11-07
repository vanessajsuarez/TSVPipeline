package assignment3;

import java.io.*;
import java.util.Arrays;

/* 
 * A pipeline to read a stream made up of two descriptive lines
 * and subsequent records.
 * This class handles file input, output, and formatting.
 * 
 * First two lines describe the proper formatting in the file and are captured
 * to check for proper formatting in subsequent lines:
 * 		The first line maintains # of fields and their names.
 * 		The second line maintains field types.
 * Field types are assumed to be either String or long types and lines are
 * stored as "records".
 * 
 * After capturing the formatting, this pipeline checks each line against
 * specifications made by the user and writes them into a new file.
 * 
 * Is tested with properly and improperly formatted files, as well as varying
 * specifications or lack of made by the user.
 */

public class TSVPipeline {

	private TSVFilter myTSVFilter;
	private BufferedReader br;
	private int numOfFields;
	private String fieldNamesLine, fieldTypesLine;
	private String[] fieldNames, fieldTypes;

	/**
	 * Constructor creates a new File and initializes the filter
	 * @param inTSVFilter Maintains specifications for the output stream
	 */
	public TSVPipeline(TSVFilter inTSVFilter) {
		myTSVFilter = inTSVFilter;

		try {
			File file = new File(myTSVFilter.getFileName());
			br = new BufferedReader(new FileReader(file));
			System.out.println("File found.");

			describeFile(br);

		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
	}
	
	/**
	 * Checks each record for proper formatting by default.
	 * If specified, filters the stream accordingly.
	 * If specified, terminates the stream accordingly.
	 * @param filename Name of new file to record output stream into.
	 */
	public void processIntoFile(String filename) {
		Processor myprocessor = new Processor(this);
		BufferedWriter bw = null;
		String line;

		//Checks for and validates a filter specification
		int i = Arrays.asList(fieldNames).indexOf(myTSVFilter.getField());
		if(myTSVFilter.getField() != null && i == -1) {
			System.out.println("Not a field option.");
		}
		else {
			//Writes valid and (optionally) specified lines into new file.
			try {
				bw = new BufferedWriter(new FileWriter(filename));
				bw.write(fieldNamesLine+"\n");
				bw.write(fieldTypesLine+"\n");
				while ((line = br.readLine()) != null) {
					if (myprocessor.isGoodLine(line)) {
						bw.write(line+"\n");
					}
				}
				//Checks for terminating specification and displays final computation.
				if(myprocessor.getTerminatingField() != null) {
					myprocessor.displayComputation();
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bw != null) {
					try {
						bw.close();
					} catch (Exception e) {}
				}
			}
		}
	}

	/**
	 * Captures information about file contents as arrays:
	 * 1) Field Names from the first line.
	 * 2) Field Types from the second line.
	 * @param br BufferedReader allows stream reading.
	 */
	private void describeFile(BufferedReader br) {
		try {
			fieldNamesLine = br.readLine();
			fieldNames = fieldNamesLine.split("\\t");
			fieldTypesLine = br.readLine();
			fieldTypes = fieldTypesLine.split("\\t");
			System.out.println("First two lines found.");

			// Checks for proper formatting in file specifications.
			if (fieldNames.length == fieldTypes.length) {
				numOfFields = fieldNames.length;
				System.out.println("First two lines are in proper form.");
			} else {
				throw new InvalidObjectException("Second line has improper form.");
			}
		} catch (IOException e) {
			System.out.println("First two lines are not valid.");
		}

	}

	public TSVFilter getTSVFilter() {
		return myTSVFilter;
	}
	
	public BufferedReader getBufferedReader() {
		return br;
	}

	public int getNumOfFields() {
		return numOfFields;
	}
	
	public String[] getFieldTypes() {
		return fieldTypes;
	}
	
	public String[] getFieldNames() {
		return fieldNames;
	}

}