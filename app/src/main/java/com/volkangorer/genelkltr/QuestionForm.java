package com.volkangorer.genelkltr;

public class QuestionForm {
    String id;
    String quesiton;
    String correct;
    String false1;
    String false2;
    String false3;
    int pointQuesiton;

    public QuestionForm(String id ,String quesiton, String correct, String false1, String false2, String false3,int pointQuestion) {
        this.id = id ;
        this.quesiton = quesiton;
        this.correct = correct;
        this.false1 = false1;
        this.false2 = false2;
        this.false3 = false3;
        this.pointQuesiton = pointQuestion;
    }
}
