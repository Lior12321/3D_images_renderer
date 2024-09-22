package renderer;

record Pixel(int row, int col) {

	/** The row amount in a image */
	private static int maxRows = 0;
	/** The column amount in a image */
	private static int maxCols = 0;
	/** The total amount of pixels in the image */
	private static long totalPixels = 0l;
	/** Counter for the row */
	private static volatile int cRow = 0;
	/** Counter for the column */
	private static volatile int cCol = -1;
	/** How many pixels painted already */
	private static volatile long pixels = 0l;
	/** The last printed percentage (in tenth of percent) */
	private static volatile int lastPrinted = 0;
	/** Check if there is a progress print */
	private static boolean print = false;
	/** The interval for printing the progress (in tenth of percent) */
	private static long printInterval = 100l;
	/** The printing format */
	private static final String PRINT_FORMAT = "%5.1f%%\r";
	/** Mutex to synchronize access to the next pixel generation */
	private static Object mutexNext = new Object();
	/** Mutex to synchronize access to pixel processing counters */
	private static Object mutexPixels = new Object();

	/**
     * function that initializes the values
     *
     * @param maxRows  the number of rows in the picture
     * @param maxCols  the number of columns in the picture
     * @param interval the interval for printing
     */
	static void initialize(int maxRows, int maxCols, double interval) {
		Pixel.maxRows = maxRows;
		Pixel.maxCols = maxCols;
		Pixel.totalPixels = (long) maxRows * maxCols;
		printInterval = (int) (interval * 10);
		if (print = printInterval != 0)
			System.out.printf(PRINT_FORMAT, 0d);
	}

	/**
	 * 
	 * @return
	 */
	static Pixel nextPixel() {
		synchronized (mutexNext) {
			if (cRow == maxRows)
				return null;
			++cCol;
			if (cCol < maxCols)
				return new Pixel(cRow, cCol);
			cCol = 0;
			++cRow;
			if (cRow < maxRows)
				return new Pixel(cRow, cCol);
		}
		return null;
	}

	static void pixelDone() {
		boolean flag = false;
		int percentage = 0;
		synchronized (mutexPixels) {
			++pixels;
			if (print) {
				percentage = (int) (1000l * pixels / totalPixels);
				if (percentage - lastPrinted >= printInterval) {
					lastPrinted = percentage;
					flag = true;
				}
			}
		}
		if (flag)
			System.out.printf(PRINT_FORMAT, percentage / 10d);
	}

	/**
	 * Internal function for thread-safe manipulating of main follow up Pixel object
	 * - this function is critical section for all the threads, and main Pixel
	 * object data is the shared data of this critical section.<br/>
	 * The function provides next pixel number each call.
	 * 
	 * @param target target secondary Pixel object to copy the row/column of the
	 *               next pixel
	 * @return the progress percentage for follow up: if it is 0 - nothing to print,
	 *         if it is -1 - the task is finished, any other value - the progress
	 *         percentage (only when it changes)
	 */
//	private synchronized int nextP(Pixel target) {
//		++col;
//		++counter;
//		if (col < maxCols) {
//			target.row = this.row;
//			target.col = this.col;
//			if (print && counter == nextCounter) {
//				++percents;
//				nextCounter = pixels * (percents + 1) / 100;
//				return percents;
//			}
//			return 0;
//		}
//		++row;
//		if (row < maxRows) {
//			col = 0;
//			if (print && counter == nextCounter) {
//				++percents;
//				nextCounter = pixels * (percents + 1) / 100;
//				return percents;
//			}
//			return 0;
//		}
//		return -1;
//	}
}
