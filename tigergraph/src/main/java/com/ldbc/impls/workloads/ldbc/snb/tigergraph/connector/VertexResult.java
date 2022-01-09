package com.ldbc.impls.workloads.ldbc.snb.tigergraph.connector;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.internal.LinkedTreeMap;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

/*
{
  "v_id":   "<vertex_id>",
  "v_type": "<vertex_type>",
  "attributes": {
    <list of key:value pairs,
     one for each attribute
     or vertex-attached accumulator>
  }
}
 */
public class VertexResult {
    String vertexId;
    String vertexType;
    Map<String, Object> attributes;

    /**
     * Create vertex result object from a record read by TigerGraph REST++ client.
     *
     * @param record
     */
    public VertexResult(LinkedTreeMap<String, Object> record) {
        this.vertexId = (String) record.get("v_id");
        this.vertexType = (String) record.get("v_type");
        this.attributes = (Map<String, Object>) record.get("attributes");
    }

    @JsonProperty("attributes")
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }


    @JsonProperty("v_id")
    public String getVertexId() {
        return vertexId;
    }

    public void setVertexId(String vertexId) {
        this.vertexId = vertexId;
    }

    @JsonProperty("v_type")
    public String getVertexType() {
        return vertexType;
    }

    public void setVertexType(String vertexType) {
        this.vertexType = vertexType;
    }

    @Override
    public String toString() {
        return "VertexResult{" +
                "vertexId='" + vertexId + '\'' +
                ", vertexType='" + vertexType + '\'' +
                ", attributes=" + attributes +
                '}';
    }

    public String getString(String attributeName) {
        return (String) this.attributes.get(attributeName);
    }

    public int getInt(String attributeName) {
        // the rest client uses Long for integer values
        return Math.toIntExact((Long) this.attributes.get(attributeName));
    }

    public long getLong(String attributeName) {
        return (Long) this.attributes.get(attributeName);
    }

    public long getDateTimeAsEpoch(String attributeName) throws ParseException {
        String datetime = (String) this.attributes.get(attributeName);

        return (Long) ResultConverter.parseDateTime(datetime).getTime();
    }

    public Iterable<String> getStringList(String attributeName) {
        ArrayList<String> result = new ArrayList<>();
        result = (ArrayList<String>) this.attributes.get(attributeName);
        return result;
    }

    public ArrayList<Object> getObjectList(String attributeName) {
        return (ArrayList<Object>) this.attributes.get(attributeName);
    }

    public boolean getBoolean(String attributeName) {
        return (boolean) this.attributes.get(attributeName);
    }
}