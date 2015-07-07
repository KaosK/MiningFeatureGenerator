package app.csvwriter;

import java.io.IOException;

import org.apache.commons.csv.*;

public class CSVWriter {
	
	Appendable out;
	CSVFormat format;
	CSVPrinter printer;

	public CSVWriter(Appendable out, String[] headers) throws IOException {
		this.out = out; 
		format = CSVFormat.DEFAULT.withHeader(headers);
		printer = new CSVPrinter(out, format);
	}
	
//	public CSVWriter() throws IOException {
//		this(System.out);
//	}

	public void write(Iterable<?> values) {
		try {
			printer.printRecords(values);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("MÖÖP");

		}
	}
}