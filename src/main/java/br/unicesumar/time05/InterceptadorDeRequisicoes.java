package br.unicesumar.time05;

import br.unicesumar.time05.usuario.UsuarioService;
import br.unicesumar.time05.usuario.sessaousuario.SessaoUsuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class InterceptadorDeRequisicoes extends HandlerInterceptorAdapter{
    
    @Autowired
    SessaoUsuario sessao;

    @Autowired
    UsuarioService usuarioService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object controller){
        
        String uri = request.getRequestURI();
        String url = request.getRequestURL().toString();
        
        CharSequence cadusuario = "/usuario";
        CharSequence login = "/login/";
        
        if (url.contains(login) || url.endsWith("/usuario") || uri.equals("/")){
            return true;
        } else if (url.contains(cadusuario) && usuarioService.listarSemPaginacao().isEmpty()) {
            return true;
        } else if (sessao.getUsuario() != null && request.getSession().getAttribute("usuarioLogado") == sessao.getUsuario()) {            
            return true;
        }
        
        System.out.println("Req...." + url);
        return true;
    }
}
