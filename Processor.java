import java.util.Arrays;

/* 
 * Processes requests and specifications made from the user and the file.
 * Carries out line-by-line tasks for the Pipeline by validating inputs,
 * field types, field names, record lengths, and delegating computation.
 * 
 * Every specification is pulled from user input. Assumes only two field
 * types are present but does not assume what they are.
 * 
 * Encapsulates each request (proper formatting, filters, computation) into
 * separate private methods for internal processing. Pipeline only communicates
 * with the delegating method "isGoodLine".
 */
public class Processor {

	private TSVPipeline myTSVPipeline;
	private TSVFilter myTSVFilter;
	private TerminalComputation myTerminal;
	
	private String[] fieldNames, fieldTypes, record, possibleFieldTypes;
	private String myField, myValue, myLine, myLongValue, myTerminatingField;
	private int numOfFields;
	private double computation;
	private boolean validRequest = true;

	/**
	 * Stores specifications made by the user (Filter) and specifications required
	 * by the file (Pipeline) to allow for processing.
	 * @param inTSVPipeline
	 */
	Processor(TSVPipeline inTSVPipeline) {

		myTSVPipeline = inTSVPipeline;
		numOfFields = myTSVPipeline.getNumOfFields();
		fieldNames = myTSVPipeline.getFieldNames();
		fieldTypes = myTSVPipeline.getFieldTypes();
		//Crediting: https://stackoverflow.com/questions/13796928/how-to-get-unique-values-from-array
		possibleFieldTypes = Arrays.stream(fieldTypes).distinct().toArray(String[]::new);

		myTSVFilter = myTSVPipeline.getTSVFilter();
		myField = myTSVFilter.getField();
		myValue = myTSVFilter.getValue();
		myLongValue = Long.toString(myTSVFilter.getLongValue());

		myTerminal = myTSVFilter.getTerminal();
		myTerminatingField = myTSVFilter.getTerminatingField();

	}

	/**
	 * @param inLine Takes line to check for proper formatting
	 * @return
	 */
	public boolean isGoodLine(String inLine) {

		myLine = inLine;

		record = myLine.split("\\t");
		if (hasProperForm() && isSelected()) {
			compute();
			return true;
		}

		return false;
	}

	/**
	 * @return Returns true if line meets filter specifications made by the user.
	 */
	private boolean isSelected() {

		boolean isSelected = false;

		// Checks for existence of filter specification made by the user
		if (myField != null) {
			// Checks each record's entry for the specified field.
			int i = Arrays.asList(fieldNames).indexOf(myField);
			if (record[i].equals(myValue)) {
				isSelected = true;
			} else if (record[i].equals(myLongValue)) {
				isSelected = true;
			}
		} else {
			return true;
		}

		return isSelected;
	}

	/**
	 * @return Returns true if line has proper form as specified by the Pipeline.
	 */
	private boolean hasProperForm() {

		boolean hasProperForm = false;

		// Checks field quantity meets specification
		if (record.length == numOfFields) {
			try {
				// Checks field type meets specification
				for (int i = 0; i < record.length; i++) {
					if (fieldTypes[i].equals(possibleFieldTypes[0])) {
					} else if (fieldTypes[i].equals(possibleFieldTypes[1])) {
						Long.parseLong(record[i]);
					} else {
						throw new NumberFormatException();
					}
				}
				hasProperForm = true;
			} catch (NumberFormatException e) {
				hasProperForm = false;
			}
		}
		return hasProperForm;
	}

	/**
	 * Carries out terminal computation
	 */
	private void compute() {

		// Checks whether termination was requested by the user
		if (myTerminatingField != null) {
			int i = Arrays.asList(fieldNames).indexOf(myTerminatingField);

			boolean isString = fieldTypes[i].equals(possibleFieldTypes[0]);
			boolean isLong = fieldTypes[i].equals(possibleFieldTypes[1]);
			
			// Checks terminal command and format
			if ( (myTerminal == TerminalComputation.MEANVAL && isLong) ||
				 (myTerminal == TerminalComputation.STDVAL && isLong) ||
				 (myTerminal == TerminalComputation.MEANLENGTH && isString) ||
				 (myTerminal == TerminalComputation.STDLENGTH && isString) ) {
				
				// If valid, carries out computation
				computation = myTerminal.terminalCompute(record[i]);
				
			} else {
				validRequest = false;
			}
		}
	}

	/**
	 * Displays result of computation.
	 */
	public void displayComputation() {
		if(validRequest) {
			System.out.println("Terminal Computation:" + computation);
		}
		else {
			System.out.println("Invalid terminal request.");
		}
	}

	public String getTerminatingField() {
		return myTerminatingField;
	}

}
