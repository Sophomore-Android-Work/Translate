package com.example.tra.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class Mydict {
    private String word;
    private String explain;

    public Mydict() {
    }

    public Mydict(String word, String explain) {
        this.word = word;
        this.explain = explain;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
