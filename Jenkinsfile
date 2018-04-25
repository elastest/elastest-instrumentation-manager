node('docker') {
    stage "Container Prep"
        echo("The node is up")
        def mycontainer = docker.image('elastest/ci-docker-compose-py-siblings:latest')
        mycontainer.pull()
        mycontainer.inside("-u jenkins -v /var/run/docker.sock:/var/run/docker.sock:rw") {

                git 'https://github.com/elastest/elastest-instrumentation-manager'

                stage "Tests"
                        echo ("Starting tests")
                        echo ("(No tests yet)")

                stage "Build eim-rest-api"
                        echo ("Build eim-rest-api")
                        sh 'cd ./eim/eim-rest-api; mvn clean package -DskipTests;'

                stage "Build MySQL image - Package"
                       echo ("building MySQL..")
                        //def logstash_image = docker.build("elastest/eim-mysql:latest","./mysql")
                       sh 'cd mysql; docker build --build-arg GIT_COMMIT=$(git rev-parse HEAD) --build-arg COMMIT_DATE=$(git log -1 --format=%cd --date=format:%Y-%m-%dT%H:%M:%S) . -t elastest/eim-mysql:0.9.0'
                       def mysql_image = docker.image('elastest/eim-mysql:0.9.0')
                
                stage "Build Elasticsearch image - Package"
                        echo ("building elasticsearch..")
                        //def elasticsearch_image = docker.build("elastest/eim-elasticsearch:latest","./elasticsearch")
                        sh 'cd elasticsearch; docker build --build-arg GIT_COMMIT=$(git rev-parse HEAD) --build-arg COMMIT_DATE=$(git log -1 --format=%cd --date=format:%Y-%m-%dT%H:%M:%S) . -t elastest/eim-elasticsearch:0.9.0'
                        def elasticsearch_image = docker.image('elastest/eim-elasticsearch:0.9.0')

                stage "Build Logstash image - Package"
                        echo ("building logstash..")
                        //def logstash_image = docker.build("elastest/eim-logstash:latest","./logstash")
                        sh 'cd logstash; docker build --build-arg GIT_COMMIT=$(git rev-parse HEAD) --build-arg COMMIT_DATE=$(git log -1 --format=%cd --date=format:%Y-%m-%dT%H:%M:%S) . -t elastest/eim-logstash:0.9.0'
                        def logstash_image = docker.image('elastest/eim-logstash:0.9.0')

                stage "Build Kibana image - Package"
                        echo ("building kibana..")
                        //def kibana_image = docker.build("elastest/eim-kibana:latest","./kibana")
                        sh 'cd kibana; docker build --build-arg GIT_COMMIT=$(git rev-parse HEAD) --build-arg COMMIT_DATE=$(git log -1 --format=%cd --date=format:%Y-%m-%dT%H:%M:%S) . -t elastest/eim-kibana:0.9.0'
                        def kibana_image = docker.image('elastest/eim-kibana:0.9.0')

               stage "Build EIM SuT image - Package"
                        echo ("building sut..")
                        //def sut_image = docker.build("elastest/eim-sut:latest","./sut")
                        sh 'cd sut; docker build --build-arg GIT_COMMIT=$(git rev-parse HEAD) --build-arg COMMIT_DATE=$(git log -1 --format=%cd --date=format:%Y-%m-%dT%H:%M:%S) . -t elastest/eim-sut:0.9.0'
                        def sut_image = docker.image('elastest/eim-sut:0.9.0')

                stage "Build EIM image - Package"
                        echo ("building eim..")
                        //def eim_image = docker.build("elastest/eim:latest","./eim")
                        sh 'cd eim; docker build --build-arg GIT_COMMIT=$(git rev-parse HEAD) --build-arg COMMIT_DATE=$(git log -1 --format=%cd --date=format:%Y-%m-%dT%H:%M:%S) . -t elastest/eim:0.9.0'
                        def eim_image = docker.image('elastest/eim:0.9.0')

                stage "Execute docker compose"
                        echo ("running docker compose..")
                        //sh 'docker-compose -f deploy/docker-compose.yml up -d --build'
                        sh 'docker-compose -f docker-compose-standalone.yml up -d --build'

            stage "Publish"
                echo ("Publishing")
                withCredentials([[
                        $class: 'UsernamePasswordMultiBinding', 
                        credentialsId: 'elastestci-dockerhub',
                        usernameVariable: 'USERNAME', 
                        passwordVariable: 'PASSWORD']]) {
                                sh 'docker login -u "$USERNAME" -p "$PASSWORD"'
                                mysql_image.push()
                                elasticsearch_image.push()
                                logstash_image.push()
                                kibana_image.push()
                                sut_image.push()
                                eim_image.push()
                        }   
            }
}
