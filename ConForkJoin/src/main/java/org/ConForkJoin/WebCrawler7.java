package org.ConForkJoin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ForkJoinPool;

import org.LinkHandler.LinkHandler;

public class WebCrawler7 implements LinkHandler {

	// private final Collection<String> visitedLinks =
	// Collections.synchronizedSet(new HashSet<String>());
	private final Collection<String> visitedLinks = Collections.synchronizedList(new ArrayList<String>());
	private String url;
	private ForkJoinPool mainPool;

	public WebCrawler7(String startingURL, int maxThreads) {
		this.url = startingURL;
		mainPool = new ForkJoinPool(maxThreads);
	}

	private void startCrawling() {
		mainPool.invoke(new LinkFinderAction(this.url, this));
	}

	public int size() {
		return visitedLinks.size();
	}

	public void addVisited(String s) {
		visitedLinks.add(s);
	}

	public boolean visited(String s) {
		return false; // visitedLinks.contains(s);
	}

	public void queueLink(String link) throws Exception {

	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) throws Exception {
		new WebCrawler7("https://github.com/", 64).startCrawling();
	}
}
