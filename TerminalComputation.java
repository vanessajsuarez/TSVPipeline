/*
 * Possible terminal computations. 
 * This class carries out computations requested by the user by
 * maintaining running totals and squares as each record in the
 * stream is handled by the processor.
 */
public enum TerminalComputation {

	MEANVAL {
		@Override
		public double terminalCompute(String inString) {

			counter++;
			num = Long.parseLong(inString);
			runningTotal += num;
			computation = runningTotal / counter;

			return computation;
		}
	},
	STDVAL {
		@Override
		public double terminalCompute(String inString) {

			mean = TerminalComputation.MEANVAL.terminalCompute(inString);
			counter++;
			num = Long.parseLong(inString);
			runningTotal += num;
			squares += (num * num);
			computation = (squares - (runningTotal * runningTotal) / counter) / (counter - 1);

			return computation;

		}
	},
	MEANLENGTH {
		@Override
		public double terminalCompute(String inString) {

			counter++;
			num = inString.length();
			runningTotal += num;
			computation = runningTotal / counter;

			return computation;

		}
	},
	STDLENGTH {
		@Override
		public double terminalCompute(String inString) {

			mean = TerminalComputation.MEANLENGTH.terminalCompute(inString);
			counter++;
			num = inString.length();
			runningTotal += num;
			squares += (num * num);
			computation = (squares - (runningTotal * runningTotal) / counter) / (counter - 1);

			return computation;
		}
	};

	public abstract double terminalCompute(String inString);

	double num, computation, mean, squares, runningTotal = 0.0;
	int counter = 0;

}
