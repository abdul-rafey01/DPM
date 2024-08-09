package com.example.Doctor_Patient_Management.Repository;

import org.bson.Document;

import java.util.List;

public interface CustomRepository {
    boolean saveDocument(Document document, String collectionName);
    List<Document> findDocuments(String collectionName, Document queryDoc);
    Document findDocumentById(String collectionName, String id);
    boolean deleteDocument(String collectionName, String id);
}