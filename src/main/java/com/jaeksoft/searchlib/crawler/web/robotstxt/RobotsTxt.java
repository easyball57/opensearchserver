/**   
 * License Agreement for OpenSearchServer
 *
 * Copyright (C) 2008-2013 Emmanuel Keller / Jaeksoft
 * 
 * http://www.open-search-server.com
 * 
 * This file is part of OpenSearchServer.
 *
 * OpenSearchServer is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 * OpenSearchServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with OpenSearchServer. 
 *  If not, see <http://www.gnu.org/licenses/>.
 **/

package com.jaeksoft.searchlib.crawler.web.robotstxt;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;

import com.jaeksoft.searchlib.crawler.web.database.RobotsTxtStatus;
import com.jaeksoft.searchlib.crawler.web.database.UrlItem;
import com.jaeksoft.searchlib.crawler.web.spider.Crawl;
import com.jaeksoft.searchlib.util.LinkUtils;

public class RobotsTxt {

	private long crawlTime;

	private long expirationTime;

	private DisallowList disallowList;

	private Crawl crawl;

	protected RobotsTxt(Crawl crawl) {
		this.crawlTime = System.currentTimeMillis();
		this.expirationTime = this.crawlTime + 1000 * 60 * 60 * 24;
		this.disallowList = (DisallowList) crawl.getParser();
		this.crawl = crawl;
	}

	/**
	 * Construit l'URL d'accès au fichier robots.txt à partir d'une URL donnée
	 * 
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 * @throws URISyntaxException
	 */
	protected static URL getRobotsUrl(URL url) throws MalformedURLException,
			URISyntaxException {
		StringBuilder sb = new StringBuilder();
		sb.append(url.getProtocol());
		sb.append("://");
		sb.append(url.getHost());
		if (url.getPort() != -1) {
			sb.append(':');
			sb.append(url.getPort());
		}
		sb.append("/robots.txt");
		return LinkUtils.newEncodedURL(sb.toString());
	}

	/**
	 * Return the status of the specified URL
	 * 
	 * @param url
	 * @param userAgent
	 * @return
	 * @throws MalformedURLException
	 * @throws URISyntaxException
	 */
	public RobotsTxtStatus getStatus(String userAgent, UrlItem urlItem)
			throws MalformedURLException, URISyntaxException {
		Integer code = crawl.getUrlItem().getResponseCode();
		if (code == null)
			return RobotsTxtStatus.ERROR;
		URL url = urlItem.getURL();
		if (url == null)
			throw new MalformedURLException("Malformed URL: "
					+ urlItem.getUrl());
		switch (code) {
		case 400:
		case 404:
			return RobotsTxtStatus.NO_ROBOTSTXT;
		case 200:
			break;
		default:
			return RobotsTxtStatus.ERROR;
		}
		if (disallowList == null)
			return RobotsTxtStatus.ALLOW;
		DisallowSet disallowSet = disallowList.get(userAgent.toLowerCase());
		if (disallowSet == null)
			disallowSet = disallowList.get("*");
		if (disallowSet == null)
			return RobotsTxtStatus.ALLOW;
		if (disallowSet.isAllowed(url.getFile()))
			return RobotsTxtStatus.ALLOW;
		return RobotsTxtStatus.DISALLOW;
	}

	/**
	 * Retourne la date d'expiration. Lorsque la date est expirée, le robots.txt
	 * est à nouveau téléchargé.
	 * 
	 * @return
	 */
	protected long getExpirationTime() {
		return expirationTime;
	}

	public Date getCrawlDate() {
		return new Date(crawlTime);
	}

	public Date getExpirationDate() {
		return new Date(expirationTime);
	}

	public Crawl getCrawl() {
		return crawl;
	}

	public DisallowList getDisallowList() {
		return disallowList;
	}

	public String getHostname() {
		if (crawl == null)
			return null;
		UrlItem urlItem = crawl.getUrlItem();
		if (urlItem == null)
			return null;
		return urlItem.getHost();
	}

	public boolean isCacheable() {
		if (crawl == null)
			return false;
		UrlItem urlItem = crawl.getUrlItem();
		if (urlItem == null)
			return false;
		Integer code = urlItem.getResponseCode();
		if (code == null)
			return false;
		switch (code) {
		case 200:
			return true;
		case 400:
		case 404:
			return true;
		default:
			return false;
		}
	}
}
