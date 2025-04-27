pipeline {
  agent any

  environment {
    JAVA_HOME = tool name: 'JDK 17', type: 'jdk'
    PATH = "${JAVA_HOME}/bin:${env.PATH}"
    SONARQUBE_URL = 'http://host.docker.internal:9000'
    SONARQUBE_TOKEN = 'sqb_84b561786662b143d281fe95cd085ddd89e5328f' // Define este secreto en Jenkins
  }

  stages {
    stage('Checkout Código') {
      steps {
        checkout scm
      }
    }

    stage('Levantar SonarQube') {
      steps {
        script {
          sh '''
            docker run -d --name sonarqube -p 9000:9000 \
              -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true \
              sonarqube:9.9-community
          '''
          // Esperar que Sonar esté listo
          sh '''
            echo "Esperando que SonarQube esté disponible..."
            for i in {1..15}; do
              if curl -s http://localhost:9000/api/system/health | grep -q '"status":"UP"'; then
                echo "✅ SonarQube está listo"
                break
              else
                echo "⏳ Intento $i esperando SonarQube..."
                sleep 5
              fi
            done
          '''
        }
      }
    }

    stage('Build y Package') {
      steps {
        sh 'mvn clean package spring-boot:repackage -DskipTests'
      }
    }

    stage('Análisis SonarQube') {
      steps {
        withSonarQubeEnv('SonarQubeServer') {
          sh """
            mvn sonar:sonar \
              -Dsonar.projectKey=calculadora-api \
              -Dsonar.host.url=${SONARQUBE_URL} \
              -Dsonar.login=${SONARQUBE_TOKEN}
          """
        }
      }
    }

    stage('Instalar Chrome y ChromeDriver') {
      steps {
        sh '''
          sudo apt-get update
          sudo apt-get install -y chromium-chromedriver chromium-browser
          sudo ln -s /usr/lib/chromium-browser/chromedriver /usr/local/bin/chromedriver
          chromedriver --version
        '''
      }
    }

    stage('Levantar la aplicación') {
      steps {
        sh '''
          nohup java -Dspring.profiles.active=prod -Dheadless -jar target/*.jar &
          echo $! > pid.txt
        '''
      }
    }

    stage('Esperar aplicación') {
      steps {
        sh '''
          for i in {1..15}; do
            if curl -s http://localhost:8080/; then
              echo "✅ App disponible"
              exit 0
            else
              echo "⏳ Esperando la app... intento $i"
              sleep 5
            fi
          done
          echo "❌ La app no se levantó a tiempo"
          exit 1
        '''
      }
    }

    stage('Probar Endpoint') {
      steps {
        sh 'curl -v "http://localhost:8080/api/calculadora/sumar?a=5&b=7"'
      }
    }

    stage('Tests de Selenium') {
      steps {
        sh 'mvn test -Dtest=com.ejemplo.calculadora.CalculadoraUITest'
      }
    }

    stage('Mostrar logs') {
      steps {
        sh 'cat nohup.out || echo "No se encontró el archivo de logs"'
      }
    }
  }

  post {
    always {
        node {
            echo "🧹 Limpiando recursos..."
            sh 'echo "Aquí comandos de limpieza o cierre"'
        }
    }
}
}
