node('linux')
{
  stage ('Poll') {
   // Poll from upstream:
    checkout([
                        $class: 'GitSCM',
                        branches: [[name: '*/main']],
                        doGenerateSubmoduleConfigurations: false,
                        extensions: [],
                        userRemoteConfigs: [[url: "https://github.com/IBM/zos-nc.git"]]])
    checkout([
      $class: 'GitSCM',
      branches: [[name: '*/main']],
      doGenerateSubmoduleConfigurations: false,
      extensions: [],
      userRemoteConfigs: [[url: 'https://github.com/ZOSOpenTools/zosncport.git']]])
  }
  stage('Build') {
    build job: 'Port-Pipeline', parameters: [string(name: 'PORT_GITHUB_REPO', value: 'https://github.com/ZOSOpenTools/zosncport.git'), string(name: 'PORT_DESCRIPTION', value: 'Simplified nc (netcat), tcp only. Connect stdin, stdout to an open tcp connections output/input' ), string(name: 'BUILD_LINE', value: 'STABLE') ]
  }
}
