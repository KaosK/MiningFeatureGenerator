package app.utils;

public class Helper {

	public Helper() {
		// TODO Auto-generated constructor stub
	}

	public static double tryConvertString2Double(String string) {
		try {
			return Double.parseDouble(string);
		} catch (NumberFormatException nfe) {
			return 0;
		}
	}

	public static Integer tryConvertString2Integer(String string) {
		try {
			return Integer.parseInt(string);
		} catch (NumberFormatException nfe) {
			return 0;
		}
	}

}
