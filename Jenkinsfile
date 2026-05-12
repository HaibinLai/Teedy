pipeline {
    agent any

    stages {
        stage('Checkout SCM') {
            steps {
                checkout scm
            }
        }

        stage('Clean') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test -Dmaven.test.failure.ignore=true'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('PMD') {
            steps {
                sh 'mvn pmd:pmd pmd:cpd'
            }
        }

        stage('JaCoCo') {
            steps {
                sh 'mvn jacoco:report'
            }
        }

        stage('Site') {
            steps {
                sh 'mvn site -Dmaven.test.skip=true'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -Dmaven.test.skip=true'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: '**/target/*.jar, **/target/*.war, target/site/**', fingerprint: true, allowEmptyArchive: true
        }
    }
}
