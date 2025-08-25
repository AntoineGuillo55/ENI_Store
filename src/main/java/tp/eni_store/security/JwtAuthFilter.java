package tp.eni_store.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tp.eni_store.service.ServiceResponse;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    tp.eni_store.service.AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String url = request.getRequestURI();

        if(!url.startsWith("/api/create-token")){

            String token = request.getHeader("Authorization");

            ServiceResponse<Boolean> serviceResponse = authService.checkToken(token);

            if (!serviceResponse.code.equals("202")) {

                response.setContentType("application/json");

                objectMapper.writeValue(response.getWriter(), serviceResponse);

                return;
            }
        }

        //passer
        filterChain.doFilter(request, response);
    }
}
