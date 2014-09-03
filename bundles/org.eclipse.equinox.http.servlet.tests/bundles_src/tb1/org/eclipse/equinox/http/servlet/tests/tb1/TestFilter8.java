/*******************************************************************************
 * Copyright (c) 2014 Raymond Augé and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 ******************************************************************************/

package org.eclipse.equinox.http.servlet.tests.tb1;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Hashtable;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.equinox.http.servlet.ExtendedHttpService;
import org.eclipse.equinox.http.servlet.tests.tb.AbstractTestServlet;
import org.eclipse.equinox.http.servlet.tests.util.BaseFilter;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.http.NamespaceException;

/*
 * This servlet is registered with the HttpService via the immediate DS
 * component OSGI-INF/testServlet1_component.xml.
 */
public class TestFilter8 extends AbstractTestServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void activate(ComponentContext componentContext) throws ServletException, NamespaceException {
		ExtendedHttpService service = (ExtendedHttpService)getHttpService();
		service.registerServlet(extensionAlias(), this, null, null);
		service.registerFilter(extensionAlias(), f1, new Hashtable<String, String>(), null);
		service.registerFilter(extensionAlias(), f2, new Hashtable<String, String>(), null);
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("filter-priority", "1");
		service.registerFilter(extensionAlias(), f3, hashtable, null);
	}

	@Override
	public void deactivate() {
		ExtendedHttpService service = (ExtendedHttpService)getHttpService();
		service.unregister(extensionAlias());
		service.unregisterFilter(f1);
		service.unregisterFilter(f2);
		service.unregisterFilter(f3);
	}

	@Override
	protected void handleDoGet(HttpServletRequest request, PrintWriter writer) throws ServletException, IOException {
		writer.print('a');
	}

	Filter f1 = new BaseFilter('c');
	Filter f2 = new BaseFilter('b');
	Filter f3 = new BaseFilter('d');
}
