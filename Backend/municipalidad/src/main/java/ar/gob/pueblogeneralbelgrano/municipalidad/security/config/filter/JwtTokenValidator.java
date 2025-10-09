package ar.gob.pueblogeneralbelgrano.municipalidad.security.config.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import ar.gob.pueblogeneralbelgrano.municipalidad.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class JwtTokenValidator extends OncePerRequestFilter {
    private JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * Metodo que actua como interceptor para las request, validando el JWT
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //obtener jwt
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            try {
                jwtToken = jwtToken.substring(7);

                //validar token
                DecodedJWT decodedJwt = jwtUtils.validateToken(jwtToken);

                //claims del token, usuario, roles y permisos. Convertir authorities en una lista
                String username = jwtUtils.extractUsername(decodedJwt);
                String authorities = jwtUtils.getSpecificClaim(decodedJwt, "authorities").asString();
                Collection authoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                //obtener contexto de seguridad y crear un objeto auth con usuario y permisos
                SecurityContext context = SecurityContextHolder.getContext();
                Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authoritiesList);
                context.setAuthentication(auth);
                SecurityContextHolder.setContext(context); //actualiza el contexto
            } catch (JWTVerificationException e) {
                // Token inválido o expirado → devolver 401
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Token inválido o expirado.\"}");
                response.getWriter().flush();
                return; // detener el filtro
            }
        }
        filterChain.doFilter(request, response);
    }
}
