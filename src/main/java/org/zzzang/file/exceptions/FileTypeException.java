package org.zzzang.file.exceptions;

import org.springframework.http.HttpStatus;
import org.zzzang.global.exceptions.CommonException;

public class FileTypeException extends CommonException {
    public FileTypeException(HttpStatus status) {
        super("FileType", status);
        setErrorCode(true);
    }
}
