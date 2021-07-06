package com.claytoncalixto.dscatalog.services.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.claytoncalixto.dscatalog.resources.exceptions.FieldMessage;
import com.claytoncalixto.dscatalog.resources.exceptions.StandardError;

public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<>();

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addErrors(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}
}
