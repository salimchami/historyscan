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

- **inputs**: you must provide a git repository and a root folder name to start the analysis based on this root folder.
    - git repository: the git repository URL to clone and analyze. The repository must be public **for now**.
    - root folder name (optional): the root folder name to start the analysis based on this root folder.
      Its name must be a folder in the git repository.

## Getting Started

### Dependencies

- Java
- Spring Boot
- Gradle

### Install and start

#### Backend

Start the spring boot application on the default port.

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

* current version:
    * 0.0.1-SNAPSHOT

## License

This project is licensed under the MIT License - see the LICENSE.md file for details.