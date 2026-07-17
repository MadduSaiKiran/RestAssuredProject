package dataDrivenUsingExcel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtilDataDriven {

	@SuppressWarnings("resource")
	public ArrayList<String> getData(String testCaseName, String sheetName) throws IOException {

		ArrayList<String> arrayList = new ArrayList<String>();
		FileInputStream fis = new FileInputStream("./src/dataDrivenUsingExcel/data.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		int noOfsheets = workbook.getNumberOfSheets();
		// Identify 'TestCases' column by scanning the entire firstRow
		for (int i = 0; i < noOfsheets; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
				XSSFSheet sheet = workbook.getSheetAt(i); // sheet is collection of rows
				Iterator<Row> rows = sheet.iterator(); // row is collection of cells

				Row firstRow = rows.next();
				Iterator<Cell> cell = firstRow.cellIterator();
				int k = 0;
				int column = 0;
				while (cell.hasNext()) {
					Cell value = cell.next();
					if (value.getStringCellValue().equalsIgnoreCase("TestCases")) {
						column = k;
					}
					k++;
				}
				System.out.println(column);

				// once desired TestCases column is identified scan entire TestCases column to
				// identify 'Purchase' test case row
				while (rows.hasNext()) {
					Row r = rows.next();
					if (r.getCell(column).getStringCellValue().equalsIgnoreCase(testCaseName)) {
						// after you grab purchase test case row then pull all data in that row and feed
						// into test.
						Iterator<Cell> c = r.cellIterator();
						while (c.hasNext()) {
							Cell c1 = c.next();
							if (c1.getCellType() == CellType.STRING) {
								arrayList.add(c1.getStringCellValue());
							} else {
								String str = NumberToTextConverter.toText(c1.getNumericCellValue());
								arrayList.add(str);
							}

						}
					}
				}
			}

		}
		return arrayList;
	}

}
