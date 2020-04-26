package stuba.fei.gono.java.blocking.errors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import stuba.fei.gono.java.errors.ReportedOverlimitTransactionBadRequestException;
import stuba.fei.gono.java.errors.ReportedOverlimitTransactionNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

/***
 * <div class="en">Class implementing custom error handling.</div>
 */
@RestControllerAdvice
public class ErrorHandler {
    /***
     * Handles not found exception by returning the list containing the error code and sending the HTTP
     * NOT_FOUND 404 code.
     * @param ex caught exception.
     * @return List containing the error code.
     * @see ReportedOverlimitTransactionNotFoundException
     */
    @ExceptionHandler(ReportedOverlimitTransactionNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public List<String> springHandleNotFound(Exception ex)  {
        return new ArrayList<>(Collections.singleton(ex.getMessage()));
    }

    /***
     * Handles the bad request exception by returning the list containing the error code and sends the HTTP
     * BAD_REQUEST 400 code.
     * @param ex caught exception
     * @return List containing the error code.
     * @see ReportedOverlimitTransactionBadRequestException
     */
    @ExceptionHandler(ReportedOverlimitTransactionBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<String> handleBadRequestException( ReportedOverlimitTransactionBadRequestException ex)
    {
        return new ArrayList<>(Collections.singleton(ex.getMessage()));
    }
    /***
     * Handles validation errors by transforming into JSON array and returns the error codes with HTTP code
     * BAD_REQUEST 400.
     * @param ex caught validation exception.
     * @return List of validation error messages.
     * @see MethodArgumentNotValidException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<String> handleValidationExceptions(MethodArgumentNotValidException ex)
    {
        return ex.getBindingResult()
                .getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
    }

    /***
     * Handles the HttpMessageNotReadableException
     * @param ex caught exception.
     * @return error code.
     */
    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMessageNotReadableException(
            org.springframework.http.converter.HttpMessageNotReadableException ex)
    {
        return ex.getMessage();
    }

    /***
     * Handles the HttpRequestMethodNotSupportedException.
     * @param ex caught exception.
     * @return "METHOD_NOT_ALLOWED" error code.
     */
    @ExceptionHandler(org.springframework.web.HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleMethonNotSupported(org.springframework.web.HttpRequestMethodNotSupportedException ex)
    {
        return "METHOD_NOT_ALLOWED";
    }
    /***
     * Handles the HttpRequestMethodNotSupportedException.
     * @param ex caught exception.
     * @return "MEDIATYPE_INVALID" error code.
     */
    @ExceptionHandler(org.springframework.web.HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleTypeNotSupported(org.springframework.web.HttpMediaTypeNotSupportedException ex)
    {
        return "MEDIATYPE_INVALID";
    }
    /***
     * Handles the HttpRequestMethodNotSupportedException.
     * @param ex caught exception.
     * @return "ILLEGAL_ARGUMENT" error code.
     */
    @ExceptionHandler(java.lang.IllegalArgumentException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgument(java.lang.IllegalArgumentException ex)
    {
        return "ILLEGAL_ARGUMENT";
    }


}
