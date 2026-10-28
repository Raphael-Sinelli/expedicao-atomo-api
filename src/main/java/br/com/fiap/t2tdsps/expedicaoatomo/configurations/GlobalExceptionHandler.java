package br.com.fiap.t2tdsps.expedicaoatomo.configurations;

import br.com.fiap.t2tdsps.expedicaoatomo.dto.ErrorCustomResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.dto.ErrorFieldCustom;
import br.com.fiap.t2tdsps.expedicaoatomo.dto.ValidationErrorResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.exceptions.ResourceNotFoundException;
import br.com.fiap.t2tdsps.expedicaoatomo.exceptions.TituloDuplicadoException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorCustomResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorCustomResponse> handleNoResourceFound(NoResourceFoundException ex) {
        return build(HttpStatus.NOT_FOUND, "Esta rota não existe. As rotas da API estão em /swagger-ui.html");
    }

    @ExceptionHandler(TituloDuplicadoException.class)
    public ResponseEntity<ErrorCustomResponse> handleTituloDuplicado(TituloDuplicadoException ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    // Segunda barreira do título único: se duas requisições passarem juntas pela checagem do service,
    // a constraint UNIQUE de nm_titulo no banco recusa a segunda.
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorCustomResponse> handleDataIntegrity(DataIntegrityViolationException ex) {
        return build(HttpStatus.CONFLICT, "A operação viola uma restrição do banco de dados, como título repetido");
    }

    // @Valid no @RequestBody: getFieldErrors() traz os erros de campo e getGlobalErrors() os de classe.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ErrorFieldCustom> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ErrorFieldCustom(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        List<ErrorFieldCustom> globalErrors = ex.getBindingResult().getGlobalErrors().stream()
                .map(globalError -> new ErrorFieldCustom(globalError.getObjectName(), globalError.getDefaultMessage()))
                .toList();

        return validationError(Stream.concat(fieldErrors.stream(), globalErrors.stream()).toList());
    }

    // @Validated na classe do controller: @Positive no id, @UfValida no filtro, @NotBlank no termo etc.
    // O caminho da violação vem como "metodo.parametro"; fica só o nome do parâmetro.
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        List<ErrorFieldCustom> errors = ex.getConstraintViolations().stream()
                .map(violacao -> new ErrorFieldCustom(nomeDoParametro(violacao.getPropertyPath().toString()),
                        violacao.getMessage()))
                .toList();
        return validationError(errors);
    }

    // Validação de parâmetros feita pelo próprio Spring MVC, quando ela roda sem o proxy do @Validated.
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ValidationErrorResponse> handleHandlerMethodValidation(HandlerMethodValidationException ex) {
        List<ErrorFieldCustom> errors = ex.getParameterValidationResults().stream()
                .flatMap(resultado -> resultado.getResolvableErrors().stream()
                        .map(erro -> new ErrorFieldCustom(resultado.getMethodParameter().getParameterName(),
                                erro.getDefaultMessage())))
                .toList();
        return validationError(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorCustomResponse> handleNotReadable(HttpMessageNotReadableException ex) {
        return build(HttpStatus.BAD_REQUEST,
                "JSON inválido: confira vírgulas, aspas e o tipo de cada campo. tipo aceita INSTALACAO, NATURAL ou HISTORICO");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorCustomResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String mensagem = "Parâmetro '" + ex.getName() + "' inválido: " + ex.getValue();
        if (ex.getRequiredType() != null && ex.getRequiredType().isEnum()) {
            mensagem += ". Valores aceitos: " + Arrays.toString(ex.getRequiredType().getEnumConstants());
        }
        return build(HttpStatus.BAD_REQUEST, mensagem);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorCustomResponse> handleMissingParameter(MissingServletRequestParameterException ex) {
        return build(HttpStatus.BAD_REQUEST, "Parâmetro obrigatório '" + ex.getParameterName() + "' não foi informado");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorCustomResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return build(HttpStatus.METHOD_NOT_ALLOWED,
                "Método " + ex.getMethod() + " não é aceito nesta rota. Métodos aceitos: " + ex.getSupportedHttpMethods());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorCustomResponse> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        return build(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Envie o corpo em JSON, com o cabeçalho Content-Type: application/json");
    }

    // Erro não previsto: o detalhe vai para o log do servidor e o cliente recebe só a mensagem genérica.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorCustomResponse> handleGeneric(Exception ex) {
        log.error("Erro não tratado na API", ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno inesperado");
    }

    private String nomeDoParametro(String caminho) {
        return caminho.substring(caminho.lastIndexOf('.') + 1);
    }

    private ResponseEntity<ValidationErrorResponse> validationError(List<ErrorFieldCustom> errors) {
        ValidationErrorResponse body = new ValidationErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errors
        );
        return ResponseEntity.badRequest().body(body);
    }

    private ResponseEntity<ErrorCustomResponse> build(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(new ErrorCustomResponse(LocalDateTime.now(), status.value(), status.getReasonPhrase(), message));
    }
}
