# History Scan Project

## Description

This project is a Java application built with Spring Boot and Gradle.

**It involves features for codebase Git analysis**.
It's inspired from the book [Your Code as a Crime Scene](https://pragprog.com/titles/atcrime2/your-code-as-a-crime-scene-second-edition)
by Adam Tornhill.

**Treemap chart of cloc revisions with scores**.

![treemap chart screenshot](doc/treemap-chart-screenshot.png)

**Network graph of network cloc revisions with scores**.

![network graph screenshot](doc/network-chart-screenshot.png)

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

#### Backend 
- Java
- Spring Boot
- Gradle

#### Frontend 
- NodeJS
- Angular

### Install and start

#### Backend

- Update the application.properties to update the codebases root folder path.

```
    io.sch.historyscan.codebases.folder=[YOUR_PATH]
```

- Start the spring boot application on the default port.

```
    ./gradlew build
    ./gradlew bootRun
```

#### Frontend

- Install dependencies.

```
    npm i
```

- Start the Angular application on the default port.

```
    npm run start
```

#### Url 

- Open the application on the default port.

```
    http://localhost:4200
```

## Version History

* 1.3.0:
    * multiple code fixes
    * network graph of cloc revisions with scores

* 1.2.0:
    * multiple code fixes
  
* 1.1.0:
    * multiple code fixes
    * upload and download files tree as json file
    * treemap colors
    * treemap tooltip
    * treemap legend


* 1.0.1-BETA:
    * fix path and name of file when root folder is a path like "src/main/java/com/example/domain"

* 1.0.0-ALPHA:
    * better score calculation
    * file system tree domain entity corresponding to the codebase file system tree

* 0.0.1
    * init project
    * add git repository cloning feature (fetch, pull...)
    * add git repository analysis feature
    * add score calculation feature

## Authors

Salim CHAMI
[work@salimchami.net](mailto:work@salimchami.net)

## License

This project is licensed under the CC BY-NC License.

See the [LICENSE.md](LICENSE.md) file for details.
