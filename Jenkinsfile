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
                        sh 'cd mysql; docker build --build-arg GIT_COMMIT=$(git rev-parse HEAD) --build-arg COMMIT_DATE=$(git log -1 --format=%cd --date=format:%Y-%m-%dT%H:%M:%S) . -t elastest/eim-mysql:latest'
                        def mysql_image = docker.image('elastest/eim-mysql:latest')
                
                stage "Build Elasticsearch image - Package"
                        echo ("building elasticsearch..")
                        //def elasticsearch_image = docker.build("elastest/eim-elasticsearch:latest","./elasticsearch")
                        sh 'cd elasticsearch; docker build --build-arg GIT_COMMIT=$(git rev-parse HEAD) --build-arg COMMIT_DATE=$(git log -1 --format=%cd --date=format:%Y-%m-%dT%H:%M:%S) . -t elastest/eim-elasticsearch:latest'
                        def elasticsearch_image = docker.image('elastest/eim-elasticsearch:latest')

                stage "Build Logstash image - Package"
                        echo ("building logstash..")
                        //def logstash_image = docker.build("elastest/eim-logstash:latest","./logstash")
                        sh 'cd logstash; docker build --build-arg GIT_COMMIT=$(git rev-parse HEAD) --build-arg COMMIT_DATE=$(git log -1 --format=%cd --date=format:%Y-%m-%dT%H:%M:%S) . -t elastest/eim-logstash:latest'
                        def logstash_image = docker.image('elastest/eim-logstash:latest')

                stage "Build Kibana image - Package"
                        echo ("building kibana..")
                        //def kibana_image = docker.build("elastest/eim-kibana:latest","./kibana")
                        sh 'cd kibana; docker build --build-arg GIT_COMMIT=$(git rev-parse HEAD) --build-arg COMMIT_DATE=$(git log -1 --format=%cd --date=format:%Y-%m-%dT%H:%M:%S) . -t elastest/eim-kibana:latest'
                        def kibana_image = docker.image('elastest/eim-kibana:latest')

                stage "Build SuT image - Package"
                        echo ("building sut..")
                        //def sut_image = docker.build("elastest/eim-sut:latest","./sut")
                        sh 'cd sut; docker build --build-arg GIT_COMMIT=$(git rev-parse HEAD) --build-arg COMMIT_DATE=$(git log -1 --format=%cd --date=format:%Y-%m-%dT%H:%M:%S) . -t eim-sut:latest'
                        def sut_image = docker.image('eim-sut:latest')

                stage "Build EIM image - Package"
                        echo ("building eim..")
                        //def eim_image = docker.build("elastest/eim:latest","./eim")
                        sh 'cd eim; docker build --build-arg GIT_COMMIT=$(git rev-parse HEAD) --build-arg COMMIT_DATE=$(git log -1 --format=%cd --date=format:%Y-%m-%dT%H:%M:%S) . -t elastest/eim:latest'
                        def eim_image = docker.image('elastest/eim:latest')

                stage "Execute docker compose"
                        echo ("running docker compose..")
                        sh 'docker-compose -f docker-compose.yml up -d --build'

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
