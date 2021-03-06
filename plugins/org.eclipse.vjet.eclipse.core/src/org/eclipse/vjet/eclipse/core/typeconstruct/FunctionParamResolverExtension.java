/*******************************************************************************
 * Copyright (c) 2012 eBay Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     eBay Inc. - initial API and implementation
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.vjet.eclipse.core.typeconstruct;

import org.eclipse.vjet.dsf.jstojava.resolver.IFunctionMetaMapping;
import org.eclipse.vjet.dsf.jstojava.resolver.ITypeConstructorResolver;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

/**
 * Represents an extension for
 * {@link TypeConstructResolverExtensionRegistry#EXTENSION_POINT_ID} extension
 * point.
 * 
 * @author paragraval
 * 
 */
public class FunctionParamResolverExtension {

	public static final String ATT_NAME = "name"; //$NON-NLS-1$
	public static final String ATT_KEY = "key"; //$NON-NLS-1$
	public static final String TAG_DESCRIPTION = "description"; //$NON-NLS-1$
	public static final String ATT_CLASS = "class"; //$NON-NLS-1$

	private final String name;
	private final String key;
	private String description;
	private String executableClass;

	private final IConfigurationElement configurationElement;

	/**
	 * Default constructor
	 */
	public FunctionParamResolverExtension(IConfigurationElement element) {

		this.configurationElement = element;

		this.name = configurationElement.getAttribute(ATT_NAME);
		Assert.isLegal(this.name != null);

		this.key = configurationElement.getAttribute(ATT_KEY);
		Assert.isLegal(this.key != null);

		this.executableClass = configurationElement.getAttribute(ATT_CLASS);
		Assert.isLegal(executableClass != null);
	}

	/**
	 * Returns human-readable name of this resolver.
	 * 
	 * @return human-readable name of this resolver
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the key to which this extension is mapped.
	 * 
	 * @return the key to which this extension is mapped
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Returns description of this resolver, if available.
	 * 
	 * @return description of this resolver, if available
	 */
	public String getDescription() {
		if (description == null) {
			IConfigurationElement[] children = configurationElement
					.getChildren(TAG_DESCRIPTION);
			if (children.length > 0) {
				description = children[0].getValue();
			}
		}
		return description;
	}

	/**
	 * Returns a new instance of {@link ITypeConstructorResolver} as described
	 * by the 'class' attribute.
	 * @return 
	 * 
	 * @see IConfigurationElement#createExecutableExtension(String)
	 * 
	 * @return a new instance of {@link ITypeConstructorResolver}
	 * @throws CoreException
	 *             if an instance of the executable extension could not be
	 *             created for any reason
	 */
	public  IFunctionMetaMapping createResolver() throws CoreException {
		IFunctionMetaMapping typeConstructResolver = (IFunctionMetaMapping) configurationElement
				.createExecutableExtension(ATT_CLASS);
		return typeConstructResolver;
	}

	@Override
	public String toString() {
		String value = name + " [key: " + key + " , class: " + executableClass
				+ "]";
		return value;
	}

}
