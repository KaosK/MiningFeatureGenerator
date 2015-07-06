package app;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple Table Object for better handling of Query ResultSets.
 *
 */
public class SimpleTable {

	private String[] columnNames;
	private int[] columnWidth;
	private List<List<String>> tableData;

	/**
	 * Constructor method for SimpleTable
	 * 
	 * @param rset Takes a ResultSet as input to create and fill the table.
	 * @throws SQLException SQLException on Query errors.
	 */
	public SimpleTable(ResultSet rset) throws SQLException {
		ResultSetMetaData rsmd = rset.getMetaData();
		int columnCount = rsmd.getColumnCount();
		columnNames = new String[columnCount];
		columnWidth = new int[columnCount];
		tableData = new ArrayList<List<String>>();

		for (int i = 1; i <= columnCount; i++) {
			columnNames[i - 1] = rsmd.getColumnName(i);
			columnWidth[i - 1] = Math.max(columnNames[i - 1].length(),
					columnWidth[i - 1]);
		}

		while (rset.next()) {
			List<String> dataRowTemp = new ArrayList<String>(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				dataRowTemp.add(rset.getString(i));
				columnWidth[i - 1] = Math.max(dataRowTemp.get(i - 1).length(),
						columnWidth[i - 1]);
			}
			tableData.add(dataRowTemp);
		}
	}
	
	public Iterator<List<String>> iterator() {
		return tableData.iterator();
	}
	
	@SuppressWarnings("rawtypes")
	public List getList() {
		return tableData;
	}

	/**
	 * This method outputs the SimpleTable to the console.
	 * 
	 */
	public void printToConsole() {
		// Header:
		for (int i = 0; i < columnNames.length; i++) {
			int width = columnWidth[i] + 4;
			System.out.printf("%-" + width + "." + (width - 2) + "s",
					columnNames[i]);
		}
		System.out.println();
		// dividing line
		for (int i = 0; i < columnNames.length; i++) {
			int width = columnWidth[i] + 2;
			String repeated = new String(new char[width]).replace("\0", "-");
			System.out.print(repeated + "  ");
		}
		System.out.println();
		// Data:
		for (List<String> row : tableData) {
			for (int i = 0; i < row.size(); i++) {
				int width = columnWidth[i] + 4;
				System.out.printf("%-" + width + "." + (width - 2) + "s",
						row.get(i));
			}
			System.out.println();
		}
	}

	/**
	 * This method outputs the SimpleTable to the console.
	 * 
	 */
	public void printToConsole2() {
		// Header:
		for (int i = 0; i < columnNames.length; i++) {
			int width = columnWidth[i] + 4;
			System.out.printf("%-" + width + "." + (width - 2) + "s",
					columnNames[i]);
		}
		System.out.println();
		// dividing line
		for (int i = 0; i < columnNames.length; i++) {
			int width = columnWidth[i] + 2;
			String repeated = new String(new char[width]).replace("\0", "-");
			System.out.print(repeated + "  ");
		}
		System.out.println();
		// Data:
		for (List<String> row : tableData) {
			System.out.println(row.toString());
		}
	}


	public String toWebTable() {
		StringBuilder SBuilder = new StringBuilder();
		for (List<String> row : tableData) {
			SBuilder.append("\t<tr>\n");
				for (String entry : row) {
					SBuilder.append("\t\t<td>");
					SBuilder.append(entry);
					SBuilder.append("</td>\n");
				}
			SBuilder.append("\t</tr>\n");
		}
		return SBuilder.toString();
	}

}

