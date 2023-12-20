package pe.gob.reniec.rrcc.plataformaelectronica.model.thirdparty;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDto {
    private String subject;
    private String message;
    private String to;
    private String from;
}
