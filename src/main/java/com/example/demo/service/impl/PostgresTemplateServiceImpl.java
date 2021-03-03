package com.example.demo.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.CommonConstants;
import com.example.demo.common.util.MustacheUtil;
import com.example.demo.common.util.ObjectUtils;
import com.example.demo.common.util.StringUtil;
import com.example.demo.config.GlobalConfig;
import com.example.demo.dto.ColumnPostgresTemplate;
import com.example.demo.dto.ForeignKeyPostgresTemplate;
import com.example.demo.dto.ListTablePostgresTemplate;
import com.example.demo.dto.TablePostgresTemplate;
import com.example.demo.dto.request.TemplateRecordEntityRequest;
import com.example.demo.dto.request.TemplateTableEntitylRequest;
import com.example.demo.service.IPostgresTemplateService;

@Service
public class PostgresTemplateServiceImpl implements IPostgresTemplateService {

	@Autowired
	private GlobalConfig globalConfig;

	/**
	 * Generate File PostgrestSql
	 */
	@Override
	public void generateFilePostgresTableSql(ListTablePostgresTemplate data) throws IOException {
		String fileName = globalConfig.getOutput().getSqlPostgres();
		// Export file
		MustacheUtil.exportFileWithMustache(data, globalConfig.getMustache().getTemplatePostgres(),
				globalConfig.getOutput().getFolderPostgres(), fileName);
	}

	/**
	 * Input: List table in excel template Output: Template table postgresSQL
	 * 
	 * @param request
	 * @return
	 */
	@Override
	public ListTablePostgresTemplate generatePostgresTemplate(List<TemplateTableEntitylRequest> request) {
		ListTablePostgresTemplate listTableResponse = new ListTablePostgresTemplate();
		List<TablePostgresTemplate> listTableTemplateResponse = new ArrayList<>();
		for (TemplateTableEntitylRequest excelTemplate : request) {
			// Table and PK
			String tableNameResponse = StringUtil.formatValueLower(excelTemplate.getTableName());
			String primaryKeyReponse = null;
			Boolean autoIncrementResponse = false;
			List<TemplateRecordEntityRequest> records = excelTemplate.getRecords();

			// Columns
			List<ColumnPostgresTemplate> listColumnsResponse = new ArrayList<>();
			// Foreign Key
			List<ForeignKeyPostgresTemplate> listForeignKeyResponse = new ArrayList<>();

			int indexCommna = 0;
			int numberColumn = records.size();
			for (TemplateRecordEntityRequest item : records) {
				++indexCommna;
				String columnName = StringUtil.formatValueLower(item.getColumnName());
				String dataType = StringUtil.formatValueLower(item.getDataType());
				Boolean isPrimaryKey = item.getPrimaryKey();
				Boolean isForiegnKey = item.getForeignKey();
				String length = item.getLength();
				Boolean isNotNull = item.getNotNull();
				Boolean isAutoIncement = item.getAutoIncement();
				String defaultValue = item.getDefaultValue();
				String notNullResponse = null;
				String commaResponse = CommonConstants.COMMA;

				if (isPrimaryKey != null && isPrimaryKey && isAutoIncement != null && isAutoIncement) {
					primaryKeyReponse = columnName;
					autoIncrementResponse = isAutoIncement;
				}

				if (dataType != null && dataType.equals(CommonConstants.STR_VARCHAR)) {
					dataType = CommonConstants.STR_VARCHAR_POSTGRES;
				}

				if (ObjectUtils.isNotEmpty(length)) {
					length = String.format(CommonConstants.BRACKET_BETWEEN, length);
				}

				if (ObjectUtils.isNotEmpty(defaultValue)) {
					// format default value string or text
					if (CommonConstants.STR_VARCHAR_POSTGRES.equalsIgnoreCase(dataType)
							|| CommonConstants.STR_TEXT.equalsIgnoreCase(dataType)) {
						defaultValue = String.format(CommonConstants.FORMAT_DEFAULT_VALUE_STRING, defaultValue);
					}

					defaultValue = String.format(CommonConstants.FORMAT_DEFAULT_VALUE, defaultValue);
				}

				if (isNotNull != null && isNotNull) {
					notNullResponse = CommonConstants.STR_NOT_NULL;
				}

				if (isForiegnKey != null && isForiegnKey) {
					ForeignKeyPostgresTemplate foreignKeyTemplateResponse = generateForeignKeyTableInPostgres(
							columnName);
					listForeignKeyResponse.add(foreignKeyTemplateResponse);
				}

				// Format comma end column
				if (indexCommna == numberColumn) {
					commaResponse = "";
				}

				// Column PostgreSQL
				ColumnPostgresTemplate columnResponse = new ColumnPostgresTemplate();
				columnResponse.setColumnName(columnName);
				columnResponse.setDataType(dataType);
				columnResponse.setLength(length);
				columnResponse.setDefaultValue(defaultValue);
				columnResponse.setNotNull(notNullResponse);
				columnResponse.setComma(commaResponse);

				listColumnsResponse.add(columnResponse);
			}

			// Table PostgreSQL template
			TablePostgresTemplate tableResponse = new TablePostgresTemplate();
			tableResponse.setTableName(tableNameResponse);
			tableResponse.setPrimaryKey(primaryKeyReponse);
			tableResponse.setAutoIncrement(autoIncrementResponse);
			tableResponse.setColumns(listColumnsResponse);
			tableResponse.setForeignKeys(listForeignKeyResponse);

			listTableTemplateResponse.add(tableResponse);
		}

		listTableResponse.setTables(listTableTemplateResponse);

		return listTableResponse;
	}

	/**
	 * Generate foreign key in table postgres
	 * 
	 * @param columnName
	 * @return
	 */
	private ForeignKeyPostgresTemplate generateForeignKeyTableInPostgres(String columnName) {
		ForeignKeyPostgresTemplate foreignKeyTemplateResponse = new ForeignKeyPostgresTemplate();
		UUID uuid = UUID.randomUUID();
		String foreignKeyConstraintResponse = String.format(CommonConstants.FORMAT_FOREIGN_KEY_CONSTRAINT,
				uuid.toString().replace(CommonConstants.SPECIAL_CHARACTER_HYPHEN, ""));
		String tableReferenceResponse = getTableReference(columnName);
		String foreignKeyResponse = columnName;

		// Foreign key PostgreSQL
		foreignKeyTemplateResponse.setForeignKey(foreignKeyResponse);
		foreignKeyTemplateResponse.setForeignKeyConstraint(foreignKeyConstraintResponse);
		foreignKeyTemplateResponse.setTableReference(tableReferenceResponse);

		return foreignKeyTemplateResponse;
	}

	/**
	 * Get table reference in postgres
	 * 
	 * @param value
	 * @return
	 */
	private String getTableReference(String value) {
		int indexSubString = value.lastIndexOf(CommonConstants.UNDERSCORE);
		value = value.substring(0, indexSubString);
		return value;
	}

}
