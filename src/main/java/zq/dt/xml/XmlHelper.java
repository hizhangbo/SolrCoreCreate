package zq.dt.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;

public class XmlHelper {

    // load xml file
    public static Document load(String filePath) throws DocumentException {
        File file = new File(filePath);
        if(file.exists()){
            SAXReader reader = new SAXReader();
            Document document = reader.read(file);
            return document;
        }
        return null;
    }

    // copy file
    public static void copy(String oldPath, String newPath) throws IOException {
        (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
        File a=new File(oldPath);
        String[] file=a.list();
        File temp=null;
        for (int i = 0; i < file.length; i++) {
            if(oldPath.endsWith(File.separator)){
                temp=new File(oldPath+file[i]);
            }
            else{
                temp=new File(oldPath+File.separator+file[i]);
            }

            if(temp.isFile()){
                FileInputStream input = new FileInputStream(temp);
                FileOutputStream output = new FileOutputStream(newPath + "/" +
                        (temp.getName()).toString());
                byte[] b = new byte[1024 * 5];
                int len;
                while ( (len = input.read(b)) != -1) {
                    output.write(b, 0, len);
                }
                output.flush();
                output.close();
                input.close();
            }
            if(temp.isDirectory()){//如果是子文件夹
                copy(oldPath+"/"+file[i],newPath+"/"+file[i]);
            }
        }
    }

    // dom4j write file
    public static void createXml(Document document, String coreName, String fileName) throws IOException {
        File xmlFile = new File("src/main/resources/solr/"+coreName+"/conf/"+fileName);
        XMLWriter writer = null;
        try {
            if (xmlFile.exists())
                xmlFile.delete();
            writer = new XMLWriter(new FileOutputStream(xmlFile), OutputFormat.createPrettyPrint());
            writer.write(document);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
