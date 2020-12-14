listView('Kubernetes') {
  description('Kubernetes Related Jobs')
  filterBuildQueue()
  filterExecutors()
  recurse()
  jobs {
    name('Kubernetes/kind-deployment')
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

folder('Kubernetes') {
  description('Kubernetes Deployment Jobs')
}

pipelineJob('Kubernetes/kind-deployment')
{
  parameters {
    stringParam('CLUSTER_NAME', 'k8s-test', 'What do you want to name your K8s/KinD cluster?')
  }

  def repoUrl = "https://github.com/knmkonexion/kind.git"
  description("Deploys Kubernetes Cluster via KinD")
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