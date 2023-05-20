pipeline{
    agent any
    tools{
        maven "maven"
    }
    stages{
        stage("Build JAR File"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: '30208d94-324b-496e-a5eb-9d911219d352', url: 'https://github.com/4lephZer0/milkStgo']])
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
                bat "mvn sonar:sonar -Dsonar.projectKey=milkstgo1 -Dsonar.projectName='milkstgo1' -Dsonar.host.url=http://localhost:9000 -Dsonar.token=sqp_3a71dbe87653c4cfca1c4f788dd3c50ccd13f896"
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