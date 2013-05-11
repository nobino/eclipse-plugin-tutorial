package com.github.nobino.menu.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
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
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		String selectionFilePath = getSelectionFilePath(currentSelection);
		startNautilus(selectionFilePath);
		
		return null;
	}

	/**
	 * @param selectionFilePath
	 */
	private void startNautilus(String selectionFilePath) {
		ProcessBuilder nautilusBuilder = new ProcessBuilder("nautilus", selectionFilePath);
		nautilusBuilder.redirectErrorStream(true);
		try {
			Process nautilus = nautilusBuilder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(nautilus.getInputStream()));
			String read;
			while (null != (read = reader.readLine())) {
				System.out.println(read);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param currentSelection 
	 * @return file path to current selection.
	 */
	private String getSelectionFilePath(ISelection currentSelection) {
		StringBuilder builder = new StringBuilder();
		if (currentSelection instanceof IStructuredSelection) {
			IStructuredSelection sSelection = (IStructuredSelection) currentSelection;
			for (Iterator<?> iterator = sSelection.iterator(); iterator.hasNext();) {
				Object type = (Object) iterator.next();
				if (type instanceof IJavaElement) {
					IJavaElement javaElement = (IJavaElement) type;
					IResource resource = javaElement.getResource();
					URI uri = resource.getLocationURI();
					IFileStore store = null;
					try {
						 store = EFS.getStore(uri);
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					builder.append(store.toString());
				}
			}
		}
		String selectionFilePath = builder.toString();
		return selectionFilePath;
	}
}
