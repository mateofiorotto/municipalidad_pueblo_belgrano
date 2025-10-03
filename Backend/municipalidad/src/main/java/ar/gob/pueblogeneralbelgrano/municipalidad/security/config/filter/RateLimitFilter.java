package ar.gob.pueblogeneralbelgrano.municipalidad.security.config.filter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimitFilter implements Filter {
    //Map para guardar IP
    private final Map<String, Bucket> bucketCache = new ConcurrentHashMap<>();

    //Limites por endpoint
    private final Map<String, Bandwidth> limits = Map.of(
            "/complaints/save", Bandwidth.classic(2, Refill.greedy(2, Duration.ofHours(3))),
            "/auth/login", Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(2)))
    );

    /**
     * Filtro para solicitudes a distintos endpoints que bloquea segun ip luego de un determinado numero de intentos
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String path = httpRequest.getRequestURI(); //endpoint

        //obtener ip, si estamos en local que se asigne 127.0.0.1 para probar (si no, devuelve la IPV6)
        String ipAddress = httpRequest.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = "127.0.0.1";
        }

        //pasar el limite segun el endpoint (definido en el map)
        Bandwidth limit = limits.get(path);

        //si no hay limite asignado, hacer la request normalmente
        if (limit == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //asignar bucket por ip
        String key = ipAddress + ":" + path;
        Bucket bucket = bucketCache.computeIfAbsent(key, k -> Bucket.builder().addLimit(limit).build());

        //consumir token o prohibir
        if (bucket.tryConsume(1)){
            filterChain.doFilter(servletRequest, servletResponse);;
        } else {
            ((HttpServletResponse) servletResponse).setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            servletResponse.getWriter().write("Intentaste demasiado rapido, vuelve a intentarlo más tarde.");
            servletResponse.getWriter().flush();
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
