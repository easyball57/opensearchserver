/**   
 * License Agreement for Jaeksoft OpenSearchServer
 *
 * Copyright (C) 2008-2010 Emmanuel Keller / Jaeksoft
 * 
 * http://www.open-search-server.com
 * 
 * This file is part of Jaeksoft OpenSearchServer.
 *
 * Jaeksoft OpenSearchServer is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 * Jaeksoft OpenSearchServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Jaeksoft OpenSearchServer. 
 *  If not, see <http://www.gnu.org/licenses/>.
 **/

package com.jaeksoft.searchlib.parser;

import java.io.IOException;
import java.util.Locale;

import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.knallgrau.utils.textcat.TextCategorizer;

import com.jaeksoft.searchlib.util.Lang;

public class XlsParser extends Parser {

	private static ParserFieldEnum[] fl = { ParserFieldEnum.title,
			ParserFieldEnum.author, ParserFieldEnum.subject,
			ParserFieldEnum.content, ParserFieldEnum.lang,
			ParserFieldEnum.filename, ParserFieldEnum.content_type };

	public XlsParser() {
		super(fl);
	}

	@Override
	protected void parseContent(LimitInputStream inputStream)
			throws IOException {

		HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
		ExcelExtractor excel = new ExcelExtractor(workbook);

		SummaryInformation info = excel.getSummaryInformation();
		if (info != null) {
			addField(ParserFieldEnum.title, info.getTitle());
			addField(ParserFieldEnum.author, info.getAuthor());
			addField(ParserFieldEnum.subject, info.getSubject());
		}

		String content = excel.getText();
		addField(ParserFieldEnum.content, content.replaceAll("\\s+", " "));

		// Identification de la langue:
		Locale lang = null;
		String langMethod = null;
		String text = getMergedBodyText(1000, " ", ParserFieldEnum.content);
		if (text != null) {
			langMethod = "ngram recognition";
			String textcat = new TextCategorizer().categorize(text,
					text.length());
			lang = Lang.findLocaleDescription(textcat);
		}

		if (lang != null) {
			addField(ParserFieldEnum.lang, lang.getLanguage());
			addField(ParserFieldEnum.lang_method, langMethod);
		}

	}

	@Override
	protected void parseContent(LimitReader reader) throws IOException {
		throw new IOException("Unsupported");
	}

	@Override
	public ParserFieldEnum[] getParserFieldList() {
		return fl;
	}

}