/**   
 * License Agreement for OpenSearchServer
 *
 * Copyright (C) 2011-2014 Emmanuel Keller / Jaeksoft
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
package com.jaeksoft.searchlib.webservice.crawler.webcrawler;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.jaeksoft.searchlib.webservice.CommonResult;

@WebService(name = "WebCrawler")
public interface SoapWebCrawler {

	public CommonResult run(@WebParam(name = "use") String use,
			@WebParam(name = "login") String login,
			@WebParam(name = "key") String key,
			@WebParam(name = "once") Boolean bOnce);

	public CommonResult stop(@WebParam(name = "use") String use,
			@WebParam(name = "login") String login,
			@WebParam(name = "key") String key);

	public CommonResult status(@WebParam(name = "use") String use,
			@WebParam(name = "login") String login,
			@WebParam(name = "key") String key);

	public CommonResult injectPatternsInclusion(
			@WebParam(name = "use") String use,
			@WebParam(name = "login") String login,
			@WebParam(name = "key") String key,
			@WebParam(name = "replaceAll") Boolean replaceAll,
			@WebParam(name = "inject_urls") Boolean inject_urls,
			@WebParam(name = "inject") List<String> injectList);

	public CommonResult injectPatternsExclusion(
			@WebParam(name = "use") String use,
			@WebParam(name = "login") String login,
			@WebParam(name = "key") String key,
			@WebParam(name = "replaceAll") Boolean replaceAll,
			@WebParam(name = "inject") List<String> injectList);

	public CommonResult injectUrls(@WebParam(name = "use") String use,
			@WebParam(name = "login") String login,
			@WebParam(name = "key") String key,
			@WebParam(name = "replace") Boolean replaceAll,
			@WebParam(name = "inject") List<String> urls);

	@WebResult(name = "urls")
	public byte[] exportURLs(@WebParam(name = "use") String use,
			@WebParam(name = "login") String login,
			@WebParam(name = "key") String key);

	public byte[] exportSiteMap(@WebParam(name = "use") String use,
			@WebParam(name = "host") String host,
			@WebParam(name = "login") String login,
			@WebParam(name = "key") String key);

	public CommonResult captureScreenshot(@WebParam(name = "use") String use,
			@WebParam(name = "login") String login,
			@WebParam(name = "key") String key,
			@WebParam(name = "url") String url);

	public CommonResult checkScreenshot(@WebParam(name = "use") String use,
			@WebParam(name = "login") String login,
			@WebParam(name = "key") String key,
			@WebParam(name = "url") String url);

	public CommonResult crawl(@WebParam(name = "use") String use,
			@WebParam(name = "login") String login,
			@WebParam(name = "key") String key,
			@WebParam(name = "url") String url,
			@WebParam(name = "returnData") Boolean returnData);
}
