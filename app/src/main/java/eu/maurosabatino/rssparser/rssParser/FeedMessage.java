package eu.maurosabatino.rssparser.rssParser;

public class FeedMessage {
	private String title;
	private String description;
	private String link;
	private String author;
	private String pubDate;
	private String guid;
	
	
	public FeedMessage() {
		title="";
		description="";
		author="";
		guid="";
		pubDate="";
		link="";
		
		
	}
	
	public FeedMessage(String title,String link,String description,String category,String guid) {
		this.title=title;
		this.link=link;
		this.description=description;
		
		this.guid=guid;
	}
	
	public String getTitle(){
		return title;
	}
	public void setTitle(String tit){
		title=tit;
	}
	
	public String getDescription(){
		return description;
	}

	public void setDescription(String desc) {
		description=desc;
	}
	
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String dat ) {
		pubDate=dat;
	}
	public String getAuthor () {
		return author;
	}
	public void setAuthor(String aut) {
		author=aut;
	}
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String gu) {
		guid=gu;
	}
	public String getLink(){
		return link;
	}
	public void setLink(String li) {
		link=li;
	}
	
	public String toString() {
		String invio="";
		invio=title+"\n"+description+"\n"+link+"\n"+author+"\n"+pubDate+"\n"+guid+"\n";
		return invio;
	}
	
}
