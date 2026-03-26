package com.springboot.api.service;

import com.springboot.api.model.ContactMessage;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.owner-email}")
    private String ownerEmail;

    @Value("${app.mail.owner-name}")
    private String ownerName;

    // Notify YOU when someone submits the contact form
    @Async
    public void sendOwnerNotification(ContactMessage msg) {
        try {
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mail, true, "UTF-8");

            helper.setFrom(ownerEmail);
            helper.setTo(ownerEmail);

            helper.setSubject(
                    "📬 New Contact: " + safe(msg.getSubject())
            );

            helper.setText(
                    buildOwnerEmailBody(msg),
                    true
            );

            mailSender.send(mail);

            log.info(
                    "Owner notification sent for message id: {}",
                    msg.getId()
            );

        } catch (MessagingException e) {

            log.error(
                    "Failed to send owner notification: {}",
                    e.getMessage()
            );

        }
    }

    // Send AUTO-REPLY to the person who submitted the form
    @Async
    public void sendAutoReply(ContactMessage msg) {
        try {
            MimeMessage mail = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(mail, true, "UTF-8");

            helper.setFrom(ownerEmail, ownerName);

            helper.setTo(msg.getEmail());

            helper.setSubject(
                    "Thanks for reaching out, "
                            + safe(msg.getName())
                            + "! ✉️"
            );

            helper.setText(
                    buildAutoReplyBody(msg),
                    true
            );

            mailSender.send(mail);

            log.info(
                    "Auto-reply sent to: {}",
                    msg.getEmail()
            );

        } catch (MessagingException e) {

            log.error(
                    "Failed to send auto-reply to {}: {}",
                    msg.getEmail(),
                    e.getMessage()
            );

        } catch (Exception e) {

            log.error(
                    "Unexpected error sending auto-reply: {}",
                    e.getMessage()
            );

        }
    }

    private String buildOwnerEmailBody(ContactMessage msg) {
        return """
                <html><body style="font-family: Arial, sans-serif; color: #333;">
                  <h2 style="color: #4A90E2;">📬 New Contact Form Submission</h2>
                  <table style="border-collapse: collapse; width: 100%%;">
                    <tr><td style="padding: 8px; font-weight: bold;">Name</td><td style="padding: 8px;">%s</td></tr>
                    <tr style="background:#f9f9f9"><td style="padding: 8px; font-weight: bold;">Email</td><td style="padding: 8px;">%s</td></tr>
                    <tr><td style="padding: 8px; font-weight: bold;">Phone</td><td style="padding: 8px;">%s</td></tr>
                    <tr style="background:#f9f9f9"><td style="padding: 8px; font-weight: bold;">Subject</td><td style="padding: 8px;">%s</td></tr>
                    <tr>
                      <td style="padding: 8px; font-weight: bold; vertical-align: top;">
                        Message
                      </td>
                      <td style="padding: 8px;">%s</td>
                    </tr>
                  </table>

                  <p style="color:#888; font-size:12px;">
                    Sent from your portfolio contact form
                  </p>

                </body></html>
                """.formatted(
                safe(msg.getName()),
                safe(msg.getEmail()),
                safe(msg.getPhone() != null
                        ? msg.getPhone()
                        : "—"),
                safe(msg.getSubject()),
                safe(msg.getMessage())
        );
    }

    private String buildAutoReplyBody(ContactMessage msg) {
        return """
                <html>
                <body style="font-family: Arial, sans-serif; color: #333; max-width: 600px;">

                  <h2 style="color: #4A90E2;">
                    Hey %s, thanks for getting in touch! 👋
                  </h2>

                  <p>
                    I've received your message and will get back to you as soon as possible.
                  </p>

                  <hr style="border: none; border-top: 1px solid #eee;" />

                  <h4>Your message:</h4>

                  <blockquote style="
                        border-left: 4px solid #4A90E2;
                        margin: 0;
                        padding: 10px 16px;
                        background: #f9f9f9;">

                    <strong>%s</strong>
                    <br/><br/>
                    %s

                  </blockquote>

                  <hr style="border: none; border-top: 1px solid #eee;" />

                  <p>
                    Talk soon,<br/>
                    <strong>%s</strong>
                  </p>

                </body>
                </html>
                """.formatted(
                safe(msg.getName()),
                safe(msg.getSubject()),
                safe(msg.getMessage()),
                safe(ownerName)
        );
    }

    // minimal safety helper
    private String safe(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("%", "%%");
    }
}