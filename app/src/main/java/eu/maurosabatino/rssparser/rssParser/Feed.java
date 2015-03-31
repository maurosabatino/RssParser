package eu.maurosabatino.rssparser.rssParser;
import java.util.LinkedList;


public class Feed {
	
	private String title;
	private String link;
	private String description;
	private String language;
	private String copyright;
	private String pubDate;
	private LinkedList <FeedMessage> items;
	
	public Feed() {
		title="";
		link="";
		description="";
		language="";
		copyright="";
		pubDate="";
		items=new LinkedList<FeedMessage>();
	}
	
	public void setTitle(String name) {
		title=name;
	}
	public String getTitle() {
		return title;
	}
	public void setLink(String link) {
		this.link=link;
	}
	public String getLink() {
		return link;
	}
	public void setPubDate(String dat) {
		pubDate=dat;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setDescription(String desc) {
		description=desc;
	}
	public String getDescription() {
		return description;
	}
	public Feed(String name) {
		title=name;
		items=new LinkedList<FeedMessage>();
	}
	
	public FeedMessage getMessage(int i) {
		return items.get(i);
	}
	
	public void addFeed(FeedMessage f) {
		items.add(f);
	}
	
	public LinkedList<FeedMessage> getFeedList() {
		return items;
	}
	
	public int size() {
		return items.size();
	}
	
	public String toString() {
		String ret="";
		
		for(FeedMessage feedMessage : items) {
			ret+=feedMessage;
			
		}
		return ret;
	}
	public void accept(FeedVisitor v) {
		v.visit(this);
	}
	
	

}
