package org.kiegroup.kogibot.commands;

import java.io.IOException;

import org.kohsuke.github.GHEventPayload;

public interface PullRequestScopedCommands {
    void run(GHEventPayload.IssueComment issueCommentPayload) throws IOException;
}
