package io.github.jhipster.sample.service;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import io.github.jhipster.sample.domain.User;
import jakarta.mail.internet.MimeMessage;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import tech.jhipster.config.JHipsterProperties;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private MessageSource messageSource;

    @Mock
    private SpringTemplateEngine templateEngine;

    @Mock
    private JHipsterProperties jHipsterProperties;

    @Mock
    private JHipsterProperties.Mail mailProperties;

    @InjectMocks
    private MailService mailService;

    @Mock
    private MimeMessage mimeMessage;

    @Test
    void sendEmailShouldSendMail() {
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(jHipsterProperties.getMail()).thenReturn(mailProperties);
        when(mailProperties.getFrom()).thenReturn("test@example.com");

        mailService.sendEmail("user@test.com", "subject", "content", false, true);

        verify(javaMailSender).send(mimeMessage);
    }

    @Test
    void sendEmailShouldNotFailIfMailSenderThrowsException() {
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(jHipsterProperties.getMail()).thenReturn(mailProperties);
        when(mailProperties.getFrom()).thenReturn("test@example.com");

        doThrow(new MailException("boom") {}).when(javaMailSender).send(any(MimeMessage.class));

        assertThatNoException().isThrownBy(() -> mailService.sendEmail("user@test.com", "subject", "content", false, true));
    }

    @Test
    void sendEmailFromTemplateShouldSendMail() {
        User user = new User();
        user.setEmail("user@test.com");
        user.setLogin("user");
        user.setLangKey("en");

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(jHipsterProperties.getMail()).thenReturn(mailProperties);
        when(mailProperties.getFrom()).thenReturn("test@example.com");
        when(mailProperties.getBaseUrl()).thenReturn("http://localhost");

        when(templateEngine.process(eq("mail/activationEmail"), any(Context.class))).thenReturn("email-content");
        when(messageSource.getMessage(eq("email.activation.title"), isNull(), eq(Locale.ENGLISH))).thenReturn("Activation");

        mailService.sendActivationEmail(user);

        verify(javaMailSender).send(mimeMessage);
    }

    @Test
    void sendEmailFromTemplateShouldDoNothingIfUserHasNoEmail() {
        User user = new User();
        user.setLogin("user");
        user.setLangKey("en");

        mailService.sendActivationEmail(user);

        verifyNoInteractions(javaMailSender, templateEngine, messageSource);
    }

    @Test
    void sendPasswordResetMailUsesCorrectTemplate() {
        User user = new User();
        user.setEmail("user@test.com");
        user.setLangKey("en");

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(jHipsterProperties.getMail()).thenReturn(mailProperties);
        when(mailProperties.getFrom()).thenReturn("test@example.com");
        when(mailProperties.getBaseUrl()).thenReturn("http://localhost");

        when(templateEngine.process(eq("mail/passwordResetEmail"), any(Context.class))).thenReturn("reset-content");
        when(messageSource.getMessage(eq("email.reset.title"), isNull(), eq(Locale.ENGLISH))).thenReturn("Reset");

        mailService.sendPasswordResetMail(user);

        verify(templateEngine).process(eq("mail/passwordResetEmail"), any(Context.class));
        verify(javaMailSender).send(mimeMessage);
    }
}
