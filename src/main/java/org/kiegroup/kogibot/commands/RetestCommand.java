package org.kiegroup.kogibot.commands;

import com.github.rvesse.airline.annotations.Cli;
import com.github.rvesse.airline.annotations.Command;
import io.quarkiverse.githubapp.command.airline.CliOptions;
import io.quarkiverse.githubapp.command.airline.CommandOptions;
import org.kohsuke.github.GHEventPayload;

import java.io.IOException;

import static org.kiegroup.kogibot.util.Workflows.executeGHWorkflows;

@Cli(name = "/retest",
        commands = {RetestCommand.RetestPullRequestCommand.class},
        defaultCommand = RetestCommand.RetestPullRequestCommand.class)
@CliOptions(defaultCommandOptions = @CommandOptions(scope = CommandOptions.CommandScope.PULL_REQUESTS))
//@Team ({"AUTHORS", "CONTRIBUTORS"})
public class RetestCommand {



    @Command(name = "retest", description = "Retest Pull Request", hidden = true)
    static class RetestPullRequestCommand implements PullRequestScopedCommands {

        @Override
        public void run(GHEventPayload.IssueComment issueCommentPayload) throws IOException {
            executeGHWorkflows(issueCommentPayload);
        }
    }
}
