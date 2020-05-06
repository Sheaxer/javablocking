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
 * <div class="en">Class that implements custom error handling.</div>
 * <div class="sk">Trieda ktorá implementuje vlastné spravocanie výnimiek.</div>
 */
@RestControllerAdvice
public class ErrorHandler {
    /***
     * <div class="en">Handles ReportedOverlimitTransactionNotFoundException
     * by returning the error code and sending HTTP code NOT_FOUND - 404.</div>
     * <div class="sk">Spracúváva ReportedOverlimitTransactionNotFoundException výnimku
     * vrátením HTTP kódu 404 Not Found a chybovej hlášky v tele správy.</div>
     * @param ex <div class="en">caught exception.</div>
     *           <div class="sk">odchytená výnimka.</div>
     * @return <div class="en">The List containing the error message of ex.</div>
     * <div class="sk">Zoznam obsahujúcí chybovú hlášku v ex.</div>
     */
    @ExceptionHandler(ReportedOverlimitTransactionNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public List<String> handleNotFound(ReportedOverlimitTransactionNotFoundException ex)  {
        return new ArrayList<>(Collections.singleton(ex.getMessage()));
    }

    /***
     * <div class="en">Handles ReportedOverlimitTransactionBadRequestException by returning
     * the error code and sending HTTP code BAD_REQUEST - 400.</div>
     * <div class="sk">Spracúvava ReportedOverlimitTransactionBadRequestException výnimku vrátením
     * HTTP kódu BAD_REQUEST - 400 a chybovej hlášky v tele správy.</div>
     * @param ex <div class="en">caught exception.</div>
     *           <div class="sk">odchytená výnimka.</div>
     * @return <div class="en">The list containing the error message of ex.</div>
     * <div class="sk">Zoznam, ktorý obsahuje chybovú hlášku v ex.</div>
     */
    @ExceptionHandler(ReportedOverlimitTransactionBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<String> handleBadRequest(ReportedOverlimitTransactionBadRequestException ex)
    {
        return new ArrayList<>(Collections.singleton(ex.getMessage()));
    }
    /***
     * <div class="en">Handles validation errors that occur during put and post REST methods. Returns
     * HTTTP code BAD_REQUEST - 400 and list of validation errors.</div>
     * <div class="sk">Spracuváva validačné výnimky ktoré môžu nastať počas PUT a POST REST metód. Vracia
     * HTTP kód BAD_REQUEST - 400 a zoznam validačných chýb v tele odpovede.</div>
     * @param ex <div class="en">caught validation exception.</div>
     *           <div class="sk">zachytená validačná výnimka.</div>
     * @return <div class="en">List of validation error messages.</div>
     * <div class="sk">Zoznam validačných chyhových hlášok.</div>
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
     * <div class="en">Handles the HttpRequestMethodNotSupportedException.</div>
     * <div class="sk">Spracuje HttpRequestMethodNotSupportedException výnimku.</div>
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
     * <div class="en">Handles the HttpRequestMethodNotSupportedException.</div>
     * <div class="sk">Spracuje HttpRequestMethodNotSupportedException výnimku.</div>
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
     * <div class="en">Handles the HttpRequestMethodNotSupportedException.</div>
     * <div class="sk">Spracuje HttpRequestMethodNotSupportedException výnimku.</div>
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
