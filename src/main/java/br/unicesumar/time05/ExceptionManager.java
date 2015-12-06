//package br.unicesumar.time05;

//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//@ControllerAdvice
//public class ExceptionManager {
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ResponseBody
//    public String tratarExceção(Exception e) {
//        //aqui será feito o controle e tratamento das exceptions
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>");
//        return e.getLocalizedMessage();
//    }
//    
//}
