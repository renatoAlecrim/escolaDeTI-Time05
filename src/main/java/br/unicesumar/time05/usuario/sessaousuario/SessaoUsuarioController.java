package br.unicesumar.time05.usuario.sessaousuario;

import br.unicesumar.time05.email.EnviaEmail;
import br.unicesumar.time05.itemacesso.ItemAcessoUsuarioInMemory;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@RestController
@RequestMapping(value = "/login")
public class SessaoUsuarioController {
    
    @Autowired
    SessaoUsuarioService service;
    
    @RequestMapping(value = "/efetuarlogin", method = RequestMethod.POST)
    public boolean efetuarLogin(@RequestBody DadosLogin aDadosLogin, HttpSession session){
        return service.efetuarLogin(aDadosLogin, session);
    }
    
    @RequestMapping(value = "/efetuarlogout", method = RequestMethod.POST)
    public void efetuarLogout(HttpSession session){
        service.efetuarLogout(session);
    }
    
    @RequestMapping(value = "/usuariologado", method = RequestMethod.GET)
    public Map<String, Object> getUsuarioLogado(){
        return service.getUsuarioLogado();
    }
    
    @RequestMapping(value = "/statusLogin/{aLogin:.+}", method = RequestMethod.GET)
    public List<Map<String, Object>> getStatusPorLogin(@PathVariable String aLogin){
        return service.getStatusPorLogin(aLogin);
    }
    
    @RequestMapping(value = "/usuariologado/itensdeacesso", method = RequestMethod.GET)
    public ItemAcessoUsuarioInMemory getItensDeAcessoDoUsuarioLogado(){
        return service.getItensDeAcessoUsuarioLogado();
    }
    
//    aEnviaEmail{
//    "endereco":"andreyhideki@gmail.com"
//    "titulo":"teste com parametro"
//    "conteudo":"teste com parametro"
//}
    @RequestMapping(value = "/enviaemail",  method = RequestMethod.POST)
    public void enviaemail(@RequestBody EnviaEmail aEnviaEmail){
        final String username = "contatoassindi@gmail.com";
        final String password = "assindi123";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                }
          });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from-email@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(aEnviaEmail.getEndereco()));
            message.setSubject(aEnviaEmail.getTitulo());
            message.setText(aEnviaEmail.getConteudo());

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}