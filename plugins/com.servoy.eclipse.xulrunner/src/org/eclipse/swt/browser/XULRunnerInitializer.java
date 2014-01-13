package org.eclipse.swt.browser;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class XULRunnerInitializer {
    private static final String XULRUNNER_PATH = "org.eclipse.swt.browser.XULRunnerPath";

	static {
    	String osArch = Platform.getOSArch();
        String platformBundle  = new StringBuffer("com.servoy.eclipse.xulrunner") //$NON-NLS-1$
                .append(".") //$NON-NLS-1$
                .append(Platform.getOS())
                .append(Platform.OS_MACOSX.equals(Platform.getOS()) ? "" : (new StringBuffer(".")).append(osArch).toString()) //$NON-NLS-1$ //$NON-NLS-2$
                .toString();
        
        Bundle bundle = Platform.getBundle(platformBundle); //$NON-NLS-1$
        if (bundle != null) 
        {
            URL resourceUrl = bundle.getResource("xulrunner"); //$NON-NLS-1$
            if (resourceUrl != null) {
                try {
                    URL fileUrl = FileLocator.toFileURL(resourceUrl);
                 // We need to use the 3-arg constructor of URI in order to properly escape file system chars.
                    File file = new File(new URI(fileUrl.getProtocol(), fileUrl.getPath(), null));
                    System.setProperty(XULRUNNER_PATH, file.getAbsolutePath()); //$NON-NLS-1$
                } catch (IOException e) {
                	e.printStackTrace();
                    // log the exception
                } catch (URISyntaxException e) {
                	e.printStackTrace();
                    // log the exception
                }
            }
        }
    }
}