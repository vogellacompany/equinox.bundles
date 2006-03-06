/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.core.internal.runtime;

import java.io.IOException;
import java.net.URL;
import org.eclipse.core.internal.boot.PlatformURLConnection;
import org.eclipse.core.internal.boot.PlatformURLHandler;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Bundle;

/**
 * Platform URL support
 * platform:/plugin/pluginId/		maps to pluginDescriptor.getInstallURLInternal()
 */
public class PlatformURLPluginConnection extends PlatformURLConnection {

	private Bundle target = null;
	private static boolean isRegistered = false;
	public static final String PLUGIN = "plugin"; //$NON-NLS-1$

	/*
	 * Constructor for the class.
	 */
	public PlatformURLPluginConnection(URL url) {
		super(url);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.internal.url.PlatformURLConnection#allowCaching()
	 */
	protected boolean allowCaching() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.internal.url.PlatformURLConnection#resolve()
	 */
	protected URL resolve() throws IOException {
		String spec = url.getFile().trim();
		if (spec.startsWith("/")) //$NON-NLS-1$
			spec = spec.substring(1);
		if (!spec.startsWith(PLUGIN))
			throw new IOException(NLS.bind(CommonMessages.url_badVariant, url));
		int ix = spec.indexOf("/", PLUGIN.length() + 1); //$NON-NLS-1$
		String ref = ix == -1 ? spec.substring(PLUGIN.length() + 1) : spec.substring(PLUGIN.length() + 1, ix);
		String id = getId(ref);
		Activator activator = Activator.getDefault();
		if (activator == null)
			throw new IOException(CommonMessages.activator_not_available);
		target = activator.getBundle(id);
		if (target == null)
			throw new IOException(NLS.bind(CommonMessages.url_resolvePlugin, url));
		if (ix == -1 || (ix + 1) >= spec.length())
			return target.getEntry("/"); //$NON-NLS-1$
		URL result = target.getEntry(spec.substring(ix + 1));
		if (result != null)
			return result;
		// if the result is null then force the creation of a URL that will throw FileNotFoundExceptions
		return new URL(target.getEntry("/"), spec.substring(ix + 1)); //$NON-NLS-1$

	}

	public static void startup() {
		// register connection type for platform:/plugin handling
		if (isRegistered)
			return;
		PlatformURLHandler.register(PLUGIN, PlatformURLPluginConnection.class);
		isRegistered = true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.internal.url.PlatformURLConnection#getAuxillaryURLs()
	 */
	public URL[] getAuxillaryURLs() throws IOException {
		if (target == null) {
			String spec = url.getFile().trim();
			if (spec.startsWith("/")) //$NON-NLS-1$
				spec = spec.substring(1);
			if (!spec.startsWith(PLUGIN))
				throw new IOException(NLS.bind(CommonMessages.url_badVariant, url));
			int ix = spec.indexOf("/", PLUGIN.length() + 1); //$NON-NLS-1$
			String ref = ix == -1 ? spec.substring(PLUGIN.length() + 1) : spec.substring(PLUGIN.length() + 1, ix);
			String id = getId(ref);
			Activator activator = Activator.getDefault();
			if (activator == null)
				throw new IOException(CommonMessages.activator_not_available);
			target = activator.getBundle(id);
			if (target == null)
				throw new IOException(NLS.bind(CommonMessages.url_resolvePlugin, url));
		}
		Bundle[] fragments = Activator.getDefault().getFragments(target);
		int fragmentLength = (fragments == null) ? 0 : fragments.length;
		if (fragmentLength == 0)
			return null;
		URL[] result = new URL[fragmentLength];
		for (int i = 0; i < fragmentLength; i++)
			result[i] = fragments[i].getEntry("/"); //$NON-NLS-1$
		return result;
	}
}
