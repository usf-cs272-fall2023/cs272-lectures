# CS 272 Lecture Code

Lecture code for CS 272 Software Development. Setup for Eclipse, Maven, and Java 17. See the individual `README.md` files in each subdirectory for more information.

## First-Time Setup

:warning: **You need to [setup HTTPS or SSH access to Github](https://docs.github.com/en/get-started/getting-started-with-git/about-remote-repositories) prior to following these steps.**

To import the lecture code into your Eclipse workspace *for the first time*, follow these steps:

  1. **Copy the clone URI from Github.** Click the green "Code" button and copy the HTTPS or SSH link, depending on which one you have already setup.

  1. **Import the projects into Eclipse.** Go to the "File" menu and select the "Import..." option. In the dialog window, open the "Git" folder and select the "Projects from Git" option. Click the "Next" button.

  1. **Paste the clone URI.** Click the "Clone URI" option in the dialog window and click the "Next" button. If the URI does not automatically paste into the boxes, click "URI" and paste it there. It should auto-fill the other boxes for you.

  1. **Double-check the repository location.** Click the "Next" button until you get to the "Local Destination" window. Double-check the directory is NOT within your Eclipse workspace folder.

  1. **Import the projects.** Continue and follow the prompts until the end.

  1. **Update the projects.** Right-click any of the imported projects and go to the "Maven" menu. Select the "Update Project..." option. Make sure all of the projects are selected and click the "OK" button.

These steps only need to be completed once.

## Updating Projects

If you want the latest version of the lecture code and already completed the first-time setup, then you can import the newest Eclipse projects as follows:

  1. **Open the "Git Repositories" view.** If you do not have this view as part of your current perspective in Eclipse, go to the "Window" menu, "Show View" submenu, and "Other..." option. Under the "Git" folder, select the "Git Repositories" option.

  1. **Pull the latest changes.** Right-click the "lectures" repository in the "Git Repositories" view. Select "Pull" (the first option, without the `...` symbols). This will *pull* the latest changes from Github onto your local system. Click the "Close" button when that is complete.

  1. **Import the projects.** Right-click the "lectures" repository again, and select "Import Projects..." from the menu. Select all of the new projects and follow the prompts.

  1. **Update the projects.** If there are compile errors, right-click the project. Under the "Maven" menu, select "Update Projects..." to update the projects.

See the course guides for a more detailed instructions!
