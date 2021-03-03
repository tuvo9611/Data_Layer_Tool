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

import com.example.demo.dto.request.TemplateControllerRequest;
import com.example.demo.dto.request.TemplateDtoControllerRequest;
import com.example.demo.dto.request.TemplateItemValidationRequest;
import com.example.demo.dto.request.TemplateMethodControllerRequest;
import com.example.demo.dto.request.TemplateParameterControllerRequest;
import com.example.demo.dto.request.TemplateRecordEntityRequest;
import com.example.demo.dto.request.TemplateTableEntitylRequest;

public class ExcelUtil {
	private static final int HEADER_TEMPLATE_ENTITY = 3;
	private static final int HEADER_TEMPLATE_CONTROLER = 3;
	private static final int HEADER_TEMPLATE_VALIDATION = 6;
	private static final String CONTROLLER_NAME_INDEX = "G8";
	private static final String EXCEL_SHEET_CONTROLLER = "Controller";
	private static final String EXCEL_SHEET_DTO = "DTO";
	private static final String EXCEL_SHEET_ITEM_VALIDATION = "Item Validation";
	private static final String EXCEL_SHEET_PARAMETER = "Parameter";

	private ExcelUtil() {
		// empty constructor
	}

	public static List<TemplateTableEntitylRequest> readExcelEntityTemplate(String fileTemplate) {
		List<TemplateTableEntitylRequest> listTemples = new ArrayList<>();
		try {
			FileInputStream file = new FileInputStream(ResourceUtils.getFile(fileTemplate));
			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				String sheetName = workbook.getSheetName(i);
				listTemples.add(readSheetEntityExcel(workbook, sheetName));
			}
			file.close();
		} catch (Exception ex) {
			System.out.printf(ex.getMessage());
		}
		return listTemples;
	}

	public static TemplateControllerRequest readExcelControllerTemplate(String fileTemplate) {
		TemplateControllerRequest templateController = new TemplateControllerRequest();
		try {
			FileInputStream file = new FileInputStream(ResourceUtils.getFile(fileTemplate));
			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				String sheetName = workbook.getSheetName(i);

				switch (sheetName) {
				case EXCEL_SHEET_CONTROLLER:
					readSheetControllerExcel(workbook, sheetName, templateController);
					break;
				case EXCEL_SHEET_DTO:
					templateController.setDtos(readSheetDtoControllerExcel(workbook, sheetName));
					break;
				case EXCEL_SHEET_ITEM_VALIDATION:
					templateController.setValidations(readSheetValidationControllerExcel(workbook, sheetName));
					break;
				case EXCEL_SHEET_PARAMETER:
					templateController.setParameters(readSheetParameterControllerExcel(workbook, sheetName));
					break;
				default:
					break;
				}
			}

			file.close();
		} catch (Exception ex) {
			System.out.printf(ex.getMessage());
		}
		return templateController;
	}

	private static List<TemplateParameterControllerRequest> readSheetParameterControllerExcel(XSSFWorkbook workbook,
			String sheetName) {
		try {
			List<TemplateParameterControllerRequest> response = new ArrayList<>();
			int index = 0;
			// Get first/desired sheet from the workbook
			int controllerIndex = workbook.getSheetIndex(sheetName);
			XSSFSheet sheet = workbook.getSheetAt(controllerIndex);
			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				index++;
				Row row = rowIterator.next();
				if (index > HEADER_TEMPLATE_CONTROLER) {
					TemplateParameterControllerRequest template = new TemplateParameterControllerRequest();
					// For each row, iterate through all the columns
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						mapExcelParameterToDto(cell, template);
					}
					// Check isEmpty for add to Template
					if (ObjectUtils.isNotEmpty(template.getName())) {
						response.add(template);
					}
				}
			}
			return response;
		} catch (Exception e) {
			return null;
		}
	}

	private static List<TemplateItemValidationRequest> readSheetValidationControllerExcel(XSSFWorkbook workbook,
			String sheetName) {
		try {
			List<TemplateItemValidationRequest> response = new ArrayList<>();
			int index = 0;
			// Get first/desired sheet from the workbook
			int controllerIndex = workbook.getSheetIndex(sheetName);
			XSSFSheet sheet = workbook.getSheetAt(controllerIndex);
			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				index++;
				Row row = rowIterator.next();
				if (index > HEADER_TEMPLATE_VALIDATION) {
					TemplateItemValidationRequest template = new TemplateItemValidationRequest();
					// For each row, iterate through all the columns
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						mapExcelControllerValidationToDto(cell, template);
					}
					// Check isEmpty for add to Template
					if (ObjectUtils.isNotEmpty(template.getItemId())) {
						response.add(template);
					}
				}
			}
			return response;
		} catch (Exception e) {
			return null;
		}
	}

	private static List<TemplateDtoControllerRequest> readSheetDtoControllerExcel(XSSFWorkbook workbook,
			String sheetName) {
		try {
			List<TemplateDtoControllerRequest> response = new ArrayList<>();
			int index = 0;
			// Get first/desired sheet from the workbook
			int controllerIndex = workbook.getSheetIndex(sheetName);
			XSSFSheet sheet = workbook.getSheetAt(controllerIndex);
			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				index++;
				Row row = rowIterator.next();
				if (index > HEADER_TEMPLATE_CONTROLER) {
					TemplateDtoControllerRequest template = new TemplateDtoControllerRequest();
					// For each row, iterate through all the columns
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						mapExcelControllerDtoToDto(cell, template);
					}
					// Check isEmpty for add to Template
					if (ObjectUtils.isNotEmpty(template.getClassName())) {
						response.add(template);
					}
				}
			}
			return response;
		} catch (Exception e) {
			return null;
		}
	}

	private static void readSheetControllerExcel(XSSFWorkbook workbook, String sheetName,
			TemplateControllerRequest response) {
		try {
			List<TemplateMethodControllerRequest> listMethod = new ArrayList<>();
			int index = 0;
			// Get first/desired sheet from the workbook
			int controllerIndex = workbook.getSheetIndex(sheetName);
			XSSFSheet sheet = workbook.getSheetAt(controllerIndex);

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				index++;
				Row row = rowIterator.next();
				if (index == HEADER_TEMPLATE_CONTROLER) {
					// find page name and set value
					CellReference cellReference = new CellReference(CONTROLLER_NAME_INDEX);
					Row rows = sheet.getRow(cellReference.getRow());
					Cell cells = rows.getCell(cellReference.getCol());
					String value = StringUtil.trimValue(cells.getStringCellValue());
					response.setControllerName(value);
				}
				if (index > HEADER_TEMPLATE_CONTROLER) {
					TemplateMethodControllerRequest template = new TemplateMethodControllerRequest();
					// For each row, iterate through all the columns
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						mapExcelControllerToDto(cell, template);
					}
					// Check isEmpty for add to Template
					if (ObjectUtils.isNotEmpty(template.getMethodName())) {
						listMethod.add(template);
					}
				}
			}
			response.setMethodControllers(listMethod);

		} catch (Exception e) {
		}
	}

	private static TemplateTableEntitylRequest readSheetEntityExcel(XSSFWorkbook workbook, String sheetName) {
		try {
			TemplateTableEntitylRequest response = new TemplateTableEntitylRequest();
			List<TemplateRecordEntityRequest> listRecord = new ArrayList<>();
			int index = 0;
			// Get first/desired sheet from the workbook
			int controllerIndex = workbook.getSheetIndex(sheetName);
			XSSFSheet sheet = workbook.getSheetAt(controllerIndex);

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				index++;
				Row row = rowIterator.next();
				if (index > HEADER_TEMPLATE_ENTITY) {
					TemplateRecordEntityRequest template = new TemplateRecordEntityRequest();
					// For each row, iterate through all the columns
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						mapExcelEntityToDto(cell, template);
					}
					// Check isEmpty for add to Template
					if (ObjectUtils.isNotEmpty(template.getColumnName())) {
						listRecord.add(template);
					}
				}
			}
			response.setTableName(sheetName);
			response.setRecords(listRecord);
			return response;
		} catch (Exception e) {
			return null;
		}
	}

	private static TemplateItemValidationRequest mapExcelControllerValidationToDto(final Cell cell,
			TemplateItemValidationRequest object) {
		final int type = cell.getCellType();
		final int index = cell.getColumnIndex();
		// Check the cell type and format accordingly
		switch (type) {
		case Cell.CELL_TYPE_STRING:
			final String value = StringUtil.trimValue(cell.getStringCellValue());
			object.setItem(index, value);
			break;
		case Cell.CELL_TYPE_NUMERIC:
			final double valueNum = cell.getNumericCellValue();
			final String valueNumber = String.valueOf((int) valueNum);
			object.setItem(index, valueNumber);
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			final boolean valueBool = cell.getBooleanCellValue();
			final String valueBolean = String.valueOf((boolean) valueBool);
			object.setItem(index, valueBolean);
			break;
		default:
			break;
		}
		return object;
	}

	private static TemplateDtoControllerRequest mapExcelControllerDtoToDto(final Cell cell,
			TemplateDtoControllerRequest object) {
		final int type = cell.getCellType();
		final int index = cell.getColumnIndex();
		// Check the cell type and format accordingly
		switch (type) {
		case Cell.CELL_TYPE_STRING:
			final String value = StringUtil.trimValue(cell.getStringCellValue());
			object.setItem(index, value);
			break;
		case Cell.CELL_TYPE_NUMERIC:
			final double valueNum = cell.getNumericCellValue();
			final String valueNumber = String.valueOf((int) valueNum);
			object.setItem(index, valueNumber);
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			final boolean valueBool = cell.getBooleanCellValue();
			final String valueBolean = String.valueOf((boolean) valueBool);
			object.setItem(index, valueBolean);
			break;
		default:
			break;
		}
		return object;
	}

	private static TemplateMethodControllerRequest mapExcelControllerToDto(final Cell cell,
			TemplateMethodControllerRequest object) {
		final int type = cell.getCellType();
		final int index = cell.getColumnIndex();
		// Check the cell type and format accordingly
		switch (type) {
		case Cell.CELL_TYPE_STRING:
			final String value = StringUtil.trimValue(cell.getStringCellValue());
			object.setItem(index, value);
			break;
		case Cell.CELL_TYPE_NUMERIC:
			final double valueNum = cell.getNumericCellValue();
			final String valueNumber = String.valueOf((int) valueNum);
			object.setItem(index, valueNumber);
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			final boolean valueBool = cell.getBooleanCellValue();
			final String valueBolean = String.valueOf((boolean) valueBool);
			object.setItem(index, valueBolean);
			break;
		default:
			break;
		}
		return object;
	}

	private static TemplateRecordEntityRequest mapExcelEntityToDto(final Cell cell,
			TemplateRecordEntityRequest object) {
		final int type = cell.getCellType();
		final int index = cell.getColumnIndex();
		// Check the cell type and format accordingly
		switch (type) {
		case Cell.CELL_TYPE_STRING:
			final String value = StringUtil.trimValue(cell.getStringCellValue());
			object.setItem(index, value);
			break;
		case Cell.CELL_TYPE_NUMERIC:
			final double valueNum = cell.getNumericCellValue();
			final String valueNumber = String.valueOf((int) valueNum);
			object.setItem(index, valueNumber);
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			final boolean valueBool = cell.getBooleanCellValue();
			final String valueBolean = String.valueOf((boolean) valueBool);
			object.setItem(index, valueBolean);
			break;
		default:
			break;
		}
		return object;
	}

	private static TemplateParameterControllerRequest mapExcelParameterToDto(final Cell cell,
			TemplateParameterControllerRequest object) {
		final int type = cell.getCellType();
		final int index = cell.getColumnIndex();
		// Check the cell type and format accordingly
		switch (type) {
		case Cell.CELL_TYPE_STRING:
			final String value = StringUtil.trimValue(cell.getStringCellValue());
			object.setItem(index, value);
			break;
		case Cell.CELL_TYPE_NUMERIC:
			final double valueNum = cell.getNumericCellValue();
			final String valueNumber = String.valueOf((int) valueNum);
			object.setItem(index, valueNumber);
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			final boolean valueBool = cell.getBooleanCellValue();
			final String valueBolean = String.valueOf((boolean) valueBool);
			object.setItem(index, valueBolean);
			break;
		default:
			break;
		}
		return object;
	}

}
