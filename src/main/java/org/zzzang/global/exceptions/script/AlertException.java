package org.zzzang.global.exceptions.script;

import org.springframework.http.HttpStatus;
import org.zzzang.global.exceptions.CommonException;

public class AlertException extends CommonException {
    public AlertException(String message, HttpStatus status) {
        super(message, status);
    }
}
