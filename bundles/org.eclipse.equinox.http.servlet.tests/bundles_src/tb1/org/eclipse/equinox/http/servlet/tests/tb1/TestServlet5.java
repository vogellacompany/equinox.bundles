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

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.equinox.http.servlet.tests.tb.AbstractTestServlet;

import org.osgi.framework.Constants;
import org.osgi.service.http.runtime.HttpServiceRuntime;

/**
 * @author Raymond Augé
 */
public class TestServlet5 extends AbstractTestServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, Object> properties;

	@Override
	protected void handleDoGet(HttpServletRequest request, PrintWriter writer) throws ServletException, IOException {
		writer.print(properties.get(Constants.SERVICE_DESCRIPTION));
	}

	public void setHttpServiceRuntime(HttpServiceRuntime httpServiceRuntime, Map<String, Object> properties) {
		this.properties = properties;
	}

}
