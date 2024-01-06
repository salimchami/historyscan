# History Scan Project

## Description

This project is a Java application built with Spring Boot and Gradle.

**It involves features for codebase Git analysis**.
It's inspired from the book [Your Code as a Crime Scene](https://pragprog.com/book/atcrime/your-code-as-a-crime-scene)
by Adam Tornhill.

## Features

- **Git Repository Analysis**: Analyzes a Git repository history and the current codebase file system tree from the disk
  to provide a tree of cloc revisions with score.
  This tree corresponds to the file system tree of the current codebase with the score of each file and folder.

- **Score Calculation**: Calculates a score for each file and folder in the file system tree based on the number of
  revisions and lines of code.

- **Git Repository Cloning**: Clones a Git repository from a remote URL to the local file system.

- **Inputs**: You must provide a git repository and a root folder name to start the analysis based on this root folder.
    - Git repository: The git repository URL to clone and analyze. The repository must be public **for now**.
    - Root folder name (optional): The root folder name to start the analysis based on it.
      Its name must be a folder in the git repository.

## Getting Started

### Dependencies

- Java
- Spring Boot
- Gradle

### Install and start

#### Backend

- update the application.properties to update the codebases root folder path.

```
    io.sch.historyscan.codebases.folder=[YOUR_PATH]
```

- Start the spring boot application on the default port.

```
    ./gradlew build
    ./gradlew bootRun
```

#### Frontend

Please refer to the README.md file in the [frontend repository](https://github.com/salimchami/historyscan-statics.git).

## Authors

Salim CHAMI
work@salimchami.net

## Version History

* 1.0.0-ALPHA:
    * better score calculation
    * file system tree domain entity corresponding to the codebase file system tree

* 0.0.1
    * init project
    * add git repository cloning feature (fetch, pull...)
    * add git repository analysis feature
    * add score calculation feature

## License

This project is licensed under the MIT License - see the LICENSE.md file for details.