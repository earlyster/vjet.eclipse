/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.vjet.testframework.util;

import junit.framework.Assert;

import org.eclipse.swt.widgets.Display;

/**
 * 
 * @author ddodd
 * 
 * The DialogMonitor is used to detect dialogs which appear during a UI plugin unit tests.
 * The IDialogProcessor can be customize different types of dialogs.  Once the test is over,
 * the passed in IDialogProcess can be used to query any information during the test.
 * 
 * See DefaultDialogProcessor 
 *
 */
public class DialogMonitor {
	
	DialogMonitorJob m_dialogMonitorJob;
	
	IDialogProcessor m_dialogProcessor;
	
	boolean syncMode=true;//is in sync mode,default to true

	public boolean isSyncMode() {
		return syncMode;
	}

	public void setSyncMode(boolean syncMode) {
		this.syncMode = syncMode;
	}

	/**
	 * Create the default processor
	 *
	 */
	public DialogMonitor() {
		m_dialogProcessor = new DefaultDialogProcessor();
	}
	
	public DialogMonitor(IDialogProcessor dialogProcessor) {
		m_dialogProcessor = dialogProcessor;
	}
	

	/**
	 * Fire up the thread and start looking for the dialog
	 *
	 */
	public void startMonitoring() {
		
 	   final Display m_display = Display.getCurrent();
 	   
 	   Assert.assertNotNull(m_display);
		
		m_dialogMonitorJob = new DialogMonitorJob(m_display, m_dialogProcessor,isSyncMode());
		m_dialogMonitorJob.start();
	}

	/**
	 * Tell the thread to stop.
	 * @return
	 */
	public void stopMonitoring() {
		
		m_dialogMonitorJob.setAllDone(true);
		
	}
}
