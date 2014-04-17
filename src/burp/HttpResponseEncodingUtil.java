package burp;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility class to process character encoding of http responses.
 *
 * @author https://twitter.com/tkishiya
 * @version 1.0
 */
public final class HttpResponseEncodingUtil {

    // regex for http response header
    private static final String REGEX_HEADER_CHARSET = "^Content-Type\\s*:.*charset=[\"']?([\\w\\-\\.\\(\\):+]{2,127})";

    // regex for html
    private static final String REGEX_HTML_CHARSET = "<head>.*<meta\\s+http-equiv=[\"']content-type[\"'][^>]+charset=([\\w\\-\\.\\(\\):+]{2,127}).*</head>";

    // regex for html5: <meta charset="charset">
    private static final String REGEX_HTML5_CHARSET = "<head>.*<meta\\s+charset=[\"']?([\\w\\-\\.\\(\\):+]{2,127}).*</head>";

    // regex for xml
    private static final String REGEX_XML_ENCODING = "<\\?xml\\s+[^>]*encoding=[\"']?([\\w\\-\\.\\(\\):+]{2,127}).*\\?>";

    private static final String REGEX_BODY_ENCODING = "(?:" + REGEX_HTML_CHARSET + "|" + REGEX_HTML5_CHARSET + "|" + REGEX_XML_ENCODING + ")";

    // can't be instantiate this class
    private HttpResponseEncodingUtil() {
    }

    /**
     * Returns a character encoding name of http response body.
     *
     * @param responseHeaders http response header(s)
     * @param responseBody http response body
     * @return character encoding name
     */
    public static String getEncoding(List<String> responseHeaders, String responseBody) {

        String encoding = "";

        if (null != responseHeaders && !responseHeaders.isEmpty()) {
            // search response headers
            Pattern pattern = Pattern.compile(REGEX_HEADER_CHARSET, Pattern.CASE_INSENSITIVE);
            Matcher matcher;

            for (String header : responseHeaders) {
                matcher = pattern.matcher(header);
                if (matcher.lookingAt()) {
                    encoding = matcher.group(1);
                    //break; // the latest statement wins.
                }
            }
        }

        if (!encoding.isEmpty() || null == responseBody || responseBody.isEmpty()) {
            return encoding;
        }

        // search response body
        Pattern pattern = Pattern.compile(REGEX_BODY_ENCODING, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(responseBody);

        if (matcher.find()) {
            if (null != matcher.group(1)) {
                encoding = matcher.group(1);
            } else if (null != matcher.group(2)) {
                encoding = matcher.group(2);
            } else if (null != matcher.group(3)) {
                encoding = matcher.group(3);
            }
        }

        return encoding;
    }
}
