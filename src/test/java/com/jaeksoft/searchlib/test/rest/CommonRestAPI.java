/**
 * License Agreement for OpenSearchServer
 * <p>
 * Copyright (C) 2013-2014 Emmanuel Keller / Jaeksoft
 * <p>
 * http://www.open-search-server.com
 * <p>
 * This file is part of OpenSearchServer.
 * <p>
 * OpenSearchServer is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * OpenSearchServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with OpenSearchServer.
 * If not, see <http://www.gnu.org/licenses/>.
 **/

package com.jaeksoft.searchlib.test.rest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.jaeksoft.searchlib.test.IntegrationTest;
import com.jaeksoft.searchlib.webservice.CommonResult;
import com.jaeksoft.searchlib.webservice.query.search.SearchResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import static org.junit.Assert.*;

public abstract class CommonRestAPI {

	public WebClient client() {
		WebClient webClient = WebClient
				.create(IntegrationTest.SERVER_URL, Collections.singletonList(new JacksonJsonProvider()));
		// WebClient.getConfig(webClient).getRequestContext().put("use.async.http.conduit",
		// Boolean.TRUE);
		return webClient;
	}

	public <T extends CommonResult> T checkCommonResult(Response response, Class<T> commonResultClass, int httpCode) {
		assertNotNull(response);
		assertEquals((int) httpCode, response.getStatus());
		T commonResult = response.readEntity(commonResultClass);
		assertNotNull(commonResult.successful);
		assertEquals(true, commonResult.successful);
		return commonResult;
	}

	private final static ObjectMapper objectMapper = new ObjectMapper();

	public <T extends CommonResult> T checkCommonResult(CloseableHttpResponse response, Class<T> commonResultClass,
			int httpCode) throws JsonParseException, JsonMappingException, UnsupportedOperationException, IOException {
		assertNotNull(response);
		assertEquals((int) httpCode, response.getStatusLine().getStatusCode());
		T commonResult = objectMapper.readValue(response.getEntity().getContent(), commonResultClass);
		assertNotNull(commonResult.successful);
		assertEquals(true, commonResult.successful);
		return commonResult;
	}

	public void checkCommonResultDetail(CommonResult commonResult, String detail, Object expectedValue) {
		Object value = commonResult.details.get(detail);
		assertEquals(expectedValue, value);
	}

	public String getResource(String name) throws IOException {
		InputStream is = getClass().getResourceAsStream(name);
		assertNotNull("Resource not found: " + name, is);
		String res = IOUtils.toString(is);
		assertFalse("Resource is empty: " + name, StringUtils.isEmpty(res));
		return res;
	}

	private SearchResult search(String json, String path) throws ClientProtocolException, IOException {
		Response response = client().path(path, IntegrationTest.INDEX_NAME).accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).post(json);
		return checkCommonResult(response, SearchResult.class, 200);
	}

	public SearchResult searchPattern(String json) throws ClientProtocolException, IOException {
		return search(json, "/services/rest/index/{index_name}/search/pattern");
	}

	public SearchResult searchField(String json) throws ClientProtocolException, IOException {
		return search(json, "/services/rest/index/{index_name}/search/field");
	}

	public void updateDocuments(String json) throws ClientProtocolException, IOException {
		Response response = client().path("/services/rest/index/{index_name}/document", IntegrationTest.INDEX_NAME)
				.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).put(json);
		checkCommonResult(response, CommonResult.class, 200);
	}

	public void deleteAll() throws ClientProtocolException, IOException {
		Response response = client().path("/services/rest/index/{index_name}/document/", IntegrationTest.INDEX_NAME)
				.accept(MediaType.APPLICATION_JSON).query("query", "*:*").delete();
		checkCommonResult(response, CommonResult.class, 200);
	}

}
