package com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    private Long id;

    @Column(length = 1000) // 👈 Aumentamos tamaño
    private String title;

    @Column(length = 50)
    private String language;

    private Integer downloadCount;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public Book() {}

    public Book(Long id, String title, String language, Integer downloadCount, Author author) {
        this.id = id;
        this.title = title;
        this.language = language;
        this.downloadCount = downloadCount;
        this.author = author;
    }

    public Long getId() { return id; }

    public String getTitle() { return title; }

    public String getLanguage() { return language; }

    public Integer getDownloadCount() { return downloadCount; }

    public Author getAuthor() { return author; }
}
