package ar.gob.pueblogeneralbelgrano.municipalidad.service.captcha;

import jakarta.servlet.http.HttpServletRequest;

public interface ICaptchaService {
    /**
     * Metodo que procesa la respuesta del recaptcha
     * @param response
     */
    public void processResponse(String response, HttpServletRequest request);

    /**
     * Metodo que sanitiza la respuesta del cliente con el recaptcha
     * @param response
     * @return true / false
     */
    public boolean responseSanityCheck(String response);

    /**
     * Obtener el recaptcha secret
     * @return
     */
    public String getReCaptchaSecret();

    /**
     * Obtener ip del cliente
     * @param request
     */
    public String getClientIP(HttpServletRequest request);
}
