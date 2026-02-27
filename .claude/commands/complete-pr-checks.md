# Command: Complete PR checks

When opening PRs, we normally add some checks to verify in the real Android device, such as

[] check that the list of LLMs is shown
[] check that tapping in one row of the list navigates to the detail

The "Complete PR checks" command will mark all these checkboxes as done inside the most recently opened PR.
The command checks for current context and assumes that the last opened PR will be the target PR.
If there is no target PR, the command will list the last three PRs opened, sorted by date 
(most recent to less recent), then the user will decide which PR to apply the command to.
The command will also offer the option of applying to a different PR, none of the three listed above. 