package com.joaopedroafluz.devfood.api.exceptionhandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.joaopedroafluz.devfood.domain.exception.EntidadeEmUsoException;
import com.joaopedroafluz.devfood.domain.exception.EntidadeNaoEncontradaException;
import com.joaopedroafluz.devfood.domain.exception.NegocioException;
import com.joaopedroafluz.devfood.domain.exception.ValidacaoException;
import com.joaopedroafluz.devfood.infrastructure.service.email.EmailException;
import com.joaopedroafluz.devfood.infrastructure.service.storage.ArmazenamentoException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. "
            + "Tente novamente e se o problema persistir, entre em contato com o administrador do sistema";

    private final MessageSource messageSource;

    public ApiExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        TipoProblema tipoProblema = TipoProblema.ERRO_NEGOCIO;
        String detalhes = ex.getMessage();

        Problema problema = createProblemaBuilder(status, tipoProblema, detalhes)
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        TipoProblema tipoProblema = TipoProblema.RECURSO_NAO_ENCONTRADO;
        String detalhes = ex.getMessage();

        Problema problema = createProblemaBuilder(status, tipoProblema, detalhes)
                .userMessage(detalhes)
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Object> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        TipoProblema tipoProblema = TipoProblema.ENTIDADE_EM_USO;
        String detalhes = ex.getMessage();

        Problema problema = createProblemaBuilder(status, tipoProblema, detalhes)
                .userMessage(detalhes)
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ArmazenamentoException.class)
    public ResponseEntity<Object> handleArmazenamento(ArmazenamentoException ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        TipoProblema tipoProblema = TipoProblema.ERRO_DO_SISTEMA;
        String detalhes = ex.getMessage();

        Problema problema = createProblemaBuilder(status, tipoProblema, detalhes)
                .userMessage(detalhes)
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<Object> handleEmail(EmailException ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        TipoProblema tipoProblema = TipoProblema.ERRO_DO_SISTEMA;
        String detalhes = ex.getMessage();

        Problema problema = createProblemaBuilder(status, tipoProblema, detalhes)
                .userMessage(detalhes)
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }


    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<Object> handleValidacao(ValidacaoException ex, WebRequest request) {
        return handleValidationInternal(ex.getBindingResult(), ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        TipoProblema tipoProblema = TipoProblema.ERRO_DO_SISTEMA;
        String detalhes = MSG_ERRO_GENERICA_USUARIO_FINAL;

        Problema problema = createProblemaBuilder(status, tipoProblema, detalhes)
                .userMessage(detalhes)
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        }

        if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }

        TipoProblema tipoProblema = TipoProblema.MENSAGEM_INCOMPREENSIVEL;
        String detalhes = "O corpo da requisição está invalido. Verifique erro de sintaxe";

        Problema problema = createProblemaBuilder(status, tipoProblema, detalhes)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        TipoProblema tipoProblema = TipoProblema.RECURSO_NAO_ENCONTRADO;
        String detalhes = String.format("O recurso %s, que você tentou acessar, é inexistente", ex.getRequestURL());

        Problema problema = createProblemaBuilder(status, tipoProblema, detalhes)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        return handleValidationInternal(ex.getBindingResult(), ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
                                                         HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex.getBindingResult(), ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      HttpHeaders headers, HttpStatus status,
                                                                      WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        if (Objects.isNull(body)) {
            body = Problema.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                    .timestamp(OffsetDateTime.now())
                    .build();
        } else if (body instanceof String) {
            body = Problema.builder()
                    .title((String) body)
                    .status(status.value())
                    .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                    .timestamp(OffsetDateTime.now())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers,
                                                       HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());
        TipoProblema tipoProblema = TipoProblema.MENSAGEM_INCOMPREENSIVEL;
        String detalhes = String.format("A propriedade '%s' recebeu o valor '%s'," +
                        "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        Problema problema = createProblemaBuilder(status, tipoProblema, detalhes)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers,
                                                         HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());
        TipoProblema tipoProblema = TipoProblema.MENSAGEM_INCOMPREENSIVEL;
        String detalhes = String.format("A propriedade '%s' não existe. "
                + "Corrija ou remova essa propriedade e tente novamente", path);

        Problema problema = createProblemaBuilder(status, tipoProblema, detalhes)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                    HttpHeaders headers, HttpStatus status,
                                                                    WebRequest request) {
        TipoProblema tipoProblema = TipoProblema.PARAMETRO_INVALIDO;
        String detalhes = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                ex.getName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName());

        Problema problema = createProblemaBuilder(status, tipoProblema, detalhes)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    private Problema.ProblemaBuilder createProblemaBuilder(HttpStatus status, TipoProblema tipoProblema, String detalhes) {
        return Problema.builder()
                .status(status.value())
                .type(tipoProblema.getUri())
                .title(tipoProblema.getTitle())
                .detail(detalhes)
                .timestamp(OffsetDateTime.now());
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
    }

    private ResponseEntity<Object> handleValidationInternal(BindingResult bindingResult, Exception ex,
                                                            HttpHeaders headers, HttpStatus status, WebRequest request) {
        TipoProblema tipoProblema = TipoProblema.DADOS_INVALIDOS;
        String detalhes = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente";

        List<Problema.ObjetoErro> problemaObjetosErro = bindingResult
                .getAllErrors()
                .stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                    String name = objectError.getObjectName();

                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }

                    return Problema.ObjetoErro.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                })
                .collect(Collectors.toList());

        Problema problema = createProblemaBuilder(status, tipoProblema, detalhes)
                .userMessage(detalhes)
                .objetosErro(problemaObjetosErro)
                .build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

}
