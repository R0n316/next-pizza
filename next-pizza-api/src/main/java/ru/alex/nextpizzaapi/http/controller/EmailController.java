package ru.alex.nextpizzaapi.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alex.nextpizzaapi.service.EmailService;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<HttpStatus> sendMail() {
        emailService.sendEmail("manyakinsasha@gmail.com", "email from JavaSpringBoot", "test sending");
        return new ResponseEntity<>(OK);
    }
}
