package ar.gob.pueblogeneralbelgrano.municipalidad.service.captcha;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.captcha.GoogleResponse;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.BadRequestException;
import ar.gob.pueblogeneralbelgrano.municipalidad.security.config.CaptchaSettings;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.regex.Pattern;

@Service
public class CaptchaService implements ICaptchaService {

    private CaptchaSettings captchaSettings;
    private RestOperations restTemplate;

    public CaptchaService(CaptchaSettings captchaSettings, RestOperations restTemplate) {
        this.captchaSettings = captchaSettings;
        this.restTemplate = restTemplate;
    }

    private static Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");

    @Override
    public void processResponse(String response, HttpServletRequest request) {
        if(!responseSanityCheck(response)){
            throw new BadRequestException("Él ReCaptcha es incorrecto o expiro.");
        }

        URI verifyUri = URI.create(String.format(
                "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s",
                captchaSettings.getSecret(), response, getClientIP(request)
        ));

        GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);

        if (googleResponse == null || !googleResponse.isSuccess() || googleResponse.hasClientError()) {
            throw new BadRequestException("Él ReCaptcha es incorrecto o expiro.");
        }
    }

    @Override
    public boolean responseSanityCheck(String response) {
        return StringUtils.hasLength(response) && RESPONSE_PATTERN.matcher(response.trim()).matches();
    }

    @Override
    public String getReCaptchaSecret() {
        return captchaSettings.getSecret();
    }

    @Override
    public String getClientIP(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = "127.0.0.1";
        }

        return ipAddress;
    }
}
