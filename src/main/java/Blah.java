
public class Blah {
	public static void main(String[] args){
        for(int i = 32; i <= 255; i++)
        {
             System.out.format("%1$-5d", i);
             System.out.format("%1$-2c", (char) i);
             System.out.println();
        }
	}

}
