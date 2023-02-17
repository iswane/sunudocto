node {
  stage("Clone the project") {
    git branch: 'develop', url: 'https://github.com/iswane/sunudocto.git'
  }

  stage("Compilation") {
    sh "./mvnw clean install -DskipTests"
  }

  stage("Tests") {
    sh "./mvnw test"
  }
}