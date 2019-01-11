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
			ftp.connect(url, port);// ����FTP������
			// �������Ĭ�϶˿ڣ�����ʹ��ftp.connect(url)�ķ�ʽֱ������FTP������
			ftp.login(username, password);// ��¼
		/*	reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}*/
			//�л���3DFilesĿ¼
			ftp.changeWorkingDirectory(path);
			//�жϿͻ�id�ļ����Ƿ����
			if (!ftp.changeWorkingDirectory(cid)) {
				//��������ھʹ����ͻ�id�ļ���
				ftp.makeDirectory(cid);
				//�л����ͻ�id�ļ���
				ftp.changeWorkingDirectory(cid);
				//�ж϶����ļ����Ƿ����,�������򴴽�
				if (!ftp.changeWorkingDirectory(folno)) {
					ftp.makeDirectory(folno);
					ftp.changeWorkingDirectory(folno);
				}
			}else{
				//�Ѿ�����ͻ�idĿ¼
				//�ж϶���Ŀ¼�Ƿ����
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
