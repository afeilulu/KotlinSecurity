pipeline {
  agent any
  stages {
    stage('检出') {
      steps {
        checkout([$class: 'GitSCM',
        branches: [[name: GIT_BUILD_REF]],
        userRemoteConfigs: [[
          url: GIT_REPO_URL,
          credentialsId: CREDENTIALS_ID
        ]]])
      }
    }
   stage('编译') {
                  steps {
                    sh 'mvn clean package'
                  }
                 }
    stage('构建镜像并推送到 CODING Docker 制品库') {
      steps {
        script {
          docker.withRegistry(
            "${CCI_CURRENT_WEB_PROTOCOL}://${CODING_DOCKER_REG_HOST}",
            "${CODING_ARTIFACTS_CREDENTIALS_ID}"
          ) {
            def dockerImage = docker.build("${CODING_DOCKER_IMAGE_NAME}", "-f ${DOCKERFILE_PATH} ${DOCKER_BUILD_CONTEXT}")
            dockerImage.push("${DOCKER_IMAGE_VERSION}")
            dockerImage.push("latest")
          }
        }

      }
    }
    stage("部署镜像") {
      steps {
        script {
           sh "curl -u '${DEPLOY_SECRET}' -X POST -H 'Accept: application/json' -H 'Content-Type: application/json' '${DEPLOY_API}' -k"
        }
      }
    }
  }
  environment {
    CODING_DOCKER_REG_HOST = "${CCI_CURRENT_TEAM}-docker.pkg.${CCI_CURRENT_DOMAIN}"
    CODING_DOCKER_IMAGE_NAME = "${PROJECT_NAME.toLowerCase()}/${DOCKER_REPO_NAME}/${DOCKER_IMAGE_NAME}"
    DEPLOY_API = "https://rancher.huishengedu.cn:8443/v3/project/c-xt75l:p-9m4zp/workloads/deployment:backend:kotlin-security-service?action=redeploy"
    DEPLOY_SECRET = "token-wx4xn:ppcrnvkf6cxrs5jrrjxqfrjkqz95cx56jvx7vhf782dtg77hnrxw4j"
  }
}
