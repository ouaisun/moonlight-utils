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

import sun.misc.BASE64Decoder;

/**
 * IDecryptTool接口的实现类
 * @author liubin
 *
 */
public class DecryptTool {
	
	/**
	 * 解密方法
	 * @param key_file 密钥文件全路径  如:e:/key.ent
	 * @param src_file 加密文件全路径  如:e:/xxx.mii
	 * @param dst_file 解密文件全路径  如:e:/xxx.xml
	 */
	public void decrypt(String key_file, String src_file, String dst_file) throws Exception{
		System.out.println("开始进行解密");
		
		FileInputStream fis =  new FileInputStream(src_file);
		InputStream in = new BufferedInputStream(fis);
		
		FileOutputStream fos= new FileOutputStream(dst_file);
		OutputStream out = new BufferedOutputStream(fos);
		
		FileInputStream key_fis = new FileInputStream(key_file);
		ObjectInputStream ois = new ObjectInputStream(key_fis);
		
		try{
			//从密钥文件读到key
			Key key = (Key) ois.readObject();
			int key_length = key.getEncoded().length;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			
			while ((bytesRead = in.read(buffer)) != -1) {
				bout.write(buffer,0, bytesRead);
				
				//每次对1兆数据进行解密
				if(bout.size()==1024*1024){
					doDecrypt(bout.toByteArray(),key,key_length,out);
					bout.reset();
				}
				
			}
			
			doDecrypt(bout.toByteArray(),key,key_length,out);
		}
		catch(Exception e){
			throw new Exception("DecryptTool:decrypt:"+e.getMessage());
		}
		finally{
			fis.close();
			fos.close();
			in.close();
			out.close();
			key_fis.close();
			ois.close();
		}
		
		System.out.println("完成数据解密");
	}
	
	/**
	 * 解密方法
	 * @param key_file 密钥文件全路径  如:e:/key.ent
	 * @param src_str 加密字符串
	 */
	public String decrypt(String key_file, String src_str) throws Exception{
		System.out.println("开始进行解密");
		if(src_str == null || src_str.trim().length()<1)
			throw new Exception("EncryptTool:encrypt:要解密的字符串不能为空");
		
		String dst_str = null;
		FileInputStream key_fis = new FileInputStream(key_file);
		ObjectInputStream ois = new ObjectInputStream(key_fis);
		
		try{
			//从密钥文件读到key
			Key key = (Key) ois.readObject();
			dst_str = doDecrypt((new BASE64Decoder()).decodeBuffer(src_str), key);
		}
		catch(Exception e){
			throw new Exception("DecryptTool:decrypt:"+e.getMessage());
		}
		finally{
			key_fis.close();
			ois.close();
		}
		
		System.out.println("完成数据解密");
		return dst_str;
	}
	
	
	private String doDecrypt(byte[] cipherText, Key key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		String str = null;
		try{
			
			byte[] buffer = cipher.doFinal(cipherText);
			str = new String(buffer,"UTF8");
		}
		catch(Exception e){
			throw new Exception("DecryptTool:decrypt:"+e.getMessage());
		}
		
		return str;
	}
	
	private void doDecrypt(byte[] cipherText, Key key,int key_length, OutputStream out) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		int blockSize = cipher.getBlockSize()+key_length;// 块大小
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
		int i = 0;
		while (cipherText.length - i * blockSize > 0) {
			if (cipherText.length - i * blockSize > blockSize) {
				byte[] buffer = cipher.doFinal(cipherText, i * blockSize,
						blockSize);
				bout.write(buffer);
			} 
			else {
				byte[] buffer = cipher.doFinal(cipherText, i * blockSize,
						cipherText.length - i * blockSize);
				bout.write(buffer);
			}
			i++;
		}
		bout.writeTo(out);
		out.flush();
		bout.close();
	}

}
