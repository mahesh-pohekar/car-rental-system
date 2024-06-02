pipeline {
    agent any

    environment {
        DOCKER_CREDENTIALS_ID = 'docker-hub-credentials'
        DOCKERHUB_NAMESPACE = 'maheshpohekar' // Replace with your Docker Hub namespace
        ANGULAR_IMAGE = "${DOCKERHUB_NAMESPACE}/frontend"
        SPRING_IMAGE = "${DOCKERHUB_NAMESPACE}/backend"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/mahesh-pohekar/techm-cicd-project.git' // Replace with your repository URL
            }
        }

        stage('Build and Push Angular') {
            steps {
                dir('car_rental_system_frontend') {
                    script {
                        docker.withRegistry('https://registry.hub.docker.com/', DOCKER_CREDENTIALS_ID) {
                            def angularImage = docker.build("${ANGULAR_IMAGE}:latest")
                            angularImage.push()
                        }
                    }
                }
            }
        }

        stage('Build and Push Spring Boot') {
            steps {
                dir('car_rental_system') {
                    script {
                        docker.withRegistry('https://registry.hub.docker.com/', DOCKER_CREDENTIALS_ID) {
                            def springImage = docker.build("${SPRING_IMAGE}:latest")
                            springImage.push()
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Build and push successful!'
        }
        failure {
            echo 'Build and push failed!'
        }
    }
}
