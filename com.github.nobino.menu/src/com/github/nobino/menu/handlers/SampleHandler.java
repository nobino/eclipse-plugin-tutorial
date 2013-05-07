package com.github.nobino.menu.handlers;

import java.net.URI;
import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public SampleHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		
		StringBuilder builder = new StringBuilder();
		
		
		if (currentSelection instanceof IStructuredSelection) {
			IStructuredSelection sSelection = (IStructuredSelection) currentSelection;
			for (Iterator<?> iterator = sSelection.iterator(); iterator.hasNext();) {
				Object type = (Object) iterator.next();
				if (type instanceof IJavaElement) {
					IJavaElement javaElement = (IJavaElement) type;
					IResource resource = javaElement.getResource();
					URI uri = resource.getLocationURI();
					
					builder.append(uri.toString());
					builder.append(System.getProperty("line.separator"));
				}
			}
		}
		
		MessageDialog.openInformation(
				window.getShell(),
				"リソースの情報を得るプラグイン",
				builder.toString());
		return null;
	}
}
