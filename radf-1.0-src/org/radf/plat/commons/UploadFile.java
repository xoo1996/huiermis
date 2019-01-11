package org.radf.plat.commons;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class UploadFile {

	public static boolean uploadFile(String url, int port, String username,
			String password, String path, String filename, InputStream input,
			String cid, String folno) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
		/*	reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}*/
			//切换到3DFiles目录
			ftp.changeWorkingDirectory(path);
			//判断客户id文件夹是否存在
			if (!ftp.changeWorkingDirectory(cid)) {
				//如果不存在就创建客户id文件夹
				ftp.makeDirectory(cid);
				//切换到客户id文件夹
				ftp.changeWorkingDirectory(cid);
				//判断订单文件夹是否存在,不存在则创建
				if (!ftp.changeWorkingDirectory(folno)) {
					ftp.makeDirectory(folno);
					ftp.changeWorkingDirectory(folno);
				}
			}else{
				//已经进入客户id目录
				//判断订单目录是否存在
				if (!ftp.changeWorkingDirectory(folno)) {
					ftp.makeDirectory(folno);
					ftp.changeWorkingDirectory(folno);
				}
			}
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.storeFile(filename, input);

			input.close();
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}
}
