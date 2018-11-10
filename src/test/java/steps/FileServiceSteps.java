package steps;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileServiceSteps {

    public static boolean compareFileContents(File a, File b) throws IOException {
        if (a.length() != b.length()) {
            return false;
        }
        FileReader readerA = new FileReader(a);
        FileReader readerB = new FileReader(b);
        int byteA;
        int byteB;
        while ((byteA = readerA.read()) > 0) {
            byteB = readerB.read();
            if (byteA != byteB) {
                return false;
            }
        }
        return true;
    }

}
