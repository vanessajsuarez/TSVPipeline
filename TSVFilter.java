/* 
 * This Filter records specifications made by the user into a data
 * structure to be used by the pipeline using the Builder pattern.
 */

public class TSVFilter {

	// required parameters for outer object
	private final String fileName;
	// optional parameters
	private final String field, value, terminatingField;
	private final long longValue;
	private final TerminalComputation myTerminal;

	/**
	 * @param inWhichFile A reference to an instance of the inner class that handles
	 *                    construction.
	 * 
	 * TSVFilter grants WhichFile a private construction
	 */
	private TSVFilter(WhichFile inWhichFile) {
		fileName = inWhichFile.fileName;
		field = inWhichFile.field;
		value = inWhichFile.value;
		longValue = inWhichFile.longValue;
		terminatingField = inWhichFile.terminatingField;
		myTerminal = inWhichFile.myTerminal;

	}

	public String getFileName() {
		return fileName;
	}

	public String getField() {
		return field;
	}

	public String getValue() {
		return value;
	}

	public long getLongValue() {
		return longValue;
	}

	public String getTerminatingField() {
		return terminatingField;
	}

	public TerminalComputation getTerminal() {
		return myTerminal;
	}

	/**
	 * Inner builder class constructs the object.
	 */
	public static class WhichFile {
		/**
		 * @param inFileName required parameter is built by constructor.
		 */
		public WhichFile(String inFileName) {
			fileName = inFileName;
		}

		/**
		 * @param inField Takes a field from the file's header.
		 * @param inValue Takes a value from the file's field column.
		 * @return constructed inner class.
		 * 
		 * Method sets optional properties to filter file output.
		 */
		public WhichFile select(String inField, String inValue) {
			field = inField;
			value = inValue;
			return this;
		}

		/**
		 * @param inField Takes a field from the file's header.
		 * @param inValue Takes a value from the file's field column as a long.
		 * @return constructed inner class.
		 * 
		 * Method sets optional properties to filter file output.
		 */
		public WhichFile select(String inField, long inValue) {
			field = inField;
			longValue = inValue;
			return this;
		}

		/**
		 * @param inTerminatingField Takes a field from the file's header.
		 * @param inTerminal Takes a terminal command.
		 * @return Constructed inner class.
		 */
		public WhichFile terminate(String inTerminatingField, TerminalComputation inTerminal) {
			terminatingField = inTerminatingField;
			myTerminal = inTerminal;
			return this;
		}

		/**
		 * @return Full object created by inner class.
		 */
		public TSVFilter done() {
			return new TSVFilter(this);
		}

		// required parameters for inner object
		private final String fileName;
		// optional parameters
		private String field, value, terminatingField;
		private long longValue;
		private TerminalComputation myTerminal;
	}

}
