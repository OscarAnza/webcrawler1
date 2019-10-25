package org.ConExecuteService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.LinkHandler.LinkHandler;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

public class LinkFinder implements Runnable {

	private String url;
	private LinkHandler linkHandler;
	/**
	 * Used fot statistics
	 */
	private static final long t0 = System.nanoTime();

	public LinkFinder(String url, LinkHandler handler) {
		this.url = url;
		this.linkHandler = handler;
	}

	public void run() {
		getSimpleLinks(url);
	}

	private void getSimpleLinks(String url) {
		// if not already visited
		if (!linkHandler.visited(url)) {
			try {
				// System.out.println(linkHandler.size() + " " + url);
				URL uriLink = new URL(url);
				Parser parser = new Parser(uriLink.openConnection());
				NodeList list = parser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));
				List<String> urls = new ArrayList<String>();

				for (int i = 0; i < list.size(); i++) {
					LinkTag extracted = (LinkTag) list.elementAt(i);

					if (!extracted.getLink().isEmpty() && !linkHandler.visited(extracted.getLink())) {

						urls.add(extracted.getLink());
					}

				}
				// we visited this url
				linkHandler.addVisited(url);

				/*
				 * if (linkHandler.size() == 1500) {
				 * System.out.println("Time to visit 1500 distinct links = " +
				 * (System.nanoTime() - t0)); }
				 */

				if (linkHandler.size() == 3000) {
					System.out.println("Time to visit 3000 non-distinct links = " + (System.nanoTime() - t0));
				}

				for (String l : urls) {
					linkHandler.queueLink(l);
				}
			} catch (Exception e) {
				// System.out.println("Destino inalcanzable");
			}
		}
	}
}
