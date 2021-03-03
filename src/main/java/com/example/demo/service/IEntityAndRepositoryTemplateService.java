package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import com.example.demo.dto.ClassTemplate;
import com.example.demo.dto.request.TemplateTableEntitylRequest;

public interface IEntityAndRepositoryTemplateService {
	
	/**
     * 1. Check is BaseEnity
	 * 2. Check have foriegn key to generate OneToOne or OneToMany
	 * 3. Add Column 
	 * 4. Create class template mustache
	 * 5. Generate ManyToOne
	 * 6. Import Library
	 * 7. Copy class BaseEntity
	 * @param request
	 * @return
	 */
	public List<ClassTemplate> generateClassTemplate(List<TemplateTableEntitylRequest> request) throws IOException;
	
	/**
	 * Generate entity .java and repository .java
	 * @param request
	 * @throws IOException
	 */
	public void generateFileEntityAndRepository(List<ClassTemplate> request) throws IOException;
}
