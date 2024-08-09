package com.example.Doctor_Patient_Management.Repository;

import com.example.Doctor_Patient_Management.Exception.DatabaseException;
import com.example.Doctor_Patient_Management.Exception.ErrorMessages;
import com.mongodb.client.MongoCursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.stereotype.Component;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomRepositoryImpl implements CustomRepository {
    @Autowired
    MongoTemplate mongoTemplate;


    public boolean saveDocument(Document document, String collectionName) {
        try {
            mongoTemplate.getCollection(collectionName).insertOne(document);
            return true;
        } catch (Exception e) {
            throw new DatabaseException(ErrorMessages.ERROR_IN_SAVING_DOCUMENT, e);
        }
    }

    public Document findDocumentById(String collectionName, String id) {
        try {
            Document queryDoc = new Document("_id", id);
            return mongoTemplate.getCollection(collectionName).find(queryDoc).first();
        } catch (Exception e) {
            throw new DatabaseException(ErrorMessages.ERROR_IN_FINDING_DOCUMENT, e);
        }
    }

    public List<Document> findDocuments(String collectionName, Document queryDoc) {
        List<Document> documents = new ArrayList<>();
        try (MongoCursor<Document> cursor = mongoTemplate.getCollection(collectionName).find(queryDoc).iterator()) {
            while (cursor.hasNext()) {
                documents.add(cursor.next());
            }
        } catch (Exception e) {
            throw new DatabaseException(ErrorMessages.ERROR_IN_FINDING_DOCUMENTS, e);
        }
        return documents;
    }

    public boolean deleteDocument(String collectionName, String id) {
        try {
            Document queryDoc = new Document("_id", id);
            long deletedCount = mongoTemplate.getCollection(collectionName).deleteOne(queryDoc).getDeletedCount();
            return deletedCount > 0;
        } catch (Exception e) {
            throw new DatabaseException(ErrorMessages.ERROR_IN_DELETING_DOCUMENT, e);
        }
    }

}