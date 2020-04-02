package com.nineone.nocm.exception;

import com.nineone.nocm.domain.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler{

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException e){
        log.error("RuntimeException",e);
        final ApiResponse response = ApiResponse.builder().error("400").message("BAD_REQUEST").build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({FileSizeLimitExceededException.class})
    public ResponseEntity<ApiResponse> handleFileSizeLimitExceededException(FileSizeLimitExceededException e){
        log.error("FileSizeLimitExceededException",e);
        final ApiResponse response = ApiResponse.builder().error("400").message("BAD_REQUEST").build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e){
        log.error(e.getMaxUploadSize()+"");
        log.error(e.getMessage());
        log.error("MaxUploadSizeExceededException",e);
        final ApiResponse response = ApiResponse.builder().error("400").message("BAD_REQUEST").build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({SizeLimitExceededException.class})
    public ResponseEntity<ApiResponse> handleSizeLimitExceededException(SizeLimitExceededException e){
        log.error("SizeLimitExceededException",e);
        final ApiResponse response = ApiResponse.builder().error("400").message("BAD_REQUEST").build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



}
// 참조 :https://cheese10yun.github.io/spring-guide-exception/