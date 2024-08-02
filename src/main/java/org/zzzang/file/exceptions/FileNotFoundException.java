package org.zzzang.file.exceptions;

import org.springframework.http.HttpStatus;
import org.zzzang.global.exceptions.script.AlertBackException;

public class FileNotFoundException extends AlertBackException {
    public FileNotFoundException() {
        super("NotFound.file", HttpStatus.NOT_FOUND);
        setErrorCode(true);
    }
}
