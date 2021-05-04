package com.albanj.capitalize.capitalizeback.exception;

import com.albanj.capitalize.capitalizeback.enums.CapitalizeErrorEnum;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ Exception.class })
	@ResponseBody
	public ResponseEntity<?> handleAnyException(Exception e) {
		return errorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ CapitalizeBadRequestException.class, CapitalizeGenericException.class })
	@ResponseBody
	public ResponseEntity<?> handleBadRequest(CapitalizeGenericException e) {
		return capitalizeErrorResponse(e, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ CapitalizeNotFoundException.class })
	@ResponseBody
	public ResponseEntity<?> handleNotFound(CapitalizeGenericException e) {
		return capitalizeErrorResponse(e, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ CapitalizeForbiddenException.class })
	@ResponseBody
	public ResponseEntity<?> handleForbidden(CapitalizeGenericException e) {
		return capitalizeErrorResponse(e, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler({ CapitalizeInternalException.class, })
	@ResponseBody
	public ResponseEntity<?> handleInternalError(CapitalizeGenericException e) {
		return capitalizeErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	protected ResponseEntity<ExceptionMessage> capitalizeErrorResponse(CapitalizeGenericException e,
			HttpStatus status) {
		if (null != e) {
			log.error("Capitalize Error {} - {} - {}", e.getCode(), e.getText(), e.getMessage());
			return response(new ExceptionMessage(e.getCode(), e.getText()), status);
		} else {
			log.error("unknown error caught in RESTController, {}", status);
			return response(null, status);
		}
	}

	protected ResponseEntity<ExceptionMessage> errorResponse(Throwable throwable, HttpStatus status) {
		if (null != throwable) {
			log.error("Internal Error", throwable);
			return response(new ExceptionMessage(CapitalizeErrorEnum.INTERNAL_ERROR.code,
					CapitalizeErrorEnum.INTERNAL_ERROR.text), status);
		} else {
			log.error("unknown error caught in RESTController, {}", status);
			return response(null, status);
		}
	}

	protected <T> ResponseEntity<T> response(T body, HttpStatus status) {
		log.debug("Responding with a status of {}", status);
		return new ResponseEntity<>(body, new HttpHeaders(), status);
	}
}
