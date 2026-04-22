package base.utilities;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

public class EmailUtilities {

    private static final String FROM_EMAIL = "bm.asbs@gmail.com";   // <-- your Gmail
    private static final String APP_PASSWORD = "eqhvyqiditedtmcsk";        // <-- Gmail App Password
    private static final String RECEIVER = "bm13.asb@gmail.com";         // <-- change here

    public static void sendExecutionReport(String browser, String tagName) throws EmailException {

        String projectPath = System.getProperty("user.dir");
        String reportPath = projectPath + "/target/cucumber-html-report/index.html";

        // Create attachment
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath(reportPath);
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("Cucumber Execution Report");
        attachment.setName("Cricbuzz_Automation_Report.html");

        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(FROM_EMAIL, APP_PASSWORD));
        email.setSSLOnConnect(true);

        email.addTo(RECEIVER);
        email.setFrom(FROM_EMAIL);

        email.setSubject("Cricbuzz Automation Report | Browser: " + browser + " | Tags: " + tagName);

        email.setMsg(
                "Hi Team,\n\n"
                        + "Please find attached the automation execution report for the Cricbuzz test suite.\n"
                        + "Browser Used: " + browser + "\n"
                        + "Tags Executed: " + tagName + "\n\n"
                        + "Regards,\nAutomation Team"
        );

        email.attach(attachment);
        email.send();

        System.out.println("EMAIL SENT SUCCESSFULLY!");
    }
}
