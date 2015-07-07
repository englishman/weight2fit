package org.weight2fit.application.ui.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.weight2fit.application.Weight2FitApplication;
import org.weight2fit.application.ui.UiFitParamsSupplier;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsConsumer;
import org.weight2fit.infrastructure.FileParamsConsumer;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * GUI application
 *
 * @author Andriy Kryvtsun
 */
public class GuiApplication implements Weight2FitApplication {
    private static final Logger LOG = Logger.getLogger(GuiApplication.class.getName());

    private final Display display;

    public GuiApplication() {
        display = Display.getDefault();
    }

    @Override
    public int execute() {
        UiFitParamsSupplier supplier = new GuiParamsSupplier();

        FitParams params = null;
        do {
            try {
                params = supplier.get();

                if (params != null) {
                    File outFile = supplier.getFile();

                    FitParamsConsumer consumer = new FileParamsConsumer(outFile);
                    consumer.accept(params);

                    showMessage(SWT.ICON_INFORMATION, "Info", "FIT file '" + outFile + "' was created");
                }
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "an exception was thrown", e);

                String errorMessage = e.getCause() != null
                        ? e.getCause().getLocalizedMessage()
                        : e.getLocalizedMessage();
                showMessage(SWT.ICON_ERROR, "Error", errorMessage);
            }
        }
        while (params != null);

        return 1;
    }

    private void showMessage(int flags, String text, String message) {
        Shell parent = display.getActiveShell();

        MessageBox messageBox = new MessageBox(parent, SWT.OK | SWT.SHEET | flags);
        messageBox.setText(text);
        messageBox.setMessage(message);

        messageBox.open();
    }
}
