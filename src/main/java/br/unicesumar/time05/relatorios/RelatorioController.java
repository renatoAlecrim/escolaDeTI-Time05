package br.unicesumar.time05.relatorios;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/relatorio")
public class RelatorioController {
    
    @Autowired
    private DataSource dataSource;

    @RequestMapping(value = "/rel", method = RequestMethod.GET)
    public void verifcarDescricao(HttpServletResponse response) throws JRException, SQLException, IOException {
        // compilacao do JRXML
        URL reportResource = getClass().getClassLoader().getResource("./relatorios/teste01.jrxml");
        JasperReport report = JasperCompileManager.compileReport(reportResource.getFile());  
        
      
        JasperPrint print = JasperFillManager.fillReport(report, new HashMap<String, Object>(), dataSource.getConnection());  
        
        File pastaUsuario = new File(System.getProperty("user.dir"));
        File pdf = new File(pastaUsuario, String.format("relatorioPessoas_%s.pdf", System.nanoTime()));
//        System.out.println("Imprimindo arquivo " + pdf);
        
        JasperExportManager.exportReportToPdfFile(print, pdf.getAbsolutePath());
        
        response.setHeader("Content-Type", "application/pdf");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(IOUtils.toByteArray(new FileInputStream(pdf)));
        outputStream.flush();
        outputStream.close();
        
//        System.out.println("Relatório gerado.");

// ******************************************************************
        //Javascript no retorno para o front PDF
//          function gerarRelatorioSuccess(response) {
//            printReport(response.data);
//          } 
//
//          function printReport(data) {
//            var file = new Blob([ data ], {
//              type : 'application/pdf'
//            });
//
//            var url = URL.createObjectURL(file);
//            window.open(url).print();
//         }
// ******************************************************************
//          function gerarRelatorioSuccess(response) {
//    printReport(response.data);
//  } 
//
//  function printReport(data) {
//    var file = new Blob([ data ], {
//      type : 'text/html'
//    });
//
//    var url = URL.createObjectURL(file);
//    window.open(url).print();
// }
 // ******************************************************************      
    }

    @RequestMapping(value = "/rel2/{where}", method = RequestMethod.GET)
    public void verifcarDescricao2(HttpServletResponse response,@PathVariable String where) throws JRException, SQLException, IOException {
//        System.out.println("parametro " + where);
        
        // compilacao do JRXML
        URL reportResource = getClass().getClassLoader().getResource("./relatorios/teste01.jrxml");
        JasperReport report = JasperCompileManager.compileReport(reportResource.getFile());  
      
        //JasperPrint print = JasperFillManager.fillReport(report, new HashMap<String, Object>(), dataSource.getConnection());  
        JasperPrint print = JasperFillManager.fillReport(report, new HashMap<String, Object>(), dataSource.getConnection());  
        
        File pastaUsuario = new File(System.getProperty("user.dir"));
        File pdf = new File(pastaUsuario, String.format("relatorioPessoas_%s.pdf", System.nanoTime()));
//        System.out.println("Imprimindo arquivo " + pdf);
        JasperExportManager.exportReportToPdfFile(print, pdf.getAbsolutePath());
        
        response.setHeader("Content-Type", "application/pdf");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(IOUtils.toByteArray(new FileInputStream(pdf)));
        outputStream.flush();
        outputStream.close();
        
//        System.out.println("Relatório gerado.");
    }
    
    @RequestMapping(value = "/rel3", method = RequestMethod.POST)
    public ResponseEntity<byte[]> getPDF(@RequestBody String json) throws JRException, SQLException {
        // compilacao do JRXML
        URL reportResource = getClass().getClassLoader().getResource("./relatorios/teste01.jrxml");
        JasperReport report = JasperCompileManager.compileReport(reportResource.getFile());  
      
        JasperPrint print = JasperFillManager.fillReport(report, new HashMap<String, Object>(), dataSource.getConnection());  
        
//        File pastaUsuario = new File(System.getProperty("user.dir"));
//        File pdf = new File(pastaUsuario, String.format("relatorioPessoas_%s.pdf", System.nanoTime()));
//        System.out.println("Imprimindo arquivo " + pdf);
//        
//        JasperExportManager.exportReportToPdfFile(print, pdf.getAbsolutePath());
//        
//        response.setHeader("Content-Type", "application/pdf");
//        ServletOutputStream outputStream = response.getOutputStream();
//        outputStream.write(IOUtils.toByteArray(new FileInputStream(pdf)));
//        outputStream.flush();
//        outputStream.close();
//        
//        System.out.println("Relatório gerado.");
        
    byte[] contents = null;
            
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.parseMediaType("application/pdf"));
    String filename = "output.pdf";
    headers.setContentDispositionFormData(filename, filename);
    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
    ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
    return response;
    }

    
    
    
    
    
    @RequestMapping(method = RequestMethod.POST)
    public void generateReport(HttpServletResponse response) throws Exception {

        byte[] data = null; //read PDF as byte stream

        streamReport(response, data, "my_report.pdf");
    }

    protected void streamReport(HttpServletResponse response, byte[] data, String name)
            throws IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + name);
        response.setContentLength(data.length);

        response.getOutputStream().write(data);
        response.getOutputStream().flush();
    }
}
