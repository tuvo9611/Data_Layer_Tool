package com.example.demo.common.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ResourceUtils;

import com.example.demo.dto.request.RecordExcelServiceFrontEndRequest;
import com.example.demo.dto.request.TableExcelServiceFrontEndRequest;

public class ExcelServiceFrontEndUtil {
	private static final int HEADER_TEMPLATE = 3;
	private static final String PAGE_NAME = "G8";
	private static final String CONTROLLER_SHEET = "Controller";

	private ExcelServiceFrontEndUtil() {
		// empty constructor
	}

	public static List<TableExcelServiceFrontEndRequest> readExcelTemplate(String fileTemplate) {
		List<TableExcelServiceFrontEndRequest> listTemples = new ArrayList<>();
		try {
			FileInputStream file = new FileInputStream(ResourceUtils.getFile(fileTemplate));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				String sheetName = workbook.getSheetName(i);
				// To do all sheet. demo 3 sheet
				if (sheetName.equals(CONTROLLER_SHEET)) {
					listTemples.add(readSheetExcel(workbook, sheetName));
				}
			}
			file.close();
		} catch (Exception ex) {
			return null;
		}
		return listTemples;
	}

	private static TableExcelServiceFrontEndRequest readSheetExcel(XSSFWorkbook workbook, String sheetName) {
		try {

			TableExcelServiceFrontEndRequest response = new TableExcelServiceFrontEndRequest();
			List<RecordExcelServiceFrontEndRequest> listRecord = new ArrayList<>();
			int index = 0;
			// Get first/desired sheet from the workbook
			int controllerIndex = workbook.getSheetIndex(sheetName);
			XSSFSheet sheet = workbook.getSheetAt(controllerIndex);

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				index++;
				RecordExcelServiceFrontEndRequest template = new RecordExcelServiceFrontEndRequest();
				Row row = rowIterator.next();

				// find page name and set value
				CellReference cellReference = new CellReference(PAGE_NAME);
				Row rows = sheet.getRow(cellReference.getRow());
				Cell cells = rows.getCell(cellReference.getCol());
				response.setPageName(cells.getStringCellValue());

				// loop to find records
				if (index > HEADER_TEMPLATE) {
					// For each row, iterate through all the columns
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						mapExcelToDto(cell, template);
					}

					// Check isEmpty for add to Template
					if (ObjectUtils.isNotEmpty(template.getMethodname())) {
						listRecord.add(template);
					}
				}
			}
			response.setRecords(listRecord);
			return response;
		} catch (Exception e) {
			return null;
		}
	}

	private static RecordExcelServiceFrontEndRequest mapExcelToDto(final Cell cell,
			RecordExcelServiceFrontEndRequest object) {
		final int type = cell.getCellType();
		final int index = cell.getColumnIndex();
		// Check the cell type and format accordingly
		if (type == Cell.CELL_TYPE_STRING) {
			final String value = cell.getStringCellValue();
			object.setItem(index, value);
		} else if (type == Cell.CELL_TYPE_NUMERIC) {
			final double value = cell.getNumericCellValue();
			final String valueNumber = String.valueOf((int) value);
			object.setItem(index, valueNumber);
		}
		return object;
	}
}
