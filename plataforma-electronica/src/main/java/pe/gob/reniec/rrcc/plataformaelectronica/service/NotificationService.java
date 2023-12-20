package pe.gob.reniec.rrcc.plataformaelectronica.service;

import pe.gob.reniec.rrcc.plataformaelectronica.model.thirdparty.NotificationDto;

public interface NotificationService {
    void send(NotificationDto notification);
}
