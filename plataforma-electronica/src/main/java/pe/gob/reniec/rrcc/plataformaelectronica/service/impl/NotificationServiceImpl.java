package pe.gob.reniec.rrcc.plataformaelectronica.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pe.gob.reniec.rrcc.plataformaelectronica.model.thirdparty.NotificationDto;
import pe.gob.reniec.rrcc.plataformaelectronica.service.NotificationService;

import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private JavaMailSender javaMailSender;

    @Override
    public void send(NotificationDto notification) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false);
            helper.setTo(notification.getTo());
            helper.setSubject(notification.getSubject());
            helper.setFrom(notification.getFrom());
            helper.setText(notification.getMessage(), true);
            javaMailSender.send(message);
            log.info("send email successful to -> " + notification.getTo());
        } catch (Exception e) {
            log.error("error to send mail to -> " + notification.getTo(), e);
        }
    }
}
