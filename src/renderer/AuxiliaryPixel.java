package renderer;

/**
 * This is record class (immutable class) that get a pixel and calculate the
 * next pixel to send to threads. it also responsible to print in live the
 * progress of the program.
 * 
 * @param row the row of the pixel
 * @param col the column of the pixel
 * @author Dan Zilberstein
 */
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
	 * function to initializes the values
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
	 * Create the next pixel to be colored
	 * 
	 * @return the next pixel (null if there isn't)
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

	/**
	 * Prints the percent if the pixel finished and continue to work if the job is
	 * not done yet
	 */
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
}
