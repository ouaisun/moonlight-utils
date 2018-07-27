package io.moonlight.utils.security;


/**
 * @ClassName: Test  
 * @Description: TODO 
 * @author: 贺勇 
 * @date: 2013-4-1 下午12:32:04   
 */
public class Test {

	/**  
	 * @Title: main  
	 * @Description: TODO 
	 * @param @param args 
	 * @return void 
	 * @throws  
	 */
	public static void main(String[] args) {
		EncryptTool et = new EncryptTool();
		DecryptTool dt = new DecryptTool();
		String path1 = Test.class.getClassLoader().getResource("key.ent").getPath();
        System.out.println(path1);
		try{
			String strInput = "20180718100000105959"; //input.nextLine();
			String ret = et.encrypt(path1,strInput); //bEHSfZLTugj3V/ho6qa5bw==
			System.out.println(ret);
			ret = dt.decrypt(path1,ret);
			System.out.println(ret);
			
			
			
			ret = dt.decrypt(path1,"NuuuXcO2v2hJldEBqRuDmZn+1UKw2zViVNXuModFsIXvY8P1x95dIrHihWBMw9zNFW7usqbVVW+zZpBQAro8KXraXpD51F+uXGOkpKQgLrRUSXC0i67/1Ri/mggHvPJ0vYj3Sy2j+79bO/UaLTrGk8fGjUS8RieiyMJLJ6K4/4G3As1v987uFzTctn8DMumbbKaIQ2QJcYkaYskNLFgtdfRgb2uMELg2ktlWuSRZLhDieScd3O6weRNkmk2p84eJGw5WfQgoeckMxcq6pOGd+Zn/1fG57IlmVtrHZSmflMHPNBnBHtiks/eSF70ZuVVSqp9kqGdD56hHavBzTaNxwuCZcrjr4jhfoQF4xcljM0TCm/dhCF38r+dnrykW6wP/a9oi3N8gCXyivCGqgS/Bcw6Hh9ZUFoJVoC4wLr64l2rxTiC/8U1uOR+lYHxRxsXcFdO7GjSk/+FS3qksKSoHFAQOuErBsyAnQmCZsL59tNyoNIuKiSYe8ujyBHQ1JGL1s1gSVHScvoRPbc2Fa+jMtLG8yDSY9C1hbK3fIYI6G9s=");
			System.out.println(ret);
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
