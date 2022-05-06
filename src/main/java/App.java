import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class App {

    private boolean createDirs(String dir){
        boolean ret = false;
        File theDir = new File(dir);
        if (!theDir.exists()){
            ret = theDir.mkdirs();
        }
        return ret;
    }

    public void split(File file, boolean renameOnly) throws IOException {
        PDDocument document = PDDocument.load(file);
        Splitter splitter = new Splitter();
        List<PDDocument> pages = splitter.split(document);
        String root = System.getProperty("user.dir");
        String one = root+"\\invoice_commercial\\";
        String two = root+"\\invoice_stelia\\";
        String other = root+"\\invoice_rename\\";

        createDirs(one);
        createDirs(two);
        createDirs(other);

        int n=1;
        for (PDDocument doc : pages) {
            PDFTextStripper stripper = new PDFTextStripper();
            String facNum = stripper.getText(doc).split("\n")[8].split(" ")[0];
            String orderNum = stripper.getText(doc).split("\n")[13];

            String filename = facNum.concat(".pdf");

            if(!renameOnly){
                if (orderNum.startsWith("DL0") || orderNum.startsWith("CA") || orderNum.startsWith("630") || orderNum.startsWith("930")){
                    File f = new File(one,filename);
                    System.out.println(f.getAbsolutePath());
                    doc.save(f);
                }else {
                    File ft = new File(two,filename);
                    System.out.println(ft.getAbsolutePath());
                    doc.save(ft);
                }
            }else{
                File fo = new File(other, filename);
                System.out.println(fo.getAbsolutePath());
                doc.save(fo);
            }
            n++;
            doc.close();
            n+=1;
        }
        document.close();
    }
}
