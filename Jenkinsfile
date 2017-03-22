void setBuildStatus(String message, String state) {
  step([
      $class: "GitHubCommitStatusSetter",
      reposSource: [$class: "ManuallyEnteredRepositorySource", url: "https://github.com/my-org/my-repo"],
      contextSource: [$class: "ManuallyEnteredCommitContextSource", context: "ci/jenkins/build-status"],
      errorHandlers: [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]],
      statusResultSource: [ $class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: message, state: state]] ]
  ]);
}

pipeline {
    agent any

    stages {
        stage('Preparation') {
            steps {
                checkout scm
                sh "rm -f keychains.directory"
                sh "rm -rf keychains/"
                sh "rm -f pit-test.zip"
            }
        }

        stage('Build') {
            steps {
                sh "./gradlew clean assemble"
            }
        }

        stage('Test') {
            steps {
                sh "./gradlew test"
            }
        }

        stage('Mutation Test') {
            steps {
                sh "./gradlew pitest"
            }
        }

        stage('Results') {
            steps {
                junit '**/test-results/test/TEST-*.xml'
                // step([$class: 'PitPublisher', mutationStatsFile: 'build/pit-reports/**/mutations.xml', minimumKillRatio: 50.00, killRatioMustImprove: false])
                archive 'target/*.jar'
                setBuildStatus("Build complete", "SUCCESS");

                archiveArtifacts artifacts: 'build/distributions/*.zip'

                zip zipFile: 'pit-test.zip', dir: 'build/pit-reports/', archive: true

                sh "rm -rf targets/"
            }
        }

        stage('Docker') {
            steps {
                // sh "docker build --tag=postit-server docker/Dockerfile.server"
                // sh "docker image push nishadmathur.com/postit-server"
            }
        }
    }
}
