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
//                     // Use the SSH credentials to log in to the server and deploy the Docker container
//                     sshagent(['deploy-ssh-credentials']) {
//                         // Run SSH commands on your Linux server from Windows Jenkins
//                         bat 'ssh root@49.13.229.11 "docker pull fatmiayoub17/calculator:latest"'
//
//                         // Stop any running container (if needed)
//                         bat 'ssh root@49.13.229.11 "docker stop calculator || true"'
//
//                         // Remove the old container (if needed)
//                         bat 'ssh root@49.13.229.11 "docker rm calculator || true"'
//
//                         // Run the new container on the server
//                         bat 'ssh root@49.13.229.11 "docker run -d -p 8080:8080 --name calculator fatmiayoub17/calculator:latest"'
//                     }
//                 }
//             }
//         }
        stage('Deploy to Server') {
            steps {
                script {
                    echo 'Deploying to server...'

                    // Using PowerShell to SSH into the server and deploy the Docker container
                    bat '''
                        echo "Deploying Docker container on the server..."

                        // SSH into the server and deploy the container
                        powershell -Command "ssh %SERVER_USER%@%SERVER_IP% 'docker pull %DOCKER_IMAGE% && docker run -d -p 8080:8080 %DOCKER_IMAGE%'"
                    '''
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
