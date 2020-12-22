listView('Kubernetes') {
  filterBuildQueue()
  filterExecutors()
  recurse()
  jobs {
    name('Kubernetes/kind-deployment')
    name('Kubernetes/k3d-deployment')
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

pipelineJob('Kubernetes/k3d-deployment')
{
  parameters {
    stringParam('CLUSTER_NAME', 'k3s-test', 'What do you want to name your K3s cluster?')
    stringParam('PORT_MAP_LB', '9099:80', 'How do you want to map ports on the load balancer <localPort:hostPort>?')
    stringParam('API_PORT', '6555', 'What API port shall we use for this cluster?')
    stringParam('AGENT_NODES', '2', 'How many agent nodes do you want in this cluster?')
  }
  def repoUrl = "https://github.com/knmkonexion/k3d-deployment.git"
  description("Deploys Kubernetes Cluster via k3s/k3d")
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