package com.example.demo.business;

import java.io.IOException;
import java.util.HashMap;

import com.example.demo.common.BusinessException;

public interface IEntityAndPostgresBusiness {
	HashMap<String, Object> generateEntityAndPostgres(String fileTemplate) throws BusinessException, IOException;
}
