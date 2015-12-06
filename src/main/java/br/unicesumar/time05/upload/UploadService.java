package br.unicesumar.time05.upload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Transactional
@Component
public class UploadService {

    public void uploadWebcam(String baseedString, Long nomeArquivo, String pasta) {
        String stringCerta = baseedString.substring(baseedString.indexOf(",") + 1);
        byte[] decode = Base64.decodeBase64(stringCerta);
        if(!(new File("src/main/webapp/fotos/"+pasta).exists())){
            new File("src/main/webapp/fotos/"+pasta).mkdirs();
        }
        File f = new File("src/main/webapp/fotos/"+pasta+"/"+ nomeArquivo + ".jpg");

        FileOutputStream out;
        try {
            out = new FileOutputStream(f);
            out.write(decode);
            out.close();
            BufferedImage read = ImageIO.read(f);
            BufferedImage bi = read.getSubimage(60, 0, read.getWidth()-2*60, read.getHeight());
            ImageIO.write(bi, "jpg", f);
        } catch (Exception ex) {
            System.out.println(ex);
            throw  new RuntimeException("Falha ao salvar a imagem");
        }
    }
    
    public Map<String, String> getUrlFoto(Long id, String path){
        Map<String, String> result = new HashMap<>();
        if(new File("src/main/webapp/fotos/"+path+"/"+id+".jpg").exists())
            result.put("foto", "fotos/"+path+"/"+id+".jpg");
        else
            result.put("foto", "fotos/default.png");
        
        return result;
    }

}
