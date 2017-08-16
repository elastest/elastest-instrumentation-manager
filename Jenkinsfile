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

		stage "Build Elasticsearch image - Package"
	                echo ("building elasticsearch..")
			def elasticsearch_image = docker.build("elastest/eim-elasticsearch:0.1","./elasticsearch")

		stage "Build Logstash image - Package"
                	echo ("building logstash..")
			def logstash_image = docker.build("elastest/eim-logstash:0.1","./logstash")

		stage "Build Kibana image - Package"
                        echo ("building kibana..")
                        def kibana_image = docker.build("elastest/eim-kibana:0.1","./kibana")

		stage "Build SuT image - Package"
                        echo ("building sut..")
                        def sut_image = docker.build("elastest/eim-sut:0.1","./sut")

		stage "Build EIM image - Package"
                        echo ("building eim..")
                        def eim_image = docker.build("elastest/eim:0.1","./eim")

		stage "Execute docker compose"
                 	echo ("running docker compose..")
                	sh 'docker-compose -f docker-compose.yml up -d --build'
//            stage "Run image"
//                myimage.run()

            stage "Publish"
                echo ("Publishing")
                withCredentials([[
			$class: 'UsernamePasswordMultiBinding', 
			credentialsId: 'elastestci-dockerhub',
			usernameVariable: 'USERNAME', 
			passwordVariable: 'PASSWORD']]) {
                    		sh 'docker login -u "$USERNAME" -p "$PASSWORD"'
				elasticsearch_image.push()
				logstash_image.push()
				kibana_image.push()
				sut_image.push()
				eim_image.push()
                	}   
        }   
}
