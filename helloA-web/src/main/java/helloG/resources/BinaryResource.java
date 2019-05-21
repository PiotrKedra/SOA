package helloG.resources;

import org.apache.commons.io.IOUtils;

import javax.inject.Singleton;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

@Singleton
public class BinaryResource {

    public byte[] convertToBinary(String filePath) throws IOException {
        System.out.println("filexd: " + filePath);
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream inputStream = new BufferedInputStream(fis);
        byte[] fileBytes = new byte[(int) file.length()];
        inputStream.read(fileBytes);
        inputStream.close();

        System.out.println(fileBytes);

        return fileBytes;
    }
}
