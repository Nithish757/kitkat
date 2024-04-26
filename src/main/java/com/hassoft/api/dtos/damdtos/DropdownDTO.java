package com.hassoft.api.dtos.damdtos;

import java.util.List;
import java.util.Map;

public class DropdownDTO {

    Map<String, List<String>> districts;
    Map<String, List<KeyValueDTO>> applyFors;
    List<String> communities;
    List<KeyValueDTO> prefDistricts;
    Map<String, List<String>> references;

    public Map<String, List<String>> getReferences() {
        return references;
    }

    public void setReferences(Map<String, List<String>> references) {
        this.references = references;
    }

    public Map<String, List<String>> getDistricts() {
        return districts;
    }

    public void setDistricts(Map<String, List<String>> districts) {
        this.districts = districts;
    }

    public Map<String, List<KeyValueDTO>> getApplyFors() {
        return applyFors;
    }

    public void setApplyFors(Map<String, List<KeyValueDTO>> applyFors) {
        this.applyFors = applyFors;
    }

    public List<String> getCommunities() {
        return communities;
    }

    public void setCommunities(List<String> communities) {
        this.communities = communities;
    }


    public List<KeyValueDTO> getPrefDistricts() {
        return prefDistricts;
    }

    public void setPrefDistricts(List<KeyValueDTO> prefDistricts) {
        this.prefDistricts = prefDistricts;
    }

    @Override
    public String toString() {
        return "DropdownDTO{" +
                "districts=" + districts +
                ", applyFors=" + applyFors +
                ", communities=" + communities +
                ", prefDistricts=" + prefDistricts +
                ", references=" + references +
                '}';
    }
}
