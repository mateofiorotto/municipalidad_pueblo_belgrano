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
            "/complaints/save", Bandwidth.classic(1, Refill.greedy(1, Duration.ofHours(6))),
            "/auth/login", Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(2)))
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String path = httpRequest.getRequestURI();

        String ipAddress = httpRequest.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = httpRequest.getRemoteAddr();
        }

        System.out.println(path);
        System.out.println(ipAddress);

        // buscar limite para este path
        Bandwidth limit = limits.get(path);

        if (limit == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String key = ipAddress + ":" + path;
        Bucket bucket = bucketCache.computeIfAbsent(key, k -> Bucket.builder().addLimit(limit).build());

        if (bucket.tryConsume(1)){
            filterChain.doFilter(servletRequest, servletResponse);
            System.out.println(ipAddress);
        } else {
            ((HttpServletResponse) servletResponse).setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            servletResponse.getWriter().write("Intentaste demasiado rapido, vuelve a intentarlo.");
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
