package com.two57.jenkins.sharedlib

class Commons {
    public static final String SLACK_MESSAGE = "Sending Slack Notification..."
    public static final String EMAIL_MESSAGE = "Sending Email..."

    Script script;

    def test() {
        script.console()
    }
}