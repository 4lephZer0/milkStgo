pipeline{
    agent any
    tools{
        maven "maven"
    }
    stages{
        stage("Build JAR File"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: '5baec134-21a4-49dc-b0a1-cb4086de9838', url: 'https://github.com/4lephZer0/milkStgo']])
                bat "mvn clean install"
            }
        }
        stage("Test"){
            steps{
                bat "mvn test"
            }
        }
        stage("SonarQube Analysis"){
            steps{
                bat "mvn clean verify sonar:sonar -Dsonar.projectKey=milkstgo -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_235811b5c0aa1a6796c4fb273774bb1b05ef26cc"
            }
        }
        stage("Build Docker Image"){
            steps{
                bat "docker build -t alephzer0/milkstgo ."
            }
        }
        stage("Push Docker Image"){
            steps{
                withCredentials([string(credentialsId: 'dckrhubpassword', variable: 'dckpass')]) {
                    bat "docker login -u alephzer0 -p ${dckpass}"
                }
                bat "docker push alephzer0/milkstgo"
            }
        }
    }
    post{
        always{
            bat "docker logout"
        }
    }
}