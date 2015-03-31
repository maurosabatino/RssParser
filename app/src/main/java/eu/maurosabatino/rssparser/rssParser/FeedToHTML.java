package eu.maurosabatino.rssparser.rssParser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FeedToHTML implements FeedVisitor {

	private String title;
	private StringBuilder htmlString;
	Feed feed;
	FeedMessage feedMessage;
	
	public FeedToHTML(String title){
		this.title=title;
		this.htmlString=new StringBuilder();
	}
	
	public void visit(Feed feed) {
		this.feed = feed;
		
		htmlString.append("<br><br><br>"+feed.getTitle()+"\n");
		htmlString.append(feed.getDescription()+"\n");
		
		htmlString.append("<br><br><br></div><div id=\"Messages\"><ol>");
		for(int i=0; i<feed.getFeedList().size(); i++){
			visit(feed.getMessage(i));
		}

		htmlString.append("</ol></div></body></html>");
	}

	@Override
	public void visit(FeedMessage feedMessage) {
		this.feedMessage = feedMessage;
		
		htmlString.append("<li><h3>"+feedMessage.getTitle()+"</h3>\n");
		
		htmlString.append("<a href=\""+feedMessage.getLink()+"\">"+feedMessage.getLink()+"</a></li><br><br>");
		htmlString.append("\tGuid=[ "+feedMessage.getGuid()+" ];\n\n");
	}
	
	public void htmlFile(String htmlString){
		try{
			FileWriter fstream = new FileWriter("Feed.html");
	        BufferedWriter out = new BufferedWriter(fstream);
			out.write(htmlString);
			out.close();
			}catch (Exception e){
				System.err.println("Error: " + e.getMessage());
			}
	}
	
	public String getHTML(){
		return htmlString.toString();
	}
	

}
