package com.example.demo.business;

import java.io.IOException;
import java.util.HashMap;

import com.example.demo.common.BusinessException;

public interface IControllerAndDtoBusiness {
	HashMap<String, Object> generateControllerAndDto(String fileTemplate) throws BusinessException, IOException;
}
