package tp.eni_store.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tp.eni_store.bo.User;
import tp.eni_store.dao.user.mongo.DAOUserMongo;
import tp.eni_store.service.ServiceResponse;

import java.io.IOException;

import static tp.eni_store.service.ServicesConstants.CD_SUCCESS_DEFAULT;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    tp.eni_store.service.AuthService authService;
    @Autowired
    private DAOUserMongo dAOUserMongo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String url = request.getRequestURI();

        if(!url.startsWith("/api/create-token")){

            String token = request.getHeader("Authorization");

            ServiceResponse<Boolean> serviceResponse = authService.checkToken(token);

            if (!serviceResponse.code.equals(CD_SUCCESS_DEFAULT)) {

                response.setContentType("application/json");

                objectMapper.writeValue(response.getWriter(), serviceResponse);

                return;
            }

            Authentication securityContext = SecurityContextHolder.getContext().getAuthentication();
//            if(securityContext == null || (securityContext != null && securityContext.getPrincipal().equals("anonymousUser"))) {
            if(securityContext.getPrincipal().equals("anonymousUser")) {

                token = token.substring(7);

                String email = authService.getEmailFromToken(token);

                User loggedUser = dAOUserMongo.selectUserByEmail(email);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loggedUser.email, loggedUser.password, loggedUser.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        //passer
        filterChain.doFilter(request, response);
    }
}
