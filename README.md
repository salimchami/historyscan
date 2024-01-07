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

#### Backend 
- Java
- Spring Boot
- Gradle

#### Frontend 
- NodeJS
- Angular 
- 
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
This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 16.1.1.

##### Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

##### Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

##### Build

Run `npm run build` to build the project. The build artifacts will be stored in the `dist/` directory.

##### Running unit tests

Run `npm run test` to execute the unit tests via [Karma](https://karma-runner.github.io).

##### Running end-to-end tests

Run `npm run e2e` to execute the end-to-end tests via a platform of your choice. To use this command, you need to first add a package that implements end-to-end testing capabilities.

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

This project is licensed under the CC BY-NC License - see the LICENSE.md file for details.