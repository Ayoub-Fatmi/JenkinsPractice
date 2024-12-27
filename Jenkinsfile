pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'fatmiayoub17/jenkinstp:latest'
        DOCKER_USERNAME = 'fatmiayoub17'
        DOCKER_PASSWORD = 'skVCSNTBw'
        SSH_CREDENTIALS_ID = 'Deployssh'
        SERVER_IP = '49.13.218.22'
        SERVER_USER = 'root'
    }

    stages {
        stage('Build') {
            steps {
                script {
                    echo 'Building the application...'
                    sh 'mvn clean package'
                }
            }
        }
        stage('Unit Tests') {
            steps {
                script {
                    echo 'Running unit tests...'
                    sh 'mvn test'
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Building Docker image...'
                    sh "docker build -t ${DOCKER_IMAGE} ."
                }
            }
        }
        stage('Push Docker Image to DockerHub') {
            steps {
                script {
                    echo 'Logging in to DockerHub...'
                    sh "echo ${DOCKER_PASSWORD} | docker login -u ${DOCKER_USERNAME} --password-stdin"
                    echo 'Pushing Docker image to DockerHub...'
                    sh "docker push ${DOCKER_IMAGE}"
                }
            }
        }
        stage('Connect to Server') {
            steps {
                sshagent(credentials: ["${SSH_CREDENTIALS_ID}"]) {
                    script {
                        echo "Connecting to server ${SERVER_IP}..."
                        sh "ssh -o StrictHostKeyChecking=no ${SERVER_USER}@${SERVER_IP} 'echo hello'"
                    }
                }
            }
        }
        stage('Deploy to Server') {
            steps {
                sshagent(credentials: ["${SSH_CREDENTIALS_ID}"]) {
                    script {
                        echo "Deploying application on server ${SERVER_IP}..."
                        sh """
                        ssh -o StrictHostKeyChecking=no ${SERVER_USER}@${SERVER_IP} << EOF
                        echo "Pulling Docker image from DockerHub..."
                        docker pull ${DOCKER_IMAGE}

                        echo "Stopping and removing any existing container..."
                        docker stop app-container || true
                        docker rm app-container || true

                        echo "Starting a new container..."
                        docker run -d --name app-container -p 80:8080 ${DOCKER_IMAGE}

                        echo "Deployment completed successfully."
                        EOF
                        """
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
