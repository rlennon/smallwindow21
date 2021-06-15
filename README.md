# smallwindow21

Repo for Small Window Assignment

## Table of Contents

- [Preamble](#preamble)
  - [Product Owner](#product-owner)
  - [Rockstars](#rockstars)
- [Project Deadline](#project-deadline)
- [Project Specification](#project-specification)
- [Useful Links](#useful-links)
  - [More Information](#more-information)
- [Risk Register](#risk-register)
- [Tenants of Design](#tenants-of-design)
- [Social Contract](#social-contract)
  - [Meetings](#meetings)
  - [Communication](#communication)
- [Other](#other)
  - [Branching Strategy](#branching-strategy)
  - [Estimating Story Points](#estimating-story-points)
- [Development](#development)
  - [PWA Support](#pwa-support)
  - [Managing dependencies](#managing-dependencies)
  - [Using Angular CLI](#using-angular-cli)
- [Building for production](#building-for-production)
  - [Packaging as jar](#packaging-as-jar)
  - [Packaging as war](#packaging-as-war)
- [Testing](#testing)
  - [Client tests](#client-tests)
  - [Other tests](#other tests)
  - [Code quality](#code-quality)
- [Using Docker to simplify development](#using-docker-to-simplify-development)
- [Continuous Integration](#continuous-integration)

## Preamble

This is the online repository for the DevOps Project Management Assignment 2021.

The customer would like an on-line system to take in staff details for analysis. SmallWindow is a
engineering and tech company. The product that they wish to market is BusIntelligence. The system
must take details for new staff and to provide easy to use analysis features. For example, they may
wish to have a review all staff which have CompTIA and have DevSecOps training as this may indicate
future projects that may be exploited. Keep the analysis simple. The analysis system should be clean
and simple. The system needs to take into account the usual details and present simplified graphics.
It must be possible to upload files or images. The administrator should be able to access detailed
information and edit as appropriate. Once the client enters details it should not be able to be changed
by the client.

Our product will be delivered using an Agile methodology that embraces the DevOps culture. Please note that our culture embraces change and these documents are treated as living, breathing artefacts that will be continuously updated.

### Product Owner

Ruth G. Lennon

### Rockstars

- Damien Gallagher
- Michael Conaghan
- Ronan Clancy
- Sharon Greene
- Sunoj Jose
- John Richard
- Robert Tokarz

## Project Deadline

Refer to BB for deadlines

## Project Specification

<team must agree specifications here - below are samples for discussion purposes>  
 Clean and simple design
User access levels (client, administrator)
Includes at least one self developed api and one webservice
To be run over <specify platform>

    Frameworks
    Database
    Database persistence technology
    Define the buisness Requirements
    Named queries and database triggers for security
    Regex for cleansing and validation of data before sending to the database.

## Useful Links

- Project Slack: https://app.slack.com/client/T84LE6L6R/C023LTYQFFY
- GitHub: https://github.com/rlennon/smallwindow21
- Jira: https://studentjira.lyit.ie/projects/SMLWIN21/

### More Information

For more information visit our other sections
<pick from the sample sections below and add your own>

| Section         | Description                                  |
| --------------- | -------------------------------------------- |
| Process         | Describes the companies process              |
| Project Log     | Log of project activities                    |
| Costings        | Overview of the project cost                 |
| Architecture    | Outlines the architecture                    |
| Environments    | Overview of the environment set-up           |
| DR Plan         | Disaster Recovery Plan and procedures        |
| Requirements    | Overview of the requirements for the project |
| SLAs            | Service level agreements                     |
| Risk Management | How we manage risk                           |
| Security        | Overview of security                         |
| Project Log     | Team log for the project                     |

## Risk Register

These are the current Risks on the project, re-aligned on a weekly basis
<pick from the sample sections below and add your own>

    Infrastructure proving to be a real problem, may block being able to release software
    Team is finding itself to be running short on time due to other work and study commitments
    No PO feedback on software
    Unknown technology choices has led to a lot of upskilling required
    Changing / ambiguous requirements
    Talk of the company being bought out has raised concerns
    Lack of rights for toolsets chosen has hindered progress and ability to deliver
        <p>Risk Register Template is loaded here.
        https://studentjira.lyit.ie/projects/SMLWIN21/issues/SMLWIN21-11?filter=allopenissues

## Tenants of Design

pick from the sample sections below and add your own

    Dedication to clean, secure, performant and self documented code
        code Frameworks used
        code coverage tool used
        Secure code: Regex for cleansing and validation, Named queries and database triggers
        performance testing tool to be used
    Documentation / code commenting (javadoc)/seperate branch
    Datastore for persistence
    Testing:
        Unit testing
        integration testing
        UA
    Environments:
        staging and production
        tight configuration management for consistency and reproducibility
        automated creation and deployments
        integrated and automated pipeline (commit -> test -> deploy)
    Github version control:
        branches used
        version/release management
    Agile project management methods/principles (jira)

## Social Contract

### Meetings

    Stand-ups will occur on Every Wednesday during class and Mondays at 8pm GMT+1 using Automated bot.
    The order that people give their updates will be based on alphabetical order of those present at the meeting.
    Updates will be in the form: What I've done, Impediments, What I plan to do.
    Sprint planning will occur Mondays at the end of our sprint on Zoom.
    Please add and update items within Jira prior to the sprint planning session.
    Sprint retro will at the end of our sprint on Monday at 8pm GMT+1 (timebox retro for 15 minutes, to be organised by the scrum master).
    The order that people present their sprint retro updates will be based on alphabetical order of those present at the meeting.
    Points raised in the sprint retro will be noted and posted on the slack channel by the Scrum Master. The Scrum Master is rotated per team member every week.
    Backlog refinement will happen on the Monday during our sprint.
    Task estimation will be done using "planningpoker.com" TBD.
    Come prepared to meetings.
    Be on time for Stand Ups and meetings.
    Mobile phones on silent.
    Everyone has equal voice and valuable contribution.
    Keep your language and tone professional at all times.
    Be honest.

### Communication

    Slack is the preferred method of communication.
    Communication in this order: Slack
    If a demonstration is required use Zoom, record the session and upload to the Slack channel.
    No Slack communications between "<Time and Timezone>" TBD.
    Raise a problem as soon as you see it.
    Respect each other and understand differences in knowledge.
    All team documents are to be created using Markdown language and shared on GitHub.
    There are no silly questions, if you don’t understand, ask.
    Share success stories.
    Focus on the positives.
    Don’t make assumptions.
    Don’t interrupt and cut another person off while they are talking.
    Listen when someone is talking, don’t interject.
    Zero tolerance for bullying.

    Agile way of working.
    If are assigned a job, take ownership of it and keep it up to date.
    Stick to your agreed working patterns. Let the team know when you are late or going early.
    Keep JIRA board updated at all times.
    Update the Scrum Board as you progress the story i.e. don’t update at standup.
    Don't be afraid to ask for help.
    Don't be afraid to give constructive criticism, as long as it is constructive.
    Solve roadblocks within the team. If the impediment can’t be solved within the team then give it to the Scrum Master.

## Other

    Sprints will start every Second Monday at 9pm GMT+1.
    The Scrum Master role rotates each week, the schedule is available on the on the process section
    Jira will be used for task management and planning.
    Each member of the team will work 8 per week, unless they are on vacation.

### Branching Strategy

GitworkFlows, Main > develop > feature

### Estimating Story Points

The teams team's velocity is calculated by the number of story points we achieve on average in the previous sprints.

The teams current story point velocity is "N/A".

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

```
npm install
```

We use npm scripts and [Angular CLI][] with [Webpack][] as our build system.

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

```
./mvnw
npm start
```

Npm is also used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in [package.json](package.json). You can also run `npm update` and `npm install` to manage dependencies.
Add the `help` flag on any command to see how you can use it. For example, `npm help update`.

The `npm run` command will list all of the scripts available to run for this project.

### PWA Support

JHipster ships with PWA (Progressive Web App) support, and it's turned off by default. One of the main components of a PWA is a service worker.

The service worker initialization code is disabled by default. To enable it, uncomment the following code in `src/main/webapp/app/app.module.ts`:

```typescript
ServiceWorkerModule.register('ngsw-worker.js', { enabled: false }),
```

### Managing dependencies

For example, to add [Leaflet][] library as a runtime dependency of your application, you would run following command:

```
npm install --save --save-exact leaflet
```

To benefit from TypeScript type definitions from [DefinitelyTyped][] repository in development, you would run following command:

```
npm install --save-dev --save-exact @types/leaflet
```

Then you would import the JS and CSS files specified in library's installation instructions so that [Webpack][] knows about them:
Edit [src/main/webapp/app/app.module.ts](src/main/webapp/app/app.module.ts) file:

```
import 'leaflet/dist/leaflet.js';
```

Edit [src/main/webapp/content/scss/vendor.scss](src/main/webapp/content/scss/vendor.scss) file:

```
@import '~leaflet/dist/leaflet.css';
```

Note: There are still a few other things remaining to do for Leaflet that we won't detail here.

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

### Using Angular CLI

You can also use [Angular CLI][] to generate some custom client code.

For example, the following command:

```
ng generate component my-component
```

will generate few files:

```
create src/main/webapp/app/my-component/my-component.component.html
create src/main/webapp/app/my-component/my-component.component.ts
update src/main/webapp/app/app.module.ts
```

## Building for production

### Packaging as jar

To build the final jar and optimize the smallwindow21 application for production, run:

```
./mvnw -Pprod clean verify
```

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

```
java -jar target/*.jar
```

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

Refer to [Using JHipster in production][] for more details.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

```
./mvnw -Pprod,war clean verify
```

## Testing

To launch your application's tests, run:

```
./mvnw verify
```

### Client tests

Unit tests are run by [Jest][]. They're located in [src/test/javascript/](src/test/javascript/) and can be run with:

```
npm test
```

### Other tests

Performance tests are run by [Gatling][] and written in Scala. They're located in [src/test/gatling](src/test/gatling).

To use those tests, you must install Gatling from [https://gatling.io/](https://gatling.io/).

For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

Note: we have turned off authentication in [src/main/docker/sonar.yml](src/main/docker/sonar.yml) for out of the box experience while trying out SonarQube, for real use cases turn it back on.

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the maven plugin.

Then, run a Sonar analysis:

```
./mvnw -Pprod clean verify sonar:sonar
```

If you need to re-run the Sonar phase, please be sure to specify at least the `initialize` phase since Sonar properties are loaded from the sonar-project.properties file.

```
./mvnw initialize sonar:sonar
```

For more information, refer to the [Code quality page][].

## Using Docker to simplify development

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a mysql database in a docker container, run:

```
docker-compose -f src/main/docker/mysql.yml up -d
```

To stop it and remove the container, run:

```
docker-compose -f src/main/docker/mysql.yml down
```

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

```
./mvnw -Pprod verify jib:dockerBuild
```

Then run:

```
docker-compose -f src/main/docker/app.yml up -d
```

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.
