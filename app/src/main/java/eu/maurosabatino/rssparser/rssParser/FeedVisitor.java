package eu.maurosabatino.rssparser.rssParser;

public interface FeedVisitor {
	void visit(Feed feed);
	void visit(FeedMessage feedMessage);
	
}
