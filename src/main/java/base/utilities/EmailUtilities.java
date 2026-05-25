package base.utilities;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailUtilities {

    private static final Logger LOGGER = Logger.getLogger(EmailUtilities.class.getName());
    private static final String DEFAULT_RECEIVER = "bm13.asb@gmail.com";

    // Loaded properties from src/main/resources/config.properties (if present)
    private static final Properties PROPS = new Properties();

    static {
        // Try loading config.properties from classpath (src/main/resources)
        try (InputStream in = EmailUtilities.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) {
                PROPS.load(in);
                LOGGER.info("[EmailUtilities] Loaded configuration from config.properties");
            } else {
                LOGGER.info("[EmailUtilities] No config.properties found on classpath; falling back to environment variables");
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "[EmailUtilities] Error loading config.properties; falling back to environment variables", e);
        }
    }

    /**
     * Sends the execution report by email.
     * Credentials and recipient are read in this order:
     * 1) src/main/resources/config.properties (keys: cricbuzz.fromEmail, cricbuzz.appPassword, cricbuzz.receiver)
     * 2) Environment variables (CRICBUZZ_FROM_EMAIL, CRICBUZZ_APP_PASSWORD, CRICBUZZ_RECEIVER)
     * If credentials are missing, the method logs and skips sending.
     */
    public static void sendExecutionReport(String browser, String tagName) {
        // Prefer properties file values; if absent, fall back to environment variables
        String fromEmail = PROPS.getProperty("cricbuzz.fromEmail", System.getenv("CRICBUZZ_FROM_EMAIL"));
        String appPassword = PROPS.getProperty("cricbuzz.appPassword", System.getenv("CRICBUZZ_APP_PASSWORD"));

        String receiver = PROPS.getProperty("cricbuzz.receiver");
        if (receiver == null || receiver.isEmpty()) {
            receiver = System.getenv("CRICBUZZ_RECEIVER");
        }
        if (receiver == null || receiver.isEmpty()) {
            receiver = DEFAULT_RECEIVER;
        }

        // If sender credentials are missing, skip sending (safer than failing at runtime)
        if (fromEmail == null || fromEmail.isEmpty() || appPassword == null || appPassword.isEmpty()) {
            LOGGER.warning("[EmailUtilities] Missing email credentials (config properties or environment variables). Skipping email send.");
            LOGGER.info("[EmailUtilities] Intended recipient: " + receiver);
            return;
        }

        String projectPath = System.getProperty("user.dir");
        String[] candidatePaths = new String[]{
                projectPath + File.separator + "target" + File.separator + "cucumber-html-report" + File.separator + "index.html",
                projectPath + File.separator + "target" + File.separator + "cucumber-html-report.html",
                projectPath + File.separator + "target" + File.separator + "cucumber-html-report" + File.separator + "cucumber-html-report.html"
        };

        String reportPath = null;
        for (String p : candidatePaths) {
            File f = new File(p);
            if (f.exists() && f.isFile()) {
                reportPath = p;
                break;
            }
        }

        try {
            MultiPartEmail email = new MultiPartEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator(fromEmail, appPassword));
            email.setSSLOnConnect(true);

            email.addTo(receiver);
            email.setFrom(fromEmail);
            email.setSubject("Cricbuzz Automation Report | Browser: " + browser + " | Tags: " + tagName);

            String msg = "Hi Team,\n\n"
                    + "Please find attached the automation execution report for the Cricbuzz test suite (if available).\n\n"
                    + "Browser Used: " + browser + "\n"
                    + "Tags Executed: " + tagName + "\n\n"
                    + "Regards,\nAutomation Team";
            email.setMsg(msg);

            if (reportPath != null) {
                EmailAttachment attachment = new EmailAttachment();
                attachment.setPath(reportPath);
                attachment.setDisposition(EmailAttachment.ATTACHMENT);
                attachment.setDescription("Cucumber Execution Report");
                attachment.setName("Cricbuzz_Automation_Report.html");
                email.attach(attachment);
            } else {
                LOGGER.info("[EmailUtilities] No HTML report found under target/, sending email without attachment.");
            }

            email.send();
            LOGGER.info("[EmailUtilities] Email sent successfully to " + receiver);
        } catch (EmailException e) {
            LOGGER.log(Level.SEVERE, "[EmailUtilities] Failed to send email", e);
        }
    }
}