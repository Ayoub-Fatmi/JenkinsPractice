pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'fatmiayoub17/jenkinstp:latest'
        DOCKER_USERNAME = 'fatmiayoub17'
        DOCKER_PASSWORD = 'skVCSNTBw'
        SSH_CREDENTIALS_ID = 'Deploy-ssh-key'
        SERVER_IP = '49.13.218.22'
        SERVER_USER = 'root'
    }

    stages {
        stage('Build') {
            steps {
                script {
                    echo 'Building the application...'
                    bat 'mvn clean package'
                }
            }
        }
        stage('Unit Tests') {
            steps {
                script {
                    echo 'Running unit tests...'
                    bat 'mvn test'
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Building Docker image...'
                    bat "docker build -t %DOCKER_IMAGE% ."
                }
            }
        }

        stage('Push Docker Image to DockerHub') {
            steps {
                script {
                    echo 'Logging in to DockerHub...'
                    bat "docker login -u %DOCKER_USERNAME% -p %DOCKER_PASSWORD%"
                    echo 'Pushing Docker image to DockerHub...'
                    bat "docker push %DOCKER_IMAGE%"
                }
            }
        }

//         stage('Deploy to Server') {
//             steps {
//                 script {
//                     echo 'Deploying to server...'
//
//                     sshagent(credentials: [SSH_CREDENTIALS_ID]) {
//                         bat '''
//                             echo "Deploying Docker container on the server..."
//                             powershell -Command "ssh %SERVER_USER%@%SERVER_IP% 'docker pull %DOCKER_IMAGE% && docker run -d -p 8080:8080 %DOCKER_IMAGE%'"
//                         '''
//                     }
//                 }
//             }
//         }
//     }
        stage('Deploy to Server') {
            steps {
                script {
                    echo 'Deploying to server...'

                    // Use withCredentials to directly inject the private key
                    withCredentials([sshUserPrivateKey(credentialsId: 'SSH_CREDENTIALS_ID', keyFileVariable: 'SSH_KEY')]) {
                        bat '''
                            echo "Deploying Docker container on the server..."

                            // Use the private key to authenticate using ssh
                            powershell -Command "ssh -i $env:SSH_KEY %SERVER_USER%@%SERVER_IP% 'docker pull %DOCKER_IMAGE% && docker run -d -p 8080:8080 %DOCKER_IMAGE%'"
                        '''
                    }
                }
            }
       }
}


    post {
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
