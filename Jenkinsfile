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
    stage('Hello to Server') {
        steps {
            script {
                echo 'Deploying to server...'

                def SSH_KEY = 'C:\\Users\\Administrator\\.ssh\\id_rsa'

                echo "SSH Key Path: $SSH_KEY"

                withCredentials([sshUserPrivateKey(credentialsId: 'Deploy-ssh-key', keyFileVariable: 'SSH_KEY')]) {
                    bat """
                        echo "Deploying Docker container on the server..."
                        powershell -Command "ssh -o StrictHostKeyChecking=no -i ${SSH_KEY} root@49.13.218.22 'echo hello'"
                    """
                }
            }
        }
    }
    stage('Deploy to Server') {
        steps {
            script {
                echo 'Deploying to server...'

                // Temporarily use the path to the private key directly
                def sshKeyPath = 'C:\\Users\\Administrator\\.ssh\\id_rsa'

                // Print the manually defined SSH key path
                echo "SSH Key Path: $sshKeyPath"

                // Run the SSH command directly using the private key path
                bat '''
                    echo "Deploying Docker container on the server..."
                    powershell -Command "ssh -vvv -i C:\\Users\\Administrator\\.ssh\\id_rsa root@49.13.218.22 'docker pull fatmiayoub17/jenkinstp:latest && docker run -d -p 8080:8080 fatmiayoub17/jenkinstp:latest'"
                '''
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
