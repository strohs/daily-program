package java.date_localization;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: java.cliff
 * Date: 1/21/13
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class DateFormat {

    private static Map<String, String> linkJavaISO = new HashMap<>();
    static {
        linkJavaISO.put("%l", "%1\\$tL");
        linkJavaISO.put("%s", "%1\\$tS");
        linkJavaISO.put("%m", "%1\\$tM");
        linkJavaISO.put("%h", "%1\\$tI");
        linkJavaISO.put("%H", "%1\\$tH");
        linkJavaISO.put("%c", "%1\\$tp");
        linkJavaISO.put("%d", "%1\\$td");
        linkJavaISO.put("%M", "%1\\$tm");
        linkJavaISO.put("%y", "%1\\$tY");
    }

    public static String dateFromISO(String format){
        for (Map.Entry<String, String> entry : linkJavaISO.entrySet()) {
            format = format.replaceAll(entry.getKey(), entry.getValue());
        }

        return String.format(format, Calendar.getInstance());
    }

    public static void main(String[] args) {
        System.out.println(dateFromISO("%s.%l"));
        System.out.println(dateFromISO("%s:%m:%h %M/%d/%y"));
        System.out.println(dateFromISO("The minute is %m! The hour is %h."));
    }
}
