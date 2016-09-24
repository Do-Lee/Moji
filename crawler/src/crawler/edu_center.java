package crawler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mysql.jdbc.PreparedStatement;

public class edu_center {
	private static Vector<Article> datas;
	
	
	public static void main(String[] args) throws IOException{
		datas = new Vector<Article>();
		
		//크롤링
		Elements text, links, board, image; 
		String url = "http://jw4.mju.ac.kr/user/indexSub.action?framePath=unknownboard&siteId=ctl&dum=dum&boardId=454365&page=";
		Document urlDoc, titleDoc, contentDoc, pictureDoc; 
		
		String picture = "";
		String content = "";
		String totalContent = "";
		String title = "";
		String [] temps;
		Vector<String> arr;
		String htmlArr; 
		for(Integer i = 1; i < 2; i++)  {
			System.out.println("페이지 번호 " + i);
			urlDoc = Jsoup.connect(url.concat(i.toString())).get();
			char charArr2[] = new char[16];
			htmlArr = new String(charArr2);
					
				arr = new Vector<String>();
				for(Element l: links = urlDoc.select(".title a[href]")){
					htmlArr = l.attr("abs:href"); 
					
					if(arr.isEmpty()) {
						arr.add(htmlArr);
					} else {
						if(!checkURL(arr, htmlArr)) {
							arr.add(htmlArr);
						}
					}
				}
				
				for(String arrTemp : arr) {
					titleDoc = Jsoup.connect(arrTemp).get();
					temps = titleDoc.select(".tit").toString().split(" <div class=\"snsInfo\"> ");
					title = temps[0].trim();
					title = stripHTML(title);
					contentDoc = Jsoup.connect(arrTemp).get();
					for(Element m: board = contentDoc.select("#divView p")){
						content = m.toString().replaceAll("<br>", "\n");
						content = stripHTML(content);
						totalContent = totalContent + "\n" + content;
					}
					pictureDoc = Jsoup.connect(arrTemp).get();
					for(Element p: image = pictureDoc.select("#divView img")){
						picture = p.absUrl("src");

					}
					
					datas.add(new Article(arrTemp, title, totalContent, picture));
					title = "";
					totalContent = "";
					picture = "";
				}	
		}
		database();
	}
	
	public static void database() {
		Connection conn = null;
		PreparedStatement preparedStmt = null;

		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://52.78.21.188:3306/Moji?useUnicode=true&characterEncoding=utf8","root","moji");
			
			String query = "insert into edu_center (url, title, m_text, image)"
					+ "values (?,?,?,?)";
			preparedStmt = (PreparedStatement) conn.prepareStatement(query);
			System.out.println(datas.size());
			for(int i = 0; i < datas.size(); i++) {
				preparedStmt.setString(1, datas.get(i).getUrl());
				preparedStmt.setString(2, datas.get(i).getTitle());
				preparedStmt.setString(3, datas.get(i).getContent());
				preparedStmt.setString(4, datas.get(i).getImage());
			    preparedStmt.execute();
			}
			
		    
		}
		catch(ClassNotFoundException cnfe){
			System.out.println("해당 클래스를 찾을 수 없습니다.");
		}
		catch(SQLException se){
			System.out.println(se.getMessage());
		}
		
		finally{
			try{
				preparedStmt.close();
			}
			catch(Exception ignored){					
			}
			
			try{
				conn.close();
			}catch(Exception ignored){
				
			}
		}
	}
	public static boolean checkURL(Vector<String> arr, String string) {
		String [] temps;
		for(int i = 0; i < arr.size(); i++) {
			temps = arr.get(i).split("id=mjukr");
			if(string.contains(temps[0])) {
				return true;
			}
			
		}
		return false;
	}
	public static String stripHTML(String htmlStr) {
		String result;
        Pattern p = Pattern.compile("<(?:.|\\s)*?>");
        Matcher m = p.matcher(htmlStr);
        result =  m.replaceAll("");
        result = result.replaceAll("&nbsp;", "");
        result = result.replaceAll("&amp;", "");
        return result;
    }
}