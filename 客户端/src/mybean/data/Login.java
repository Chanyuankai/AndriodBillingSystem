package mybean.data;
 
import java.util.LinkedList;
//�洢�û���¼����Ϣ
public class Login {
   private String logname="";
   private String backNews="δ��¼";
   private LinkedList<String> car;//�û��Ĺ��ﳵ
   public Login(){
	   car=new LinkedList<String>();
   }
   public String getLogname() {
		return logname;
   }
   public void setLogname(String logname) {
		this.logname = logname;
   }
   public String getBackNews() {
		return backNews;
   }
   public void setBackNews(String backNews) {
		this.backNews = backNews;
   }
   public LinkedList<String> getCar() {
		return car;
   } 
}