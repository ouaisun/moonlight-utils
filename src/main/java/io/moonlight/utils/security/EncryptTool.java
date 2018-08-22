package io.moonlight.utils.security;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.security.Key;

import javax.crypto.Cipher;

import sun.misc.BASE64Encoder;

/**
 * 加密
 * @author liubin
 *
 */
public class EncryptTool {
	
	/**
	 * 加密方法
	 * @param key_file 密钥文件             如：e:/key.ent
	 * @param src_file 需要加密的文件       如：e:/xxx.xml
	 * @param dst_file 加密后的文件         如：e:/xxx.dat
	 */
	public void encrypt(String key_file, String src_file, String dst_file) throws Exception{
		System.out.println("开始进行加密");
		
		FileInputStream fis =  new FileInputStream(src_file);
		InputStream in = new BufferedInputStream(fis);
		
		FileOutputStream fos= new FileOutputStream(dst_file);
		OutputStream out = new BufferedOutputStream(fos);
		
		FileInputStream key_fis = new FileInputStream(key_file);
		ObjectInputStream ois = new ObjectInputStream(key_fis);
		try{
			//从密钥文件读到key
			Key key = (Key) ois.readObject();
			
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			int bytesRead = 0;
			//循环加密,每次加密的数据大小为1M
			while ((bytesRead = in.read(buffer)) != -1) {
				bout.write(buffer,0, bytesRead);
				//加密并写到目标文件
				if(bout.size()==1024*1024){
					doEncrypt(bout.toByteArray(),key,out);
					bout.reset();
				}
				
				buffer = new byte[1024];
			}
			
			doEncrypt(bout.toByteArray(),key,out);
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("EncryptTool:encrypt:"+e.getMessage());
		}
		finally{
			fis.close();
			fos.close();
			in.close();
			out.close();
			key_fis.close();
			ois.close();
		}
		
		System.out.println("结束数据加密");
	}
	
	/**
	 * 加密方法
	 * @param key_file 密钥文件             如：e:/key.ent
	 * @param src_str 需要加密字符串
	 */
	public String encrypt(String key_file, String src_str) throws Exception{
		System.out.println("开始进行加密");
		if(src_str == null || src_str.trim().length()<1)
			throw new Exception("EncryptTool:encrypt:要加密的字符串不能为空");
		
		byte[] buffer = src_str.getBytes("UTF8");
		byte[] result = null;
		FileInputStream key_fis = new FileInputStream(key_file);
		ObjectInputStream ois = new ObjectInputStream(key_fis);
		try{
			//从密钥文件读到key
			Key key = (Key) ois.readObject();
			result = doEncrypt(buffer,key);
			
		}catch(Exception e){
			throw new Exception("EncryptTool:encrypt:"+e.getMessage());
		}
		finally{
			key_fis.close();
			ois.close();
		}
		
		System.out.println("结束数据加密...");
		BASE64Encoder encoder = new BASE64Encoder();
//		return new String(org.bouncycastle.util.encoders.Base64.encode(result),"UTF-8");
		return new String(encoder.encode(result).getBytes(),"UTF-8");
	}
	
	/**
	 * 加密数据
	 * @param plainText 需要加密的数据
	 * @param key    密钥
	 * @throws Exception
	 */
	private  byte[] doEncrypt(byte[] plainText, Key key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] buffer = null;
		try{
			buffer = cipher.doFinal(plainText);
		}catch(Exception e){
			throw new Exception("EncryptTool:encrypt:"+e.getMessage());
		}
		return buffer;
	}
	
	/**
	 * 加密数据
	 * @param plainText 需要加密的数据
	 * @param key    密钥
	 * @param out    加密后的目标输出文件
	 * @throws Exception
	 */
	private void doEncrypt(byte[] plainText, Key key,OutputStream out) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		int blockSize = cipher.getBlockSize();// 块大小
		ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
		int i = 0;
		while (plainText.length - i * blockSize > 0) {
			if (plainText.length - i * blockSize > blockSize) {
				byte[] buffer = cipher.doFinal(plainText, i * blockSize,
						blockSize);
				bout.write(buffer);
			} else {
				byte[] buffer = cipher.doFinal(plainText, i * blockSize,
						plainText.length - i * blockSize);
				bout.write(buffer);
			}
			i++;
		}
		bout.writeTo(out);
		out.flush();
		bout.close();
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EncryptTool et = new EncryptTool();
		DecryptTool dt = new DecryptTool();
		String path1 = "D:/work/key/key.ent";
		
		try{
			String strInput = "TBCQ"; //input.nextLine();
			String ret = et.encrypt(path1,strInput); //bEHSfZLTugj3V/ho6qa5bw==
			System.out.println(ret);
			ret = dt.decrypt(path1,"hHb2plrjlfIR9eWVZrJSSA==");
			System.out.println(ret);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static void main1(String[] args) {
		EncryptTool et = new EncryptTool();
		DecryptTool dt = new DecryptTool();
		//Scanner input = new Scanner(System.in);
		String path1 = "D:/work/key/key.ent";
		
		try{
			/*String strInput = input.nextLine();
			String ret = et.encrypt(path1,strInput); //bEHSfZLTugj3V/ho6qa5bw==
			System.out.println(ret);
			ret = dt.decrypt(path1,input.nextLine());
			System.out.println(ret);*/
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
