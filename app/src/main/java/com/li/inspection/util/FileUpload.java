package com.li.inspection.util;

import com.li.inspection.constant.Constants;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Socket;


public class FileUpload {
	private Socket	socket;
	private String filePath;
	private String alarmID;
	private MyFileListener listener;
	
	/**
	 * 文件上传工具
	 * @param filePath 文件路径
	 * @param alarmID 警情ID
	 * @param myFileListener 监听接口
	 */
	public FileUpload(String filePath, String alarmID, MyFileListener myFileListener) {
		this.filePath = filePath;
		this.alarmID = alarmID;
		this.listener = myFileListener;
	}
	

	public void transStart() {
		try {
			File file = new File(this.filePath);
			if(!file.exists()){
				return;
			}
			this.socket = new Socket(Constants.UPLOADSERVER, Constants.UPLOADSERVERPORT);
			DataOutputStream ps = new DataOutputStream(this.socket.getOutputStream());
			ps.writeUTF(file.getName()); // 文件名
			ps.flush();
//			ps.writeUTF(this.alarmID); // 警情ID
//			ps.flush();
			DataInputStream pi = new DataInputStream(this.socket.getInputStream());
			String czbz = pi.readUTF();
			System.out.println("标志长度" + czbz);
			if (czbz.equals("noexist")) {
				byte[] buf = new byte[1024];
				DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(this.filePath)));
				int num = fis.read(buf);
				int length = 0,mark = 0,pro = 0;
				while (num != (-1)) {// 是否读完所有数据
					length += num;
					ps.write(buf, 0, num);// 将数据写往文件
					num = fis.read(buf);// 继续从网络中读取文件
					pro = (int)((float) length / (float)file.length() * 100);
					if(mark!=pro){
						mark = pro;
						System.out.println("上传进度："+mark+"%");
						this.listener.transferred(mark);
					}
				}
				fis.close();
				ps.flush();
				ps.close();
				pi.close();
			} else {
				long filelength = Long.parseLong(czbz);
				//System.out.println("获得服务器端长度"+filelength);
				RandomAccessFile fileff = new RandomAccessFile(this.filePath, "r");
				// fileff.seek(filelength);
				fileff.skipBytes((int) filelength);
				//System.out.println("指针当前位置" + fileff.getFilePointer());
				if(filelength==fileff.getFilePointer()){
					this.listener.transferred(100);
				}
				
				byte[] by = new byte[1024];
				int amount = fileff.read(by);
				int mark = 0,pro = 0;
				while (amount != -1) {
					filelength += amount;
					ps.write(by, 0, amount);
					amount = fileff.read(by);
					
					pro = (int)((float) filelength / (float)fileff.length() * 100);
					if(mark!=pro){
						mark = pro;
						System.out.println("续传进度："+mark+"%");
						this.listener.transferred(mark);
					}
				}
				fileff.close();
				ps.flush();
				ps.close();
				pi.close();
			}
			if(socket!=null && socket.isConnected()){
				socket.close();
			}
		} catch (IOException e) {
			this.listener.transferred(-1);
			e.printStackTrace();
		}
	}
	
	
	public interface MyFileListener{
		public void transferred(int position);
	}
}
