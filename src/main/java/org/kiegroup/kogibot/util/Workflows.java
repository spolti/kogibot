package org.kiegroup.kogibot.util;

import org.jboss.logging.Logger;
import org.kohsuke.github.GHEventPayload;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHWorkflowRun;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

public class Workflows {

    private static final Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    public static void executeGHWorkflows(final GHEventPayload.IssueComment issueCommentPayload) throws IOException {
        if (issueCommentPayload.getIssue().isPullRequest()) {
            GHPullRequest pullRequest = issueCommentPayload.getRepository()
                    .getPullRequest(issueCommentPayload.getIssue().getNumber());
            List<GHWorkflowRun> ghWorkflowRuns = pullRequest.getRepository().queryWorkflowRuns()
                    .branch(pullRequest.getHead().getRef())
                    .list()
                    .toList()
                    .stream()
                    .filter(workflowRun -> workflowRun.getHeadRepository().getOwnerName()
                            .equals(pullRequest.getHead().getRepository().getOwnerName()))
                    .filter(workflowRun -> workflowRun.getStatus() != GHWorkflowRun.Status.QUEUED
                            || workflowRun.getStatus() != GHWorkflowRun.Status.IN_PROGRESS)
                    .collect(Collectors.toList());

            for (GHWorkflowRun workflowRun : ghWorkflowRuns) {
                workflowRun.rerun();
            }
        }else{
            log.warn("Issue is not a PR");
        }
    }
}
