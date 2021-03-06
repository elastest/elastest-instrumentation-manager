package io.elastest.eim.config;

public class Dictionary {
		
	
	//PROPERTIES
	
//	public static String PROPERTY_MONGODB_HOST = "mongoDB.host";
	
	public static String PROPERTY_PUBLICKEY_LOCATION = "publickey.location";
	public static String PROPERTY_TEMPLATES_SSH_INSTALL_PLAYBOOK = "templates.ssh.install.playbook";
	public static String PROPERTY_TEMPLATES_SSH_SEND_IPTABLES_RULE = "templates.ssh.rule.iptables";
	public static String PROPERTY_TEMPLATES_SSH_REMOVE_PLAYBOOK = "templates.ssh.remove.playbook";
	public static String PROPERTY_TEMPLATES_PLAYBOOK_JOKER = "templates.playbook.joker";
	public static String PROPERTY_TEMPLATES_USER_JOKER = "templates.user.joker";
	public static String PROPERTY_TEMPLATES_SCRIPT_JOKER_PLAYBOOK = "templates.script.joker.playbook";
	public static String PROPERTY_TEMPLATES_SCRIPT_JOKER_CONFIG = "templates.script.joker.config";
	public static String PROPERTY_TEMPLATES_SSH_INSTALL_EXECUTION_PLAYBOOK_PREFIX = "templates.ssh.install.execution_playbook_prefix";
	public static String PROPERTY_TEMPLATES_SSH_REMOVE_EXECUTION_PLAYBOOK_PREFIX = "templates.ssh.remove.execution_playbook_prefix";
	public static String PROPERTY_TEMPLATES_SSH_EXECUTIONPATH = "templates.ssh.executionPath";
	public static String PROPERTY_TEMPLATES_SSH_INSTALL_LAUNCHER = "templates.ssh.install.launcher";
	public static String PROPERTY_TEMPLATES_SSH_REMOVE_LAUNCHER = "templates.ssh.remove.launcher";
	public static String PROPERTY_TEMPLATES_SSH_INSTALL_EXECUTION_LAUNCHER_PREFIX= "templates.ssh.install.execution_launcher_prefix";
	public static String PROPERTY_TEMPLATES_SSH_REMOVE_EXECUTION_LAUNCHER_PREFIX= "templates.ssh.remove.execution_launcher_prefix";

	public static String PROPERTY_TEMPLATES_SSH_HOSTS_FOLDER = "templates.ssh.hostsFolder";
	
	public static String PROPERTY_TEMPLATES_BEATS_PLAYBOOKPATH = "templates.beats.playbookPath";
	public static String PROPERTY_TEMPLATES_BEATS_INSTALL_PLAYBOOK = "templates.beats.install.playbook";
	public static String PROPERTY_TEMPLATES_BEATS_RESTORE_IPTABLES_RULES_PLAYBOOK="templates.beats.restore.iptablesRule.playbook";
	public static String PROPERTY_TEMPLATES_BEATS_REMOVE_PLAYBOOK = "templates.beats.remove.playbook";
	public static String PROPERTY_TEMPLATES_BEATS_INSTALL_EXECUTION_PLAYBOOK_PREFIX_IPTABLES_RULE ="templates.beats.install.execution_playbook_prefix_iptables-rule";
	public static String PROPERTY_TEMPLATES_BEATS_INSTALL_EXECUTION_PLAYBOOK_PREFIX = "templates.beats.install.execution_playbook_prefix";
	public static String PROPERTY_TEMPLATES_BEATS_REMOVE_EXECUTION_PLAYBOOK_PREFIX = "templates.beats.remove.execution_playbook_prefix";
	public static String PROPERTY_TEMPLATES_BEATS_EXECUTIONPATH = "templates.beats.executionPath";
	public static String PROPERTY_TEMPLATES_BEATS_INSTALL_LAUNCHER = "templates.beats.install.launcher";
	public static String PROPERTY_TEMPLATES_BEATS_REMOVE_LAUNCHER = "templates.beats.remove.launcher";
	public static String PROPERTY_TEMPLATES_BEATS_INSTALL_EXECUTION_LAUNCHER_PREFIX = "templates.beats.install.execution_launcher_prefix";
	public static String PROPERTY_TEMPLATES_BEATS_REMOVE_EXECUTION_LAUNCHER_PREFIX = "templates.beats.remove.execution_launcher_prefix";
	public static String PROPERTY_TEMPLATES_BEATS_JOKER_LOGSTASH_IP = "templates.beats.joker.logstaship";
	public static String PROPERTY_TEMPLATES_BEATS_JOKER_LOGSTASH_PORT = "templates.beats.joker.logstashport";
	public static String PROPERTY_TEMPLATES_BEATS_JOKER_EXEC = "templates.beats.joker.exec";
	public static String PROPERTY_TEMPLATES_BEATS_JOKER_COMPONENT = "templates.beats.joker.component";
	public static String PROPERTY_TEMPLATES_BEATS_JOKER_STREAM_FILEBEAT = "templates.beats.joker.stream.filebeat";
	public static String PROPERTY_TEMPLATES_BEATS_JOKER_STREAM_PACKETBEAT = "templates.beats.joker.stream.packetbeat";
	public static String PROPERTY_TEMPLATES_BEATS_JOKER_STREAM_METRICBEAT = "templates.beats.joker.stream.metricbeat";
	public static String PROPERTY_TEMPLATES_BEATS_JOKER_FILEPATHS = "templates.beats.joker.filepaths";
	public static String PROPERTY_TEMPLATES_NUMBER_OF_BLANKS_FOR_FILEPATHS = "            - ";
	public static String PROPERTY_TEMPLATES_BEATS_JOKER_PLAYBOOK_DOCKERIZED_FILEBEAT = "templates.beats.joker.playbook.dockerized.filebeat";
	public static String PROPERTY_TEMPLATES_BEATS_JOKER_DOCKER_PATH = "templates.beats.joker.dockerpath";
	public static String PROPERTY_TEMPLATES_BEATS_JOKER_DOCKER_METRICS = "templates.beats.joker.dockermetrics";
	public static String PROPERTY_TEMPLATES_BEATS_JOKER_PLAYBOOK_DOCKERIZED_METRICBEAT = "templates.beats.joker.playbook.dockerized.metricbeat";
	
	
	// Execbeat properties configuration & installation
	public static String PROPERTY_TEMPLATES_BEATS_PLAYBOOK_IPTABLES_RULE="templates.ssh.rule.iptables";
	public static String PROPERTY_TEMPLATES_BEATS_INSTALL_PLAYBOOK_EXECBEAT="templates.beats.install.plabook";
	public static String PROPERTY_TEMPLATES_BEATS_JOKER_STREAM_EXECBEAT = "templates.beats.joker.stream.execbeat";
	public static String PROPERTY_TEMPLATES_BEATS_ARGS_EXECBEAT="templates.beats.joker.args.execbeat";
	public static String PROPERTY_TEMPLATES_BEATS_CRONEXPRESSION_EXECBEAT="templates.beats.joker.cronExpression.execbeat";
	


	public static String PROPERTY_EXECUTION_LOGS_PATH = "execution.logs.path";
	public static String PROPERTY_EXECUTION_LOGS_SSH_PREFIX = "execution.logs.ssh.prefix";
	public static String PROPERTY_EXECUTION_LOGS_BEATS_PREFIX = "execution.logs.beats.prefix";

	public static String PROPERTY_LOGGING_FILE = "logging.file";
	
	// AVAILABLE ACTIONS OVER SUT
	public static String SUT_ACTION_MONITOR = "monitor";
	public static String SUT_ACTION_UNMONITOR = "unmonitor";
	
	// ACTIONS AVAILABLE OVER SUT: CONTROLLABILITY
	public static String SUT_ACTION_CHECKED = "checked";
	public static String SUT_ACTION_UNCHECKED = "unchecked";
	public static String SUT_ACTION_PACKETLOSS = "packetloss";
	public static String SUT_ACTION_STRESS_CPU = "stress";
	public static String NO_ACTION_PACKETLOSS = "";
	public static String NO_ACTION_STRESS_CPU = "";
	public static String PROPERTY_TEMPLATES_BEATS_ITEM_ANSIBLE = "templates.beats.item.ansible";

	
	// USED TO INSTALL/REMOVE FEATURES IN SUT
	public static String INSTALL = "install";
	public static String REMOVE = "remove";
	public static String REMOVE_CONTROL ="remove_control";
	
	// DOCKERIZED
	public static String DOCKERIZED_YES = "yes";
	public static String DOCKERIZED_NO = "no";
	public static String DOCKERIZED_DEFAULT_DOCKER_PATH = "/var/lib/docker/containers/";
	public static String DOCKERIZED_DEFAULT_DOCKER_METRIC = "/var/run/docker.sock";
	
	//MYSQL DATABASE CONSTANTS
	 public static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	 public static final String DBNAME = "EIM";
	 public static final String DBTABLE_AGENT = "agent";
	 public static final String DBTABLE_AGENT_CONFIGURATION = "agent_configuration";
	 public static final String DBTABLE_AGENT_CONFIGURATION_CONTROL = "agent_configuration_control";
	 public static final String DBUSER = "elastest";
	 public static final String DBPASS = "elastest";
	 //public static final String DBPORT = "3306";
	 //public static final String DBURL ="jdbc:mariadb://" + System.getenv("ET_EIM_MONGO_HOST") + ":" + DBPORT + "/eim";
	 public static final String DBURL ="jdbc:mariadb://" + System.getenv("ET_EDM_MYSQL_HOST") + ":" + System.getenv("ET_EDM_MYSQL_PORT") + "/" + DBNAME;
	 //public static final String DBURL ="jdbc:mariadb://" + "172.17.0.2" + ":" + "3306" + "/" + DBNAME;
	
	 // PREFIX FOR AGENTS ID
	 public static String PREFIX_AGENTS_ID = "iagent";
}
