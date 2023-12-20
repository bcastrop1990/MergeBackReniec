package pe.gob.reniec.rrcc.plataformaelectronica.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.ApiExceptionResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.ConstantUtil;

@Slf4j
@RestControllerAdvice
public class ResponseExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiExceptionResponse> methodArgumentNotValidException(
          MethodArgumentNotValidException exception) {
    return ResponseEntity.ok()
            .body(ApiExceptionResponse.builder()
                    .code(ConstantUtil.ERROR_CODE)
                    .message(exception.getBindingResult().getFieldErrors()
                            .stream().findFirst().get().getDefaultMessage())
                    .build());

  }

  @ExceptionHandler(MissingPathVariableException.class)
  public ResponseEntity<ApiExceptionResponse> missingPathVariableException(
          MissingPathVariableException exception) {
    return ResponseEntity.ok()
            .body(ApiExceptionResponse.builder()
                    .code(ConstantUtil.ERROR_CODE)
                    .message(exception.getMessage())
                    .build());

  }
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ApiExceptionResponse> missingServletRequestParameterException(
          MissingServletRequestParameterException exception) {
    return ResponseEntity.ok()
            .body(ApiExceptionResponse.builder()
                    .code(ConstantUtil.ERROR_CODE)
                    .message(exception.getMessage())
                    .build());

  }
  @ExceptionHandler(MissingServletRequestPartException.class)
  public ResponseEntity<ApiExceptionResponse> missingServletRequestPartException(
          MissingServletRequestPartException exception) {
    return ResponseEntity.ok()
            .body(ApiExceptionResponse.builder()
                    .code(ConstantUtil.ERROR_CODE)
                    .message(exception.getMessage())
                    .build());

  }
  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<ApiExceptionResponse> maxUploadSizeExceededException(
          MissingPathVariableException exception) {
    return ResponseEntity.ok()
            .body(ApiExceptionResponse.builder()
                    .code(ConstantUtil.ERROR_CODE)
                    .message(exception.getMessage())
                    .build());

  }
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiExceptionResponse> exception(Exception exception) {
    log.error(exception.getMessage(), exception);
    return ResponseEntity.ok()
            .body(ApiExceptionResponse.builder()
                    .code(ConstantUtil.ERROR_CODE)
                    .message(ConstantUtil.ERROR_MESSAGE)
                    .build());
  }

  @ExceptionHandler(ApiValidateException.class)
  public ResponseEntity<ApiExceptionResponse> validationException(ApiValidateException exception) {
    return ResponseEntity.ok()
            .body(ApiExceptionResponse.builder()
                    .code(ConstantUtil.ERROR_CODE)
                    .message(exception.getLocalizedMessage())
                    .build());
  }

  @ExceptionHandler(ApiErrorException.class)
  public ResponseEntity<ApiExceptionResponse> errorException(ApiErrorException exception) {
    log.error(exception.getMessage(), exception);
    return ResponseEntity.ok()
            .body(ApiExceptionResponse.builder()
                    .code(ConstantUtil.ERROR_CODE)
                    .message(ConstantUtil.ERROR_MESSAGE)
                    .build());
  }
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ApiExceptionResponse> errorException(AccessDeniedException exception) {
    log.error(exception.getMessage(), exception);
    return ResponseEntity.ok()
            .body(ApiExceptionResponse.builder()
                    .code(ConstantUtil.ERROR_CODE)
                    .message(ConstantUtil.MSG_ACCESS_DENIED)
                    .build());
  }

}