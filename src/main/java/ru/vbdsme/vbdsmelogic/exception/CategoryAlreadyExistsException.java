package ru.vbdsme.vbdsmelogic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The category already exists")
public class CategoryAlreadyExistsException extends RuntimeException {

}
