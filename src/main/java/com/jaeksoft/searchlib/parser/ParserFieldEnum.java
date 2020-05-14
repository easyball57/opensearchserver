/**
 * License Agreement for OpenSearchServer
 * <p>
 * Copyright (C) 2008-2015 Emmanuel Keller / Jaeksoft
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

package com.jaeksoft.searchlib.parser;

public enum ParserFieldEnum {

	parser_name,

	url,

	title,

	generated_title,

	body,

	meta_keywords,

	meta_description,

	meta_robots,

	lang_method,

	internal_link,

	external_link,

	htmlProvider,

	htmlSource,

	generatedSource,

	creator,

	author,

	subject,

	description,

	content,

	producer,

	keywords,

	creation_date,

	modification_date,

	language,

	number_of_pages,

	note,

	other,

	charset,

	internal_link_nofollow,

	external_link_nofollow,

	lang,

	artist,

	album,

	year,

	track,

	genre,

	album_artist,

	comment,

	composer,

	grouping,

	name,

	announce,

	total_length,

	file_length,

	file_path,

	file_name,

	info_hash,

	info_hash_urlencoded,

	ocr_content,

	pdfcrack_password,

	image_ocr_boxes,

	image_phash,

	image_height,

	image_width,

	image_number,

	image_format,

	image_area_size,

	channel_title,

	channel_link,

	channel_description,

	link,

	rss_mode,

	md5,

	email_display_from,

	email_display_to,

	email_display_cc,

	email_display_bcc,

	email_recipient_name,

	email_recipient_address,

	email_conversation_topic,

	email_sent_date,

	email_received_date,

	email_attachment_name,

	email_attachment_type,

	email_attachment_content,

	// Addon Azure Identity Protection
	sensitivity;

	public static ParserFieldEnum find(String fieldName) {
		for (ParserFieldEnum pfe : values())
			if (pfe.name().equals(fieldName))
				return pfe;
		return null;
	}

}
