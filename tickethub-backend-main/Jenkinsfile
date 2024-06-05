pipeline {
    agent any

    environment {
        //dockerHubRegistry = 'ccamm1/demo-eks-cicd'
        //dockerHubRegistryCredential = 'credential-dockerhub'
        awsecrRegistry = '814835082161.dkr.ecr.ap-northeast-2.amazonaws.com/tickethub-backend'
        awsecrRegistryCredential = 'credential-AWS-ECR'

        //배포
        githubCredential = 'credential-github'
        gitEmail = 'rlatmdals5095@gmail.com'
        gitName = 'SEUNGMIN-KIM-05'

        // 앱
        githubCredentialApplication = 'credential-github-application'
    }

    stages {
        stage('Checkout Application Git Branch') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: githubCredentialApplication, url: 'https://github.com/daeunjeong520/tickethub-backend.git']]])
            }

            post {
                failure {
                    echo 'Repository clone failure'
                }
                success {
                    echo 'Repository clone success'
                }
            }
        }

        stage('Gradle Build') {
            steps {
                echo 'Bulid Gradle'
                dir ('.'){
                    sh """
                ./gradlew clean build
                """
                }
            }
            post {
                failure {
                    error 'This pipeline stops here...'
                }
            }
        }

        stage('Docker Image Build') {
            steps {
                // 도커 이미지 빌드
                sh "docker build . -t ${awsecrRegistry}:${currentBuild.number}"
                sh "docker build . -t ${awsecrRegistry}:latest"
            }
            // 성공, 실패 시 슬랙에 알람오도록 설정
            post {
                failure {
                    echo 'Docker image build failure'
                    //slackSend (color: '#FF0000', message: "FAILED: Docker Image Build '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                success {
                    echo 'Docker image build success'
                    //slackSend (color: '#0AC9FF', message: "SUCCESS: Docker Image Build '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }

        stage('Docker Image Push') {
            steps {
                withDockerRegistry([url: "https://${awsecrRegistry}", credentialsId: "ecr:ap-northeast-2:${awsecrRegistryCredential}"]) {
                    sh "docker push ${awsecrRegistry}:${currentBuild.number}"
                    sh "docker push ${awsecrRegistry}:latest"
                    // 10초 쉰 후에 다음 작업 이어나가도록 함
                    sleep 10
                }
            }
            post {
                failure {
                    echo 'Docker Image Push failure'
                    sh "docker rmi ${awsecrRegistry}:${currentBuild.number}"
                    sh "docker rmi ${awsecrRegistry}:latest"
                    //slackSend (color: '#FF0000', message: "FAILED: Docker Image Push '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                success {
                    echo 'Docker Image Push success'
                    sh "docker rmi ${awsecrRegistry}:${currentBuild.number}"
                    sh "docker rmi ${awsecrRegistry}:latest"
                    //slackSend (color: '#0AC9FF', message: "SUCCESS: Docker Image Push '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }

        stage('Deploy') {
            steps {
                // git 계정 로그인, 해당 레포지토리의 main 브랜치에서 클론
                git credentialsId: githubCredential,
                        url: 'https://github.com/SEUNGMIN-KIM-05/test.git',
                        branch: 'main'

                // 이미지 태그 변경 후 메인 브랜치에 푸시
                sh "git config --global user.email ${gitEmail}"
                sh "git config --global user.name ${gitName}"
                sh "cd prod && cd backend && kustomize edit set image ${awsecrRegistry}:${currentBuild.number} && kustomize build ."
                sh "git add -A"
                sh "git status"
                sh "git commit -m 'update the image tag'"
                sh "git branch -M main"
            }
        }

        stage('Push to Git Repository') {
            steps {
                withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: githubCredential, usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD']]) {
                    sh "git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/SEUNGMIN-KIM-05/test.git"
                }
            }
        }
    }
}