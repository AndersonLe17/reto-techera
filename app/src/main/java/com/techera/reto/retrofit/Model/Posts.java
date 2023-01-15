package com.techera.reto.retrofit.Model;


public class Posts {
    // Atributos
    private Integer id;
    private Integer userId;
    private String title;
    private String body;

    public Posts() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Id: " + this.id + " - userId; " + this.userId + "\ntitle: " + this.title;
    }
}
