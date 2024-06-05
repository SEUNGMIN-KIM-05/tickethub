pipeline {
  agent any
  tools {
      nodejs 'node21.7.2'
      git 'git'
  }

  environment {
    awsecrRegistry = '814835082161.dkr.ecr.ap-northeast-2.amazonaws.com/tickethub-frontend'
    awsecrRegistryCredential = 'credential-AWS-ECR'
    
    githubCredential = 'credential-github'
    gitEmail = 'rlatmdals5095@gmail.com'
    gitName = 'SEUNGMIN-KIM-05'
    
    githubCredentialApplication = 'credential-github-application'
  }

  stages {
    stage('Checkout Application Git Branch') {
      steps {
        checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: githubCredentialApplication, url: 'https://github.com/daeunjeong520/tickethub-frontend.git']]])
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

    stage('npm build') {
      steps {
        sh "npm i @vue/cli-service"
        sh "npm run build"
      }
    }

    stage('Docker Image Build') {
      steps {
        sh "docker build . -t ${awsecrRegistry}:${currentBuild.number}"
        sh "docker build . -t ${awsecrRegistry}:latest"
      }

      post {
        failure {
          echo 'Docker image build failure'
        }
        success {
          echo 'Docker image build success'
        }
      }
    }
    

    stage('Docker Image Push') {
      steps {
        withDockerRegistry([url: "https://${awsecrRegistry}", credentialsId: "ecr:ap-northeast-2:${awsecrRegistryCredential}"]) {
          sh "docker push ${awsecrRegistry}:${currentBuild.number}"
          sh "docker push ${awsecrRegistry}:latest"

          sleep 10
        } 
      }
      post {
        failure {
          echo 'Docker Image Push failure'
          sh "docker rmi ${awsecrRegistry}:${currentBuild.number}"
          sh "docker rmi ${awsecrRegistry}:latest"
        }
        success {
          echo 'Docker Image Push success'
          sh "docker rmi ${awsecrRegistry}:${currentBuild.number}"
          sh "docker rmi ${awsecrRegistry}:latest"
        }
      }
    }
    

    stage('Deploy') { 
      steps {
        git credentialsId: githubCredential,
            url: 'https://github.com/SEUNGMIN-KIM-05/test.git',
            branch: 'main'  
        
        sh "git config --global user.email ${gitEmail}"
        sh "git config --global user.name ${gitName}"
        sh "cd prod && cd frontend && kustomize edit set image ${awsecrRegistry}:${currentBuild.number} && kustomize build ."
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