package com.mcbank.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST )
public class InvalidRequestException extends RuntimeException {

  public InvalidRequestException() {
    super();
  }
  public InvalidRequestException(String message, Throwable cause) {
    super(message, cause);
  }
  public InvalidRequestException(String message) {
    super(message);
  }
  public InvalidRequestException(Throwable cause) {
    super(cause);
  }

  @ResponseBody
  public String getMessage() {
    return super.getMessage();
  }
}
