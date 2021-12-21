package pl.springboot2.karoljanik.wykopclone.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ResponseStatus(HttpStatus.NOT_FOUND)
public class WykopCloneException extends RuntimeException {
    public WykopCloneException(String message) {
        super(message);
            }
}
