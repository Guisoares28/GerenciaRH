package com.gerenciarh.gerenciarh.Security;
import com.gerenciarh.gerenciarh.Exceptions.NotFoundException;
import com.gerenciarh.gerenciarh.Models.User;
import com.gerenciarh.gerenciarh.Repositories.UserRepository;
import com.gerenciarh.gerenciarh.Services.JwtService;
import com.gerenciarh.gerenciarh.Utils.AuthenticationUserHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FilterSecurity extends OncePerRequestFilter {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    public FilterSecurity(JwtService jwtService, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try{
            if(request.getRequestURI().equals("/login") || request.getRequestURI().equals("/enterprise/create")){
                filterChain.doFilter(request,response);
                return;
            }

            String header = request.getHeader("Authorization");
            if(header == null || !header.startsWith("Bearer ")){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            String token = header.substring(7);
            if(!jwtService.validarToken(token)){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }else{
                String nickname = jwtService.pegarNicknameDoToken(token);
                User user = userRepository.findByNickname(nickname)
                        .orElseThrow(() -> new NotFoundException("Usuário ou senha invalidos"));
                AuthenticationUserHolder.set(user);
                filterChain.doFilter(request,response);
            }
        }finally {
            AuthenticationUserHolder.clear();
        }

    }
}
