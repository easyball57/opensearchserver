/**   
 * License Agreement for OpenSearchServer
 *
 * Copyright (C) 2008-2010 Emmanuel Keller / Jaeksoft
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

package com.jaeksoft.searchlib.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Context {

	public static Object get(String lookup1, String lookup2)
			throws NamingException {
		javax.naming.Context ctx1 = new InitialContext();
		javax.naming.Context ctx2 = (javax.naming.Context) ctx1.lookup(lookup1);
		if (ctx2 == null)
			return null;
		return (String) ctx2.lookup(lookup2);
	}

	public static Object get(String lookup) throws NamingException {
		javax.naming.Context ctx = new InitialContext();
		return ctx.lookup(lookup);
	}

}
