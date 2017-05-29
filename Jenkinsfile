pipeline {
    agent any
    stages {
        stage('Download sources') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    doGenerateSubmoduleConfigurations: false,
                    extensions: [
                        [$class: 'UserExclusion', excludedUsers: 'ci-thecoffeine'],
                        [$class: 'LocalBranch', localBranch: 'master']
                    ],
                    submoduleCfg: [],
                    userRemoteConfigs: [
                        [
                            credentialsId: 'b2c5042a-d992-49e5-994a-7ae4bfc4a0bf',
                            url: 'git@github.com:coffeine-009/auth.git'
                        ]
                    ]
                ])
            }
        }
        stage('Release') {
            steps {
                sh "./gradlew clean release"
            }
        }
        stage('Build image') {
            steps {
                script {
                    docker.build('thecoffeine/auth')
                }
            }
        }
        stage("Publish image") {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'thecoffeine') {
                        docker.image('thecoffeine/auth').push('latest')
                    }
                }
            }
        }
    }

    post {
        always {
            archive 'build/libs/**/*.jar'
            junit 'build/test-results/**/*.xml'
        }
        success {
            slackSend channel: '#release',
                color: 'good',
                message: "@channel Auth server has released. \nVersion:${env.BUILD_NUMBER}."
        }
        failure {
            slackSend channel: '#release',
                color: 'danger',
                message: "@channel Auth server hasn't released. \nVersion:${env.BUILD_NUMBER} is failed."
        }
        unstable {
            slackSend channel: '#release',
                color: 'warning',
                message: "@channel Auth server's build #${env.BUILD_NUMBER} is unstable."
        }
        changed {
            slackSend channel: '#release',
                color: 'warning',
                message: "@channel Auth server's build #${env.BUILD_NUMBER} is changed."
        }
    }
}
