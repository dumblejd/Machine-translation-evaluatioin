package trytry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

	public static void main(String[] args) {
	    String str = "<p class='xxxx'> Content\n\rÄÚÈİ\t\n\n</p>";
	    Matcher m = Pattern.compile("<p.*?>([\\s\\S]*)</p>").matcher(str);
	    while(m.find()){
	        System.out.println(m.group(1));
	    }
	}
}
