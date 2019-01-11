package com.cm.test;

import java.io.File;
import org.apache.tools.zip.ZipOutputStream; //�������ant.jar�Ҫ���ٷ�������
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;

public class CompressBook {
    public CompressBook() {}

    /*
    * inputFileName ����һ���ļ���
    * zipFileName ���һ��ѹ���ļ���      
    */
    public void zip(String inputFileName) throws Exception {
        String zipFileName = inputFileName+".zip"; //������ļ�����
        System.out.println(zipFileName);
        zip(zipFileName, new File(inputFileName));
    }

    private void zip(String zipFileName, File inputFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        zip(out, inputFile, "");
        System.out.println("zip done");
        out.close();
    }

    private void zip(ZipOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory()) {
           File[] fl = f.listFiles();
           out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
           base = base.length() == 0 ? "" : base + "/";
           for (int i = 0; i < fl.length; i++) {
           zip(out, fl[i], base + fl[i].getName());
         }
        }else {
           out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
           FileInputStream in = new FileInputStream(f);
           int b;
           System.out.println(base);
           while ( (b = in.read()) != -1) {
            out.write(b);
         }
         in.close();
       }
    }

    public static void main(String [] temp){
        CompressBook book = new CompressBook();
        try {
           book.zip("D:\\debug2015105\\201505");//��Ҫѹ�����ļ���
        }catch (Exception ex) {
           ex.printStackTrace();
       }
    }
}
