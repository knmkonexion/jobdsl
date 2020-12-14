listView('Containers') {
  description('Containers Jobs')
  filterBuildQueue()
  filterExecutors()
  recurse()
  jobs {
    name('Containers/my-container')
  }
  columns {
    status()
    weather()
    name()
    lastSuccess()
    lastFailure()
    lastDuration()
    buildButton()
  }
}

folder('Containers') {
  description('Container build pipelines')
}

pipelineJob('Containers/jenkins-master')
{
  def repoUrl = "https://github.com/knmkonexion/jenkins-master.git"
  description("Container build pipeline for: Jenkins Master")
  keepDependencies(false)

  definition {
    cpsScm {
      lightweight(true)
      scm {
        git {
          remote {
            credentials('kasey-github')
            url(repoUrl)
          }

          branches('master')
          scriptPath('Jenkinsfile')

          extensions {
            wipeOutWorkspace()
          }
        }
      }
    }
  }


  logRotator {
    numToKeep(15)
    artifactNumToKeep(15)\
  }
}