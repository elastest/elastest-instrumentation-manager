package io.elastest.eim.test.e2e;

public class EimApiRestTest {

	// TODO - Register Agent:
	/**
	 * curl -i -X POST -H "Content-Type:application/json" -H
	 * "Accept:application/json" http://localhost:8080/eim/api/agent/ -d
	 * '{"address": "172.20.0.6","user": "root","private_key":"-----BEGIN RSA
	 * PRIVATE
	 * KEY-----\nMIIEpAIBAAKCAQEA6lVvhQmi3zW0LH1YfhmXSqf105n5OJEaaQlBVv4lqdrAIcmn\nnqr05RkpBjC5Odoym/mVHDJd0i+j4JQ6FvB9Gq2be5RPxClZp3zMP0kdznZBYRmS\nSYPNtukROf/swKA9D411bnDi8tIoglMQ9Hp6ZnWPChMvwoS02gpVEEyBkD3S6EWd\nPkIuLp21SRAyeH+TGIxawbkbWZSGXF9BD/N3b2G8Dy7t8mp2M1JLrRrmNDPLM+tw\nBZUEm5OAcIrbDONVCB4EGXrLQ4N0GKkY2gQgPCZ3Z3ct1Woy1R6xzSM9lDTL8nap\nUimCBGC6pMqHIqLrXSTBrke49J3nu353cxQj9wIDAQABAoIBAAPclJHkrsJu6CEz\nj5nEYjHgwrRR/UFpYr4IYQNF/OjnqfLkl9aNiqub1ok7lFHXvq3DVym3ysQD9Pdm\nee6W1/jwk3dd4lKhO9D+xX7lfZBBcqJfAYIkoec5wAbsqMIj4d23vw+q3JKT1AcR\nx13ABvRZS5om5sqV1UUilnRGTnxKAHUoS4ICcu+kN11BIAc7oYWS5EqVxeXR5b4q\nMzBbo/938abzjd5c5SOrJIKfWZWIhndMDXEkjGJSfQQIxWTzqsAX1r73V6x8+j11\nOhpBWk5jK41BsoR4vsNXO7rt1qvdkZpIjdDdAVjFT/NRvuz5xX2McuDOuZINK2Tc\nNlul8QECgYEA9kXp6YM2u2EvEC/BQQMt0age9L2+/yR8Mzr2ta0SK2vV0cXsHSpE\nYUMAPFCV70ApGVox/DJ92OUTg33goVrWGw6JwFZo992CxiGEKy8S0LLZyw7EGPxC\nQ3fXhXjilJm4dfQRfzCoUFMhX8HMwDLNtr+GE3L7tGAlCaUh2EdBANECgYEA85bN\nUnkonev5T2q7rRtTeRgmyx4JKoavLxYLThkzs5K7DpPg9kDbS8w5XaOp5LqnI6ud\nx+0quE54j1Abqh3jlBsZR9Z8VzsuffsQLF5FkBJuWM7sBhymOFn12kabTY1kSMEz\n1lufAs/n9FZDaGzSuYSCIo5an+RqTXza6SjoykcCgYEAg+90nztCiSRJeFx9Jf00\nAMwWuXsl5b6AI1oFbdMolsaQqG9mTUGlnI2uhKGPkbtHyWM+wCO0tAwVZi57tzXY\n2mnxdm9UkOXE96xhCFmRtOj8MQLaH6CVR1vexIy8pmusHNUCwqcopM/EY26J6LXO\n64azp5vEKSAQ95fWB+40buECgYEAvmAZ0F9I00Pd8aelTkGRF488onqzBz2EJPTB\nmSQxOCNxdo80vsEpoy/Vlc2Xtl/6yPITunEtdiY+KyOcu3PorZQQSgjj3Pkv+N4D\nYem7zEHbZCU0agJyFpCYiSOttQrQWdxFuz6YJAaBboEM5cxHVR6u5nsDcPt/6Vev\nb5K9fXkCgYAgo/bz+LMMGmWFT1yWLBNKEEPpmtCiBFp5pLrToEN0hMqRnjXpAKrJ\nRG0K/1o4HzTnC6kh1aZm9MhEG3BDdp20IpiJth5tRw4a6YHerLkNGbvT4amk/iqW\nqFchooKilv7pa5lb9SJuMTrWbsDprEi6RNAmf293Lm3NSiYiV33M+A==\n-----END
	 * RSA PRIVATE KEY-----","logstash_ip": "172.20.0.4", "logstash_port":
	 * "5044","password": "elastest"}'
	 **/

	// TODO - Request command using action: PacketLoss Command

	/**
	 * curl -i -X POST -H "Content-Type:application/json" -H
	 * "Accept:application/json"
	 * http://localhost:8080/eim/api/agent/controllability/iagent0/packetloss -d '{
	 * "exec": "EXECBEAT", "component": "EIM", "packetLoss": "0.01", "stressNg": "",
	 * "dockerized": "yes", "cronExpression": "@every 60s" }'
	 */

	// TODO - Request command using action: Stress Command

	/**
	 * curl -i -X POST -H "Content-Type:application/json" -H
	 * "Accept:application/json"
	 * http://localhost:8080/eim/api/agent/controllability/iagent0/stress -d '{
	 * "exec": "EXECBEAT", "component": "EIM", "packetLoss": "", "stressNg": "4",
	 * "dockerized": "yes", "cronExpression": "@every 60s" }'
	 **/

	// TODO - Unistall Agent

	/**
	 * curl -i -X DELETE -H "Content-Type:application/json" -H
	 * "Accept:application/json"
	 * http://localhost:8080/eim/api/agent/iagent0/unmonitor
	 */

	// TODO - Remove Agent

	/**
	 * curl -i -X DELETE -H "Content-Type:application/json" -H
	 * "Accept:application/json" http://localhost:8080/eim/api/agent/iagent0
	 */

}
