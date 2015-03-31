package eu.maurosabatino.rssparser.rssParser;

import java.io.*;
import java.net.*;
import org.xmlpull.v1.*;


public class RSSFeedParser {
	private XmlPullParser xmlParser;
	private int eventType;
	private String ns;
	
	public RSSFeedParser(XmlPullParser xmlParser)
			throws XmlPullParserException, IOException {
		this.xmlParser = xmlParser;
		move();
	}

	void move() throws XmlPullParserException, IOException {
		eventType = xmlParser.next();
	}
	
	void matchStart(String t) throws XmlPullParserException, IOException {
		xmlParser.require(XmlPullParser.START_TAG, ns, t);
	}

	void matchEnd(String t) throws XmlPullParserException, IOException {
		xmlParser.require(XmlPullParser.END_TAG, ns, t);
	}
	
	public Feed rss () throws IOException, XmlPullParserException {
		matchStart(Tag.rss);
		move();
		Feed feed=new Feed();
		while(eventType != XmlPullParser.END_DOCUMENT){
			if (xmlParser.getEventType() != XmlPullParser.START_TAG) {
				move();
				continue;
			}
			String tag=xmlParser.getName();
			
			if(tag.equals(Tag.channel)) {
				move();
				continue;
			}
			if(tag.equals(Tag.title)) {
				System.out.println(readText());
				String titolo=readText();
				feed.setTitle(titolo);
				move();
				continue;
			}
			
			if(tag.equals(Tag.link)) {
				String link=readText();
				feed.setLink(link);
				move();
				continue;
			} 
			if(tag.equals(Tag.description)) {
				String description=readText();
				feed.setDescription(description);
				move();
				continue;
			}
			if(tag.equals(Tag.pubDate)){
				String pubDate=readText();
				feed.setPubDate(pubDate);
				move();
				continue;
			}
			if(tag.equals(Tag.item)) {
				FeedMessage messaggio=feedMessage();
				feed.addFeed(messaggio);
				
			}
			else skip();
			}
	System.out.println("end parsing news");
	return feed;
		}
	
	
	/*private Feed channel()throws IOException, XmlPullParserException{
		Feed feed = new Feed();
		while(eventType!=XmlPullParser.END_TAG) {
			if (xmlParser.getEventType() != XmlPullParser.START_TAG) {
				move();
				continue;
			}
			String name=xmlParser.getName();
			if(name.equals(Tag.channel)){
				move();
			}else if(name.equals(Tag.title)) {
				feed.setTitle(title());
				continue;
			}
			else if(name.equals(Tag.pubDate)) {
				feed.setPubDate(pubDate());
				continue;
			}
			else if(name.equals(Tag.item)) {
					FeedMessage messaggio=feedMessage();
					feed.addFeed(messaggio);
			}else skip();
		}
		return feed;
	}*/
	
	private FeedMessage feedMessage() throws IOException, XmlPullParserException {
		FeedMessage feedMessage=new FeedMessage();
		while(eventType!=XmlPullParser.END_TAG) {
			if (xmlParser.getEventType() != XmlPullParser.START_TAG) {
				move();
				continue;
			}
			String name=xmlParser.getName();
			
			if (name.equals(Tag.item)) {
				move();
			} else if(name.equals(Tag.title)){
				feedMessage.setTitle(title());
			}else if(name.equals(Tag.link)){
				feedMessage.setLink(link());
			}else if(name.equals(Tag.description)){
				feedMessage.setDescription(description());
			}
			else if(name.equals(Tag.pubDate)){
				feedMessage.setPubDate(pubDate());
			}
			else if(name.equals(Tag.guid)) {
				feedMessage.setGuid(guid());
			}
			else {
				skip();
			}
		}
		return feedMessage;
	}
	
	/*metodi per ritornare testo--------------------------------------*/
	private String title() throws IOException, XmlPullParserException{
		String title = readText();
		matchEnd(Tag.title);
		move();
		return title;
	}
	
	private String link() throws IOException, XmlPullParserException{
		String link = readText();
		matchEnd(Tag.link);
		move();
		return link;
	}
	
	private String description() throws IOException, XmlPullParserException{
		String description = readText();
		matchEnd(Tag.description);
		move();
		return description;
	}
	
	private String pubDate() throws IOException, XmlPullParserException{
		String pubDate = readText();
		matchEnd(Tag.pubDate);
		move();
		return pubDate;
	}
	
	private String guid() throws IOException, XmlPullParserException{
		String guid = readText();
		matchEnd(Tag.guid);
		move();
		return guid;
	}
	/*----------------------------------------------------------------*/
	
	private String readText() throws IOException, XmlPullParserException {
		String result = "";
		if (xmlParser.next() == XmlPullParser.TEXT) {
			result = xmlParser.getText();
			move();
		}
		return result;
	}
	
	private void skip() throws XmlPullParserException, IOException {
		if (eventType != XmlPullParser.START_TAG)
			throw new IllegalStateException();
		int depth = 1;
		move();
		while (depth != 0) {
			switch (eventType) {
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
			move();
		}
	}

	public static void main(String[] args) throws MalformedURLException, IOException, XmlPullParserException {
		
		
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		
		factory.setNamespaceAware(true);
		XmlPullParser xmlParser = factory.newPullParser();
		
		
		//File f=new File("src/rssNews.xml"); // per utilizzare file locale
		//InputStream xmlStream = new FileInputStream(f);
		InputStream in=new URL("http://www.educ.di.unito.it/EduFeed/rssNews_20.xml").openStream();
		
		InputStream xmlStream=in;
		
		xmlParser.setInput(xmlStream,null);
		
		RSSFeedParser parser=new RSSFeedParser(xmlParser);
		System.out.println("new RSSParser");
		Feed feed=parser.rss();
		FeedToHTML visitor = new FeedToHTML("Visitor");
		visitor.visit(feed);
		visitor.htmlFile(visitor.getHTML()); //su file o in console(true)
		
	}
		

}

