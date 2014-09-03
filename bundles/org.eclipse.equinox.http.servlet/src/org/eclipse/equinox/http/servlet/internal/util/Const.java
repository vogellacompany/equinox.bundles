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

package org.eclipse.equinox.http.servlet.internal.util;

/**
 * @author Raymond Augé
 */
public class Const {

	public static final String BLANK = ""; //$NON-NLS-1$
	public static final String CLOSE_PAREN = ")"; //$NON-NLS-1$
	public static final String[] EMPTY_ARRAY = new String[0];
	public static final String EQUAL = "="; //$NON-NLS-1$
	public static final String FILTER_NAME = "filter-name"; //$NON-NLS-1$
	public static final String FILTER_PRIORITY = "filter-priority"; //$NON-NLS-1$
	public static final String OPEN_PAREN = "("; //$NON-NLS-1$
	public static final String SERVLET_NAME = "servlet-name"; //$NON-NLS-1$
	public static final String SLASH = "/"; //$NON-NLS-1$
	public static final String SLASH_STAR = "/*"; //$NON-NLS-1$

	public static enum Dispatcher {

		ASYNC, ERROR, FORWARD, INCLUDE, REQUEST

	}

}
