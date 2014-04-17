package burp;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenuItem;

public class BurpExtender implements IBurpExtender, IContextMenuFactory {

    private static final String EXTENSION_NAME = "BurpMBClip";
    private static final String MENU_TITLE = "★ Copy as stated encoding";

    private IBurpExtenderCallbacks callbacks;
    private IExtensionHelpers helpers;

    @Override
    public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks) {

        // keep a reference to our callbacks object
        this.callbacks = callbacks;

        // obtain an extension helpers object
        helpers = callbacks.getHelpers();

        // set our extension name
        callbacks.setExtensionName(EXTENSION_NAME);

        // register ourselves as a custom context menu
        callbacks.registerContextMenuFactory(this);
    }

    @Override
    public List<JMenuItem> createMenuItems(IContextMenuInvocation invocation) {
        byte context = invocation.getInvocationContext();

        if (context != IContextMenuInvocation.CONTEXT_MESSAGE_EDITOR_RESPONSE
                && context != IContextMenuInvocation.CONTEXT_MESSAGE_VIEWER_RESPONSE) {
            return null;
        }

        final byte[] response = invocation.getSelectedMessages()[0].getResponse();

        JMenuItem menuItem = new JMenuItem(MENU_TITLE);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (arg0.getActionCommand().equals(MENU_TITLE)) {
                    clip(response);
                }
            }
        });

        List<JMenuItem> menuList = new ArrayList<>();
        menuList.add(menuItem);
        return menuList;
    }

    private void clip(byte[] response) {
        IResponseInfo responseInfo = helpers.analyzeResponse(response);
        List<String> responseHeaders = responseInfo.getHeaders();
        
        int offset = responseInfo.getBodyOffset();
        int length = response.length - offset;
        String responseBody = new String(response, offset, (length > 0 ? length : 0));
        
        //debug
        PrintWriter stdout = new PrintWriter(callbacks.getStdout(), true);
        stdout.println(responseBody);

        String encoding = HttpResponseEncodingUtil.getEncoding(responseHeaders, responseBody);

        String res;
        if (null == encoding || encoding.isEmpty()) {
            res = new String(response);
            callbacks.issueAlert("Encoding not specified. Copied using default encoding.");
        } else {
            try {
                res = new String(response, encoding);
            } catch (UnsupportedEncodingException ex) {
                res = new String(response);
                callbacks.issueAlert("Unknown encoding specified. Copied using default encoding.");
            }
        }

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(res);
        clipboard.setContents(selection, null);
    }
}
