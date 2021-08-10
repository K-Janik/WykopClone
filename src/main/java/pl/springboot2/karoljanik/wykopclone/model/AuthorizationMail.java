package pl.springboot2.karoljanik.wykopclone.model;

public class AuthorizationMail {
    private String to;
    private String subject;
    private String text;

    public AuthorizationMail(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.text = body;
    }

    public AuthorizationMail() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
