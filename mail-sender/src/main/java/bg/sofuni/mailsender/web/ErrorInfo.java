package bg.sofuni.mailsender.web;

import org.springframework.http.HttpStatus;

public record ErrorInfo(String url, HttpStatus status, String reason) {
}
