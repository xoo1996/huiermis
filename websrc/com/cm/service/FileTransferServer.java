package com.cm.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMText;
import org.apache.axis2.AxisFault;

public class FileTransferServer {  
    public static final String TMP_PATH="C://temp//";  
      
    public OMElement upload(OMElement element) throws Exception {   
          
        OMElement _fileContent = null;//文件内容   
        OMElement _fileName = null;//文件名   
        OMElement _fileType = null;//文件类型   
        System.out.println("调用上传..");  
        System.out.println("The element for upload: " + element);   
        for (Iterator _iterator = element.getChildElements(); _iterator   
                      .hasNext();) {   
              OMElement _ele = (OMElement) _iterator.next();   
              if (_ele.getLocalName().equalsIgnoreCase("fileContent")) {   
                      _fileContent = _ele;   
              }   
              if (_ele.getLocalName().equalsIgnoreCase("fileName")) {   
                      _fileName = _ele;   
              }   
              if (_ele.getLocalName().equalsIgnoreCase("fileType")) {   
                      _fileType = _ele;   
              }   
        }   
  
        if (_fileContent == null || _fileType == null) {   
              throw new AxisFault("Either Image or FileName is null");   
        }   
  
        OMText binaryNode = (OMText) _fileContent.getFirstOMChild();   
        String fileName = _fileName.getText();   
        String fileType = _fileType.getText();   
        String storeDir = TMP_PATH + "//" + "velmaUpload";   
        File dir = new File(storeDir);   
        if (!dir.exists()) {   
              dir.mkdir();   
        }   
        String filePath = storeDir + "/" + fileName + "." + fileType;   
        File uploadFile = new File(filePath);   
        if (uploadFile.exists()) {   
              filePath = storeDir + "/" + fileName + "(1)" + "." + fileType;   
              uploadFile = new File(filePath);   
        }   
  
        // Extracting the data and saving   
        DataHandler actualDH;   
        actualDH = (DataHandler) binaryNode.getDataHandler();   
  
        FileOutputStream imageOutStream = new FileOutputStream(uploadFile);   
         
        actualDH.writeTo(imageOutStream);   
         
        OMFactory fac = OMAbstractFactory.getOMFactory();   
        OMNamespace ns = fac.createOMNamespace("http://example.org/filedata",   
                      "fd");   
        OMElement ele = fac.createOMElement("response", ns);   
        ele.setText("true");   
        return ele;   
}   
  
    public OMElement download(OMElement element) throws Exception {   
        System.out.println("The element for download: " + element);   
          
        OMElement _userName = null;   
        OMElement _fileName = null;   
        OMElement _fileType = null;   
        for (Iterator _iterator = element.getChildElements(); _iterator   
                      .hasNext();) {   
              OMElement _ele = (OMElement) _iterator.next();   
              if (_ele.getLocalName().equalsIgnoreCase("userName")) {   
                      _userName = _ele;   
              }   
              if (_ele.getLocalName().equalsIgnoreCase("fileName")) {   
                      _fileName = _ele;   
              }   
              if (_ele.getLocalName().equalsIgnoreCase("fileType")) {   
                      _fileType = _ele;   
              }   
        }   
        String userName = _userName.getText();   
        String fileName = _fileName.getText();   
        String fileType = _fileType.getText();   
        String filePath = TMP_PATH + "/" + userName + "/" + fileName + "."   
                      + fileType;   
          
        System.out.println("The filePath for download: " + filePath);   
          
        FileDataSource dataSource = new FileDataSource(filePath);   
        DataHandler expectedDH = new DataHandler(dataSource);   
        OMFactory fac = OMAbstractFactory.getOMFactory();   
        OMNamespace ns = fac.createOMNamespace("http://example.org/filedata",   
                      "fd");   
        OMText textData = fac.createOMText(expectedDH, true);   
        OMElement ele = fac.createOMElement("response", ns);   
        ele.addChild(textData);   
        return ele;   
  
}   
  
  
}  
