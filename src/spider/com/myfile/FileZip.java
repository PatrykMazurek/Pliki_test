package spider.com.myfile;

import java.io.*;
import java.nio.file.Path;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileZip {

    public static void sendToZIPFile(Path location, String name, File[] files){

        try {
            FileOutputStream fOut = new FileOutputStream(location.resolve(name).toString());
            ZipOutputStream zipOut = new ZipOutputStream(fOut);
            zipOut.setLevel(Deflater.NO_COMPRESSION);
            zipOut.setMethod(ZipOutputStream.DEFLATED);

            for (File f : files){
                if (f.isFile()){
                    System.out.println("Zapisuje do archiwum " + f.getName());
                    ZipEntry ze = new ZipEntry(f.getName());
                    zipOut.putNextEntry(ze);

                    FileInputStream fin = new FileInputStream(f);
                    byte[] buffer = new byte[2048];
                    int length = 0;
                    while ( (length = fin.read( buffer )) >= 0 ){
                        zipOut.write(buffer, 0, length);
                    }
                    zipOut.closeEntry();
                }
            }
            zipOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void readFromZIPFile(Path destLocation, String zipFile ){
        try {
            FileInputStream fInput = new FileInputStream(zipFile);
            ZipInputStream zInput = new ZipInputStream( fInput );
            ZipEntry ze;
            while ( (ze = zInput.getNextEntry()) != null){
                System.out.println("Nazwa " + ze.getName() + " wielkość " + ze.getSize() );
                FileOutputStream fOutput =
                        new FileOutputStream(destLocation.resolve(ze.getName()).toString() );
                byte[] buffer = new byte[2048];
                int length = 0;
                while ( (length = zInput.read( buffer) ) >= 0 ){
                    fOutput.write(buffer, 0, length);
                }
                fOutput.close();
                zInput.closeEntry();
            }
            zInput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
