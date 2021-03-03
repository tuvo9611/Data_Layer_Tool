package com.example.demo.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.AnnotationConstants;
import com.example.demo.common.CommonConstants;
import com.example.demo.common.GenerateEntity;
import com.example.demo.common.util.MustacheUtil;
import com.example.demo.config.GlobalConfig;
import com.example.demo.dto.AnnotationTemplate;
import com.example.demo.dto.ClassTemplate;
import com.example.demo.dto.PropertyTemplate;
import com.example.demo.dto.RepositoryTemplate;
import com.example.demo.dto.request.TemplateRecordEntityRequest;
import com.example.demo.dto.request.TemplateTableEntitylRequest;
import com.example.demo.service.IEntityAndRepositoryTemplateService;

@Service
public class EntityAndRepositoryTemplateServiceImpl implements IEntityAndRepositoryTemplateService {
	@Autowired
	private GlobalConfig globalConfig;

	/**
	 * 1. Check is BaseEnity
	 * 2. Check have foriegn key to generate OneToOne or OneToMany
	 * 3. Add Column 
	 * 4. Create class template mustache
	 * 5. Generate ManyToOne or OneToOne Another Class Relationship
	 * 6. Import Library
	 * 7. Copy class BaseEntity
	 */
	@Override
	public List<ClassTemplate> generateClassTemplate(List<TemplateTableEntitylRequest> listTable) throws IOException {
		List<ClassTemplate> listClassTemplate = new ArrayList<>();
		boolean isExtendBaseEntity = false;
		for (TemplateTableEntitylRequest tbl : listTable) {
			String tableName = tbl.getTableName();
			List<TemplateRecordEntityRequest> listRecord = tbl.getRecords();
			List<PropertyTemplate> listProperty = new ArrayList<>();
			for (TemplateRecordEntityRequest record : listRecord) {
				String columnName = record.getColumnName();
				// 1. Ignore base entity
				if (CommonConstants.IGNORE_BASE_ENTITY.contains(columnName)) {
					isExtendBaseEntity = true;
				} else {
					// 2. Get Column Have FK
					if (record.getForeignKey() != null && record.getForeignKey()) {
						if (GenerateEntity.checkOneToOne(record)) {
							listProperty.add(GenerateEntity.generatePropertiesOneToOne(columnName));
						} else {
							listProperty.add(GenerateEntity.generatePropertiesManyToOne(columnName));
						}
					} else {
						// 3. Get Column Normal
						PropertyTemplate property = GenerateEntity.generateProperty(tbl.getTableName(), record);
						listProperty.add(property);
					}
				}
			}
			// 4. Create Class Template Mustache
			ClassTemplate classTemplate = GenerateEntity.createClassTemplate(tableName, listProperty,
					isExtendBaseEntity);
			listClassTemplate.add(classTemplate);
		}

		// 5. Create Relationship OneToMany or OneToOne Another Class
		GenerateEntity.mappingOneToManyOrOneToOne(listClassTemplate);

		// 6. Import Library
		GenerateEntity.importLibrary(listClassTemplate);

		if (isExtendBaseEntity) {
			// 7. Copy file entity
			copyBaseEntity();
		}
		
		return listClassTemplate;
	}

	@Override
	public void generateFileEntityAndRepository(List<ClassTemplate> request)
			throws IOException {
		for (ClassTemplate item : request) {
			generateEntityJava(item);
			generateRepositoryJava(item);
		}
	}

	/**
	 * Generate File Entity .java
	 */
	private void generateEntityJava(ClassTemplate classTemplate) throws IOException {
		String packageName = globalConfig.getPackageName().getEntity();
		classTemplate.setPackageName(packageName);

		String fileName = classTemplate.getClassName();

		// Export file
		MustacheUtil.exportFileWithMustache(classTemplate, globalConfig.getMustache().getTemplateEntity(),
				globalConfig.getOutput().getFolderEntity(),
				String.format(CommonConstants.FILE_WITH_ENTENSION_JAVA, fileName));
	}

	/**
	 * Generate File Repository .Java
	 */
	private void generateRepositoryJava(ClassTemplate classTemplate) throws IOException {
		String dataType = null;
		String className = classTemplate.getClassName();

		// Get data type in primary key
		for (PropertyTemplate prop : classTemplate.getProperties()) {
			for (AnnotationTemplate anno : prop.getAnnotations()) {
				if (anno.getAnnotationName().equals(AnnotationConstants.ANNOTATION_PRIMARY_KEY)) {
					dataType = prop.getDataType();
					break;
				}
			}
		}

		// Check have primary key to export file
		if (dataType != null) {
			RepositoryTemplate repository = new RepositoryTemplate();
			repository.setPackageEntity(globalConfig.getPackageName().getEntity());
			repository.setClassName(className);
			repository.setDataType(dataType);
			String packageName = globalConfig.getPackageName().getRepository();
			repository.setPackageName(packageName);

			// Export file
			MustacheUtil.exportFileWithMustache(repository, globalConfig.getMustache().getTemplateRepository(),
					globalConfig.getOutput().getFolderRepository(),
					String.format(CommonConstants.FILE_REPOSITORY, className));
		}
	}

	/**
	 * Copy file BaseEntity.java to output entity
	 */
	private void copyBaseEntity() throws IOException {
		Path target = Paths.get(globalConfig.getOutput().getFolderEntity() + "\\" + CommonConstants.FILE_BASE_ENTITY);
		Path source = Paths.get(CommonConstants.DIRECTORY_FILE_BASE_ENTITY);
		Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
	}

}
