package ru.vbdsme.vbdsmelogic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The item already exists")
public class ItemAlreadyExistsException extends RuntimeException {
}
