/**
 * This class is generated by jOOQ
 */
package com.artorias.database.jooq.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SchemaVersion implements Serializable {

    private static final long serialVersionUID = -103956609;

    private Integer   installedRank;
    private String    version;
    private String    description;
    private String    type;
    private String    script;
    private Integer   checksum;
    private String    installedBy;
    private Timestamp installedOn;
    private Integer   executionTime;
    private Boolean   success;

    public SchemaVersion() {}

    public SchemaVersion(SchemaVersion value) {
        this.installedRank = value.installedRank;
        this.version = value.version;
        this.description = value.description;
        this.type = value.type;
        this.script = value.script;
        this.checksum = value.checksum;
        this.installedBy = value.installedBy;
        this.installedOn = value.installedOn;
        this.executionTime = value.executionTime;
        this.success = value.success;
    }

    public SchemaVersion(
        Integer   installedRank,
        String    version,
        String    description,
        String    type,
        String    script,
        Integer   checksum,
        String    installedBy,
        Timestamp installedOn,
        Integer   executionTime,
        Boolean   success
    ) {
        this.installedRank = installedRank;
        this.version = version;
        this.description = description;
        this.type = type;
        this.script = script;
        this.checksum = checksum;
        this.installedBy = installedBy;
        this.installedOn = installedOn;
        this.executionTime = executionTime;
        this.success = success;
    }

    public Integer getInstalledRank() {
        return this.installedRank;
    }

    public SchemaVersion setInstalledRank(Integer installedRank) {
        this.installedRank = installedRank;
        return this;
    }

    public String getVersion() {
        return this.version;
    }

    public SchemaVersion setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public SchemaVersion setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public SchemaVersion setType(String type) {
        this.type = type;
        return this;
    }

    public String getScript() {
        return this.script;
    }

    public SchemaVersion setScript(String script) {
        this.script = script;
        return this;
    }

    public Integer getChecksum() {
        return this.checksum;
    }

    public SchemaVersion setChecksum(Integer checksum) {
        this.checksum = checksum;
        return this;
    }

    public String getInstalledBy() {
        return this.installedBy;
    }

    public SchemaVersion setInstalledBy(String installedBy) {
        this.installedBy = installedBy;
        return this;
    }

    public Timestamp getInstalledOn() {
        return this.installedOn;
    }

    public SchemaVersion setInstalledOn(Timestamp installedOn) {
        this.installedOn = installedOn;
        return this;
    }

    public Integer getExecutionTime() {
        return this.executionTime;
    }

    public SchemaVersion setExecutionTime(Integer executionTime) {
        this.executionTime = executionTime;
        return this;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public SchemaVersion setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final SchemaVersion other = (SchemaVersion) obj;
        if (installedRank == null) {
            if (other.installedRank != null)
                return false;
        }
        else if (!installedRank.equals(other.installedRank))
            return false;
        if (version == null) {
            if (other.version != null)
                return false;
        }
        else if (!version.equals(other.version))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        }
        else if (!description.equals(other.description))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        }
        else if (!type.equals(other.type))
            return false;
        if (script == null) {
            if (other.script != null)
                return false;
        }
        else if (!script.equals(other.script))
            return false;
        if (checksum == null) {
            if (other.checksum != null)
                return false;
        }
        else if (!checksum.equals(other.checksum))
            return false;
        if (installedBy == null) {
            if (other.installedBy != null)
                return false;
        }
        else if (!installedBy.equals(other.installedBy))
            return false;
        if (installedOn == null) {
            if (other.installedOn != null)
                return false;
        }
        else if (!installedOn.equals(other.installedOn))
            return false;
        if (executionTime == null) {
            if (other.executionTime != null)
                return false;
        }
        else if (!executionTime.equals(other.executionTime))
            return false;
        if (success == null) {
            if (other.success != null)
                return false;
        }
        else if (!success.equals(other.success))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((installedRank == null) ? 0 : installedRank.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((script == null) ? 0 : script.hashCode());
        result = prime * result + ((checksum == null) ? 0 : checksum.hashCode());
        result = prime * result + ((installedBy == null) ? 0 : installedBy.hashCode());
        result = prime * result + ((installedOn == null) ? 0 : installedOn.hashCode());
        result = prime * result + ((executionTime == null) ? 0 : executionTime.hashCode());
        result = prime * result + ((success == null) ? 0 : success.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SchemaVersion (");

        sb.append(installedRank);
        sb.append(", ").append(version);
        sb.append(", ").append(description);
        sb.append(", ").append(type);
        sb.append(", ").append(script);
        sb.append(", ").append(checksum);
        sb.append(", ").append(installedBy);
        sb.append(", ").append(installedOn);
        sb.append(", ").append(executionTime);
        sb.append(", ").append(success);

        sb.append(")");
        return sb.toString();
    }
}
