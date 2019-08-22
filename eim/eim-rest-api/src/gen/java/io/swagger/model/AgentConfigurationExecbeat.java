package io.swagger.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-11-15T18:55:29.616+01:00")
public class AgentConfigurationExecbeat {
	 
	@JsonProperty("packetLoss")
	private String packetLoss = null;
	 
	@JsonProperty("stressNg")
	private String stressNg = null;
	
	public AgentConfigurationExecbeat() {
		
	}
	  
	public AgentConfigurationExecbeat packetloss(String packetloss) {
	  this.packetLoss="iptables -A INPUT -m statistic --mode random --probability "+packetloss+" -j DROP";
	  return this;
	}
	  
	  
	public AgentConfigurationExecbeat stressNg(String stressNg) {
	 this.stressNg = stressNg;
	  return this;
	}
	  
	/**
	  * Get stressNg
	  * @return stressNg
	**/
	 @JsonProperty("stressNg")
	  @ApiModelProperty(example = "stress-ng --cpu 4 --vm 2 --hdd 1 --fork 8 --switch 4 --timeout 30s --metrics-brief", value = "")
	  @NotNull
	  public String getStressNg() {
		  return stressNg;
	  }
	  
	  public void setStressNg(String stressNg) {
		  this.stressNg=stressNg;
	  }
	  
	  /**
	   * Get packetLoss
	   * @return packetLoss
	   **/
	  @JsonProperty("packetLoss")
	  @ApiModelProperty(example = "0.01", value = "")
	  @NotNull
	  public String getPacketLoss() {
		  return packetLoss;
	  }
	  
	  public void setPacketLoss(String packetloss) {
		  this.packetLoss=packetloss;
	  }
	  
	  @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((packetLoss == null) ? 0 : packetLoss.hashCode());
		result = prime * result + ((stressNg == null) ? 0 : stressNg.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgentConfigurationExecbeat other = (AgentConfigurationExecbeat) obj;
		if (packetLoss == null) {
			if (other.packetLoss != null)
				return false;
		} else if (!packetLoss.equals(other.packetLoss))
			return false;
		if (stressNg == null) {
			if (other.stressNg != null)
				return false;
		} else if (!stressNg.equals(other.stressNg))
			return false;
		return true;
	}


	public String toString() {
		  StringBuilder sb = new StringBuilder();
		  sb.append("class AgentConfigurationExecbeat {\n");
		  sb.append("    stressNg").append(toIndentedString(stressNg)).append("\n");
		  sb.append("    packetLoss").append(toIndentedString(packetLoss)).append("\n");
		  sb.append("}");
		  return sb.toString();
	  }
	  /**
	   * Convert the given object to string with each line indented by 4 spaces
	   * (except the first line).
	   */
	  private String toIndentedString(java.lang.Object o) {
	    if (o == null) {
	      return "null";
	    }
	    return o.toString().replace("\n", "\n    ");
	  }
}
